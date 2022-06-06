import { Byte } from '@angular/compiler/src/util';
import { JwtHelperService } from '@auth0/angular-jwt';
import { AnuncioService } from 'src/app/services/anuncio.service';
import { Anuncio } from './../../interfaces/interface';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-mis_jobs',
  templateUrl: './mis_jobs.component.html',
  styleUrls: ['./mis_jobs.component.css']
})
export class Mis_jobsComponent implements OnInit {

  constructor(private anuncioService : AnuncioService) { }

  first = 0;
  rows = 10;
  listaAnunciosAdmin:Anuncio[]=[];

  ngOnInit() {
    this.mostrarAnunciosAdmin();
  }

  /**
   * Método que llama a getImage del servicio y transforma un array de bytes en una url correspondiente a una imagen
   * @param file
   * @returns
   */
   getImage(file: Byte[]) {
    return this.anuncioService.getImage(file);
  }

  jwt:JwtHelperService = new JwtHelperService();

  /**
   * Método para obtener role
   */
  findRolUser(): string {

    let token = localStorage.getItem("jwt")!;

    return this.jwt.decodeToken(token).role!;
  }

 /**
   * Metodo para cargar los anuncios para admin
   */
  mostrarAnunciosAdmin(){
    this.anuncioService.mostrarAnunciosAdmin().subscribe( resp => {
      this.listaAnunciosAdmin=resp;

    })
  }

}
