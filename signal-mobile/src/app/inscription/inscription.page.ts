import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HTTP } from '@awesome-cordova-plugins/http/ngx';
import { Observable } from 'rxjs';
import { LoadingController } from '@ionic/angular';
import {take,finalize} from 'rxjs/operators';

@Component({
  selector: 'app-inscription',
  templateUrl: './inscription.page.html',
  styleUrls: ['./inscription.page.scss'],
})

export class InscriptionPage implements OnInit 
{
  public message;
   constructor(private http: HttpClient){
   }

  ngOnInit() {
  }
  inscription(data) {
    // console.log("Email: " + data.login);
    // console.log("password : " + data.mdp);
    var params = {
      nom: data.nom,
      prenom: data.prenom,
      email : data.email,
      password : data.mdp
    }
    if(data.mdp == data.mdpConfirm){
      const headers = { 'content-type': 'application/json'}  
      this. http.post('http://localhost:8072/v1/auth/signupUtilisateur',params,{'headers':headers}).pipe(finalize(() => {}))
      .subscribe(
        res => {console.log(res); this.message="Inscription réussi"},
        err => { console.log(err.error); this.message = err.error.details}
      );      
    }
    else{
      this.message="le mot de passe n'est pas identique";
    }
  }

}
