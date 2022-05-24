import { CardRecientesComponent } from './card-recientes/card-recientes.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home/home.component';
import { CarruselComponent } from './carrusel/carrusel.component';
import { RouterModule } from '@angular/router';
import { CarouselModule} from 'primeng/carousel';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {ToastModule} from 'primeng/toast';
import {ButtonModule} from 'primeng/button';

@NgModule({
  declarations: [
    HomeComponent,
    CarruselComponent,
    CardRecientesComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    CarouselModule,
    BrowserAnimationsModule,
    ToastModule,
    ButtonModule
  ],
  exports:[
    HomeComponent,
    CarruselComponent,
    BrowserAnimationsModule,
    ToastModule,
    ButtonModule,
    CardRecientesComponent
  ]
})
export class HomeModule { }
