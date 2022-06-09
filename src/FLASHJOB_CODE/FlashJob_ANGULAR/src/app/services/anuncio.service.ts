import { Categoria } from './../interfaces/interface';
import { Byte } from '@angular/compiler/src/util';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Anuncio } from '../interfaces/interface';

@Injectable({
  providedIn: 'root'
})
export class AnuncioService {
  private baseUrl: string = environment.baseUrl;

   private headers = new HttpHeaders()
      .set('Authorization', `Bearer ${localStorage.getItem('jwt')}` || '' );


  constructor(private http: HttpClient) {}

  /**
   * Método que actualiza el header con el token de localStorage
   * @returns
   */
   cargarHeaders(){
    return  new HttpHeaders()
      .set('Authorization', `Bearer ${localStorage.getItem('jwt')}` || '' );
  }

  /**
   * Método para registrar usuarios, recibe un usuario y lanza la peticion a la API
   * @param user
   * @returns Un observable con el resultado de la petición
   */
   addAnuncio(anuncio:Anuncio, file:any){
    let url = `${this.baseUrl}/anuncio`;

    if (file==="NotSelected") {
      url = `${this.baseUrl}/anuncio/defaultImage`;
    }

    const headers = this.cargarHeaders();
    console.log(anuncio.price!.toString())
    const formData: FormData = new FormData();
      formData.append('file', file);
      formData.append('title', anuncio.title!);
      formData.append('category', anuncio.category!);
      formData.append('price', anuncio.price!.toString());
      // formData.append('tipoPrecio', anuncio.tipoPrecio!);
      formData.append('description', anuncio.description!);
      formData.append('location', anuncio.location!);

      return this.http.post<Anuncio>(url, formData, {headers});
  }

  /**
   * Método para cargar los anuncios recientes
   * @returns
   */
   cargarAnunciosRecientes(){
    const url = `${this.baseUrl}/anuncios/anuncios-recientes`;
    return this.http.get<Anuncio[]>(url);
  }

  /**
   * Método para realizar las busqueda de anuncios, hará una llamada a la API con todos los parámetros y recibira una lista de anuncios
   * con los anuncios que se correspondan con los filtros seleccionados
   * @param termino
   * @param categoria
   * @param rangoPrecio
   * @param orden
   * @returns
   */
   buscarAnuncio(termino: string, categoria:string, rangoPrecio:number[], orden:string){
    const url = `${this.baseUrl}/anuncios/?termino=${termino}&categoria=${categoria}&rangoPrecio=${rangoPrecio}&orden=${orden}`;
    return this.http.get<Anuncio[]>(url);
  }

  /**
   * Metodo para borrar un anuncio, le pasamos el id del anuncio a borrar y hará la llamada a la API
   * @param idAnuncio
   * @returns
   */
   borrarAnuncio(idAnuncio:number){
    const url = `${this.baseUrl}/anuncio/${idAnuncio}`;
    const headers = this.cargarHeaders();
      return this.http.delete(url, {headers});
  }

  /**
 * Método para convertir un array de bytes en una url correspondiente a una imagen
 * @param file El parametro de tipo byte que le pasamos para que lo convierta en una url
 * @returns url | null
 */
  getImage(file: Byte[]) {
    if(file != null){
      const base64String = btoa(
        String.fromCharCode(...new Uint8Array(file))
      );
      const source = `data:image/png;base64,${base64String}` + file;
      return source;
    }
    else{
      return null;
    }
  }

  /**
 * Método para mostrar un anuncio concreto, le pasamos un id de anuncio y hara la llamada a la API, la cual nos dará el anuncio correspondiente
 * o un error
 * @param id
 * @returns
 */
mostrarAnuncioDetalle(id: any){

  const url = `${this.baseUrl}/anuncio/${id}`;
  return this.http.get<Anuncio>(url);
}

/**
   * Método para pedir todas las categorias
   * @returns lista con todas las categorias
   */
 misAnuncios(){
  const url = `${this.baseUrl}/profile/mis_jobs`;

  const headers = this.cargarHeaders();
    return this.http.get<Anuncio[]>(url, {headers});

}

/**
 * Método para mostrar todos los anuncios siendo admin
 */
mostrarAnunciosAdmin(){
  const headers = this.cargarHeaders();
  const url = `${this.baseUrl}/anuncio/todos`;
  return this.http.get<Anuncio[]>(url, {headers});

}

}
