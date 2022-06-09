import { HttpHeaders, HttpClient } from '@angular/common/http';
import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';
import { Usuario } from '../interfaces/interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl: string = environment.baseUrl;

  private headers = new HttpHeaders()
     .set('Authorization', `Bearer ${localStorage.getItem('jwt')}` || '' );

constructor(private http: HttpClient) { }

 /**
   * Método que actualiza el header con el token de localStorage
   * @returns
   */
  cargarHeaders(){
    return  new HttpHeaders()
      .set('Authorization', `Bearer ${localStorage.getItem('jwt')}` || '' );
  }

/**
 * Método para mostrar todos los users siendo admin
 */
 mostrarUsersAdmin(){
  const headers = this.cargarHeaders();
  const url = `${this.baseUrl}/usuario/todos`;
  return this.http.get<Usuario[]>(url, {headers});

}

  /**
  * Metodo para borrar un user, le pasamos el id del user a borrar y hará la llamada a la API
  */
   borrarUser(idUsuario:number){
    const url = `${this.baseUrl}/usuario/${idUsuario}`;
    const headers = this.cargarHeaders();
      return this.http.delete(url, {headers});
  }

}
