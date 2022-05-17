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
   * Método para registrar usuarios, recibe un usuario y lanza la peticion a la API
   * @param user
   * @returns Un observable con el resultado de la petición
   */
 addAnuncio(anuncio:Anuncio){
  const url = `${this.baseUrl}/anuncio`;
  // const headers = new HttpHeaders()
  //   .set('Authorization', `Bearer ${localStorage.getItem('jwt')}` || '' );
  const headers = this.headers;
    return this.http.post(url, anuncio, {headers});
}


}
