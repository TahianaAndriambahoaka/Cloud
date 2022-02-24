import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HTTP } from '@awesome-cordova-plugins/http/ngx';
import { Observable } from 'rxjs';
import { LoadingController } from '@ionic/angular';
import {take,finalize} from 'rxjs/operators';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {
  public message;
  constructor(private router:Router,private http: HttpClient) { }

  ngOnInit() {
  }

  inscription(){
    this.router.navigate(['/inscription']);
  }

  login(data){
    var params = {
      email : data.email,
      password : data.mdp
    }
    const headers = { 'content-type': 'application/json'}  
    this.http.post('http://localhost:8072/v1/auth/signin',params,{'headers':headers})
      .pipe(finalize(() => {}))
        .subscribe(
          res =>{ console.log(res); this.router.navigate(['/accueil'])},
          err => { console.log(err) ; this.message = "Verifier votre email ou votre mot de passe"}
        );              
  }

}
