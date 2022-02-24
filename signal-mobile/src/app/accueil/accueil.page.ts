import { observable } from 'rxjs';
import { Component, Directive, OnInit, Pipe } from '@angular/core';
import { MenuController, ModalController, Platform } from '@ionic/angular';
// import { Camera,CameraResultType,CameraSource,Photo } from '@capacitor/camera';
import { CameraResultType,CameraSource,Photo } from '@capacitor/camera';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HTTP } from '@ionic-native/http';
import { $ } from 'protractor';
import { LoadingController } from '@ionic/angular';
import { Directory, Filesystem } from '@capacitor/filesystem';
import { take, finalize } from 'rxjs/operators';
import { Geolocation } from '@awesome-cordova-plugins/geolocation/ngx';

import { Camera } from '@awesome-cordova-plugins/camera/ngx';

const IMAGE_DIR = 'stored-images';

interface LocalFile {
    name : string ;
    path : string ; 
    data : string ;
}


@Component({ selector: 'app-accueil',templateUrl: './accueil.page.html', styleUrls: ['./accueil.page.scss'], })
export class AccueilPage implements OnInit {

  images : LocalFile [] = [];
  imgURL;

  
  constructor(private menu:MenuController,private modalCtrl:ModalController,private platform : Platform , private LoadingCtrl: LoadingController,private http: HttpClient , private geolocation: Geolocation, private camera:Camera) { }
  
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


  // async selectImage($event) 
  // {
  //   const image = await Camera.getPhoto({
  //       quality : 90,
  //       allowEditing : true ,
  //       resultType : CameraResultType.DataUrl//,
  //       // source : CameraSource.Camera
  //   });
  //   console.log(image);

  //   if(image)
  //   {
  //     this.saveImage(image);
  //   }
  // }

  // async saveImage(photo: Photo)
  // {
  //     const base64Data = await this.readAsBase64(photo);
  //     console.log(base64Data);
  //     const fileName = new Date().getTime() + '.jpeg';
  //     const savedFile = await Filesystem.writeFile(
  //     {
  //         directory: Directory.Data,
  //         path : `${IMAGE_DIR}/${fileName}`,
  //         data : base64Data
  //     });
  //     console.log('saved: ',savedFile);
  //     this.loadFiles();
  // }

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

      this.geolocation.getCurrentPosition().then((resp) => {
          const formData = new FormData();
          formData.append('file',blob,file.name);
          var debug = {longitude:resp.coords.longitude,latitude:resp.coords.latitude};
          var blobine = new Blob([JSON.stringify(debug,null,2)],{type : 'application/json'});
          formData.append('signalement',blobine);
          this.uploadData(formData);
          console.log("///////////////////////////////////////////////////////////");
      }).catch((error) => { console.log('Error getting location', error); });

    }

    async uploadData(formData: FormData)
    {
      const loading = await this.LoadingCtrl.create({  message : 'Uploading image .....', });
      await loading.present();
      const url = 'http://localhost:8072/v1/signalement';
      const myheader = new HttpHeaders().set('Authorization','Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtb2JpbGVAZ21haWwuY29tIiwiaWF0IjoxNjQ1NTQ3NDk4LCJleHAiOjE2NDU2MzM4OTh9.2T6Zr1EURLiFS6KRbEMRJDCVLrb0Er0QKn5IpQrY3DXZ9WOTMgyvyMEGAv5I69pVTzToEOHUVKkGxzPNPzCjZw')//.set('Content-type','application/json')
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


    async saveImage(base64Data)
  {
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

    getCamera(){
      this.camera.getPicture({
        sourceType:this.camera.PictureSourceType.CAMERA,
        destinationType: this.camera.DestinationType.FILE_URI
      }).then((res)=>{
        this.saveImage(res);
        console.log(res);
      }).catch(e=>{
        console.log(e);
      })
    }

    getGalery(){
      this.camera.getPicture({
        sourceType:this.camera.PictureSourceType.PHOTOLIBRARY,
        destinationType: this.camera.DestinationType.DATA_URL
      }).then((res)=>{
        this.saveImage(res);
        console.log(res);
      }).catch(e=>{
        console.log(e);
      })
    }

}
