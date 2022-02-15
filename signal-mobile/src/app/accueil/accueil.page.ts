import { Component, OnInit } from '@angular/core';
import { MenuController, ModalController } from '@ionic/angular';

@Component({
  selector: 'app-accueil',
  templateUrl: './accueil.page.html',
  styleUrls: ['./accueil.page.scss'],
})
export class AccueilPage implements OnInit {

  constructor(private menu:MenuController,private modalCtrl:ModalController) { }

  ngOnInit() {
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

}
