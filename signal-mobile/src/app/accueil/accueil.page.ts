import { observable } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { MenuController, ModalController, Platform } from '@ionic/angular';
import { Camera,CameraResultType,CameraSource,Photo } from '@capacitor/camera';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HTTP } from '@ionic-native/http';
import { $ } from 'protractor';
import { LoadingController } from '@ionic/angular';
import { Directory, Filesystem } from '@capacitor/filesystem';
import { take, finalize } from 'rxjs/operators';

const IMAGE_DIR = 'stored-images';

interface LocalFile {
    name : string ;
    path : string ; 
    data : string ;
}


@Component({ selector: 'app-accueil',templateUrl: './accueil.page.html', styleUrls: ['./accueil.page.scss'], })
export class AccueilPage implements OnInit {

  images : LocalFile [] = [];

  constructor(private menu:MenuController,private modalCtrl:ModalController,private platform : Platform , private LoadingCtrl: LoadingController,private http: HttpClient ) { }
  
  async ngOnInit() {
    this.loadFiles();
  }

  async loadFiles()
  {
    this.images = [];
    const loading = await this.LoadingCtrl.create({
      message: 'Loading data...' ,
    });

    await loading.present();

    Filesystem.readdir({ directory: Directory.Data,path: IMAGE_DIR }).then(result => 
    {
          console.log('HERE: ' , result);
          this.loadFileData(result.files);
    }, async err => {
          console.log('err: ', err);
          await Filesystem.mkdir(
            {
              directory: Directory.Data,
              path : IMAGE_DIR
            }
          );
      }).then(_ => {
        loading.dismiss();
      })

  }


  async loadFileData(fileNames: string [] )
  {
    for( let f of fileNames)
    {
      const filePath = `${IMAGE_DIR}/${f}`;
      const readFile = await Filesystem.readFile({
        directory: Directory.Data,
        path: filePath
      });

      this.images.push({
          name: f,
          path: filePath,
          data : `data:image/jpeg;base64,${readFile.data}`
      });
    }
  }

  openSideNav()
  {
    this.menu.enable(true,'menu-content');
    this.menu.open('menu-content');
  }

  modalClose()
  {
    this.modalCtrl.dismiss();
  }


  async selectImage() 
  {
    const image = await Camera.getPhoto({
        quality : 90,
        allowEditing : false ,
        resultType : CameraResultType.Uri,
        source : CameraSource.Camera
    });
    console.log(image);

    if(image)
    {
      this.saveImage(image);
    }
  }

  async saveImage(photo: Photo)
  {
      const base64Data = await this.readAsBase64(photo);
      console.log(base64Data);
      const fileName = new Date().getTime() + '.jpeg';
      const savedFile = await Filesystem.writeFile(
      {
          directory: Directory.Data,
          path : `${IMAGE_DIR}/${fileName}`,
          data : base64Data
      });
      console.log('saved: ',savedFile);
      this.loadFiles();
  }

  async readAsBase64(photo: Photo)
  {
      if(this.platform.is('hybrid'))
      {
        const file = await Filesystem.readFile({
          path : photo.path
        });

        return file.data;
      }
      else 
      {
        const response = await fetch(photo.webPath);
        const blob = await response.blob();
        return await this.convertBlobToBase64(blob) as string ;
      }
    }

    convertBlobToBase64 = (blob: Blob) => new Promise((resolve , reject ) => {
      const reader = new FileReader;
      reader.onerror = reject ;
      reader.onload = () => {
        resolve(reader.result);
      };
      reader.readAsDataURL(blob);
    });

    async startUpload(file: LocalFile)
    {
      const response = await fetch(file.data);
      const blob = await response.blob();
      const formData = new FormData();
      formData.append('file',blob,file.name);
      var debug = {};
      var blobine = new Blob([JSON.stringify(debug,null,2)],{type : 'application/json'});
      formData.append('signalement',blobine);
      this.uploadData(formData);
    }

    async uploadData(formData: FormData)
    {
      const loading = await this.LoadingCtrl.create({
        message : 'Uploading image .....',
      });
      await loading.present();
      const url = 'http://localhost:8072/v1/signalement';
      const myheader = new HttpHeaders().set('Authorization','Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtb2JpbGVAbW9iaWxlLmNvbSIsImlhdCI6MTY0NTAwNzIzNiwiZXhwIjoxNjQ1MDkzNjM2fQ.XTX77RPD2NMECUVvZ13K7R1skcyPl76Yko8OhFHvTfaU107ibXu-jXUzkaBMKWMSEyKSIzxV3l5qbT4DndglBQ')//.set('Content-type','application/json')
      this.http.post(url, formData,{headers: myheader}).pipe( finalize (() => { loading.dismiss(); })).subscribe(res => { console.log(res); })
    }

    async deleteImage(file: LocalFile)
    {
      await Filesystem.deleteFile({
        directory: Directory.Data,
        path: file.path
      });
      this.loadFiles();
    }

}
