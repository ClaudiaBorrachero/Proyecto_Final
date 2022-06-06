import { Usuario } from './../../interfaces/interface';
import { Byte } from '@angular/compiler/src/util';
import { Anuncio } from 'src/app/interfaces/interface';
import { LoginService } from './../../services/login.service';
import { PrimeNGConfig } from 'primeng/api';
import { SelectButtonModule } from 'primeng/selectbutton';
import { CategoriaService } from './../../services/categoria.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AnuncioService } from 'src/app/services/anuncio.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-searchPage',
  templateUrl: './searchPage.component.html',
  styleUrls: ['./searchPage.component.css']
})
export class SearchPageComponent implements OnInit {

  constructor(private anuncioService:AnuncioService, private rutaActiva: ActivatedRoute, private categoriaService: CategoriaService
    , private primeNGConfig: PrimeNGConfig, private selectButtonModule: SelectButtonModule,
    private loginService: LoginService,
    private router: Router) {

      this.paymentOptions = [
        { name: 'Por Horas', value: 1 },
        { name: 'Total', value: 2 }
      ];

    }

  paymentOptions: any[];

  value2: number=0;
  listaAnuncios:Anuncio[]=[];

  //Variables filtrado
  listaCategorias:any[]=[];
  rangoPrecio:number[] = [0,5000];
  categoria:string='Todas las categorias';
  tipoOrden: string= "Novedades";


  ngOnInit(): void {
    this.primeNGConfig.ripple = true;
    this.cargarCategorias();
    this.onCheckUser();
    this.sacarParametroRuta(this.categoria, this.rangoPrecio, this.tipoOrden);
  }

  public isLogged : boolean = false;
  userRegistered : Usuario ={
    email: '',
    password: '',
    firstName: '',
    lastName: '',
    phoneNumber: '',
    location: ''
  }

  onCheckUser():void{
    this.loginService.validarToken().subscribe({

      next: resp => {
        this.userRegistered = resp;
      }, error(error){

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

/**
 * Metodo para mostrar la lista de categorias, la cual la recibe del servicio categoriaService
 */
 cargarCategorias(){
  this.categoriaService.mostrarCategorias()
  .subscribe( resp => {
    this.listaCategorias=resp;
  })
}

sacarParametroRuta(categoria:string, rangoPrecio:number[], tipoOrden:string){
  this.rutaActiva.params.subscribe((search: any) => {
    this.busquedaAnuncios(search, categoria, rangoPrecio, tipoOrden)
});
}


  busquedaAnuncios(search: any, categoria:string, rangoPrecio:number[], tipoOrden:string){
    let termino:string  = search.termino;

    this.anuncioService.buscarAnuncio(termino, categoria, rangoPrecio, tipoOrden).subscribe({

         next:resp => {
           this.listaAnuncios = resp;

        },
        error(error){
        }
      })
 }

 filtrarCategoria(categoria:string){
   this.categoria = categoria;
   this.sacarParametroRuta(this.categoria, this.rangoPrecio, this.tipoOrden);
 }

 filtrarPrecio(){
  this.sacarParametroRuta(this.categoria, this.rangoPrecio, this.tipoOrden);
}

filtrarPorOrden(tipoOrden: string){
  this.tipoOrden = tipoOrden;
  this.sacarParametroRuta(this.categoria, this.rangoPrecio, this.tipoOrden);
}


}
