import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Anuncio } from 'src/app/interfaces/interface';
import { AnuncioService } from 'src/app/services/anuncio.service';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-card-recientes',
  templateUrl: './card-recientes.component.html',
  styleUrls: ['./card-recientes.component.css']
})
export class CardRecientesComponent implements OnInit {

  constructor(private anuncioService : AnuncioService, private loginService: LoginService, private router: Router) { }

  ngOnInit(): void {
    this.cargarAnunciosRecientes();
  }

  anunciosRecientes:Anuncio[]=[];

  /**
   * Metodo que hace la llamada para cargar los anuncios recientes
   */
  cargarAnunciosRecientes(){
    this.anuncioService.cargarAnunciosRecientes().subscribe({

      next:resp => {
        this.anunciosRecientes = resp;
        this.anunciosRecientes.splice(9)

     },
     error: error =>{

     }

   })
  }


}
