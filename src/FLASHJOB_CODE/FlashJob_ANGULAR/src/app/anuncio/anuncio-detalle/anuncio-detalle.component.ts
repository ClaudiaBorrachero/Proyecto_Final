import { Byte } from '@angular/compiler/src/util';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Anuncio, Usuario } from 'src/app/interfaces/interface';
import { AnuncioService } from 'src/app/services/anuncio.service';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-anuncio-detalle',
  templateUrl: './anuncio-detalle.component.html',
  styleUrls: ['./anuncio-detalle.component.css']
})
export class AnuncioDetalleComponent implements OnInit {

  constructor(private anuncioService: AnuncioService,
    private rutaActiva: ActivatedRoute,
    private loginService: LoginService,
    private router: Router,
    private messageService: MessageService,) {}

  anuncioDetalle: Anuncio = {}
  usuarioRegistrado: Usuario={
    email: '',
    password: '',
    firstName: '',
    lastName: '',
    phoneNumber: '',
    // fechaNacimiento: '',
    location: ''
   };

  ngOnInit(): void {
    this.sacarParametroRuta();
    this.conseguirUsuarioRegistrado();
  }

  cargarAnuncioDetalle(id: number){
    this.anuncioService.mostrarAnuncioDetalle(id).subscribe({
     next:resp => {
       this.anuncioDetalle = resp;
       console.log(this.anuncioDetalle.title);
    },
    error: error =>{
      console.log("aaaaaa")
    }
   })
   }

   sacarParametroRuta(){
     this.rutaActiva.params.subscribe((idRuta: any) => {
     this.cargarAnuncioDetalle(idRuta.id);
   });
   }

  conseguirUsuarioRegistrado(){
    this.loginService.validarToken().subscribe({

      next:resp => {
        this.usuarioRegistrado = resp;
     },
     error: error =>{
     }
   })
  }

   /**
   * Método que llama a getImage del servicio y transforma un array de bytes en una url correspondiente a una imagen
   * @param file
   * @returns
   */
    getImage(file: Byte[]) {
      return this.anuncioService.getImage(file);
    }


}
