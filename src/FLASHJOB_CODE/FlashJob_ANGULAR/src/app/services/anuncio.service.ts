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
   addAnuncio(anuncio:Anuncio, file:File){
    const url = `${this.baseUrl}/anuncio`;
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

      return this.http.post(url, formData, {headers});
  }

  /**
   * Método para cargar los anuncios recientes
   * @returns
   */
   cargarAnunciosRecientes(){
    const url = `${this.baseUrl}/anuncios/anuncios-recientes`;
    return this.http.get<Anuncio[]>(url);
  }

}
