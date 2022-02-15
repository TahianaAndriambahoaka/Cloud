import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-inscription',
  templateUrl: './inscription.page.html',
  styleUrls: ['./inscription.page.scss'],
})

export class InscriptionPage implements OnInit 
{

  constructor(private router:Router) { }

  ngOnInit() {}

  inscription() {
    this.router.navigate(['/login']);
  }

}
