import { MessageService } from 'primeng/api';
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

  constructor(private anuncioService : AnuncioService,
    private messageService: MessageService) { }

  first = 0;
  rows = 10;
  dialogoVisible:boolean = false;
  listaAnunciosAdmin:Anuncio[]=[];
  listaMisAnuncios:Anuncio[]=[];

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

  /**
   * Este método resuelve la peticion de anuncioService, la cual dará una lista de anuncios que cargamos en la variable listaMisAnuncios
   */
   misAnuncios(){
    this.dialogoVisible = false;
    this.anuncioService.misAnuncios().subscribe( resp => {
      this.listaMisAnuncios=resp;


    })
  }

  /**
 * Este método borrará un anuncio, le pasamos un id de anuncio por parámatro y resuelve la petición de anuncioService, en el caso de que
 * se borre el anuncio se llamará al método correspondiente para que se muestre el mensaje adecuado
 * @param idAnuncio
 */
 borrarAnuncio(idAnuncio:number){

  this.anuncioService.borrarAnuncio(idAnuncio).subscribe(
   resp => {
      this.showSuccess()
      this.mostrarAnunciosAdmin()
     //  setTimeout(function() {window.location.reload()}, 1000)

     },error => {
       this.showError();
     })
     // window.location.reload();

}
/**
 * Este método muestra el mensaje verde con el texto indicado si ha sido posible borrar un anuncio
 */
 showSuccess() {
  this.messageService.add({severity:'success', summary: 'Eliminado', detail: 'El anuncio ha sido eliminado'});
}
/**
 * Este método muestra el mensaje rojo con el texto indicado si no ha sido posible borrar un anuncio
 */
 showError() {
  this.messageService.add({severity:'error', summary: 'Error', detail: 'No se ha podido eliminar el anuncio'});
}
}
