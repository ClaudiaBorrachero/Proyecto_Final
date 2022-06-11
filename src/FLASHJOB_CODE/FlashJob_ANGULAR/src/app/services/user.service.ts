import { Byte } from '@angular/compiler/src/util';
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

    updateProfile(user:Usuario, file:any, idUsuario:string){
      let url = `${this.baseUrl}/profile/update/${idUsuario}`;

      if (file==="NotSelected") {
        url = `${this.baseUrl}/profile/updateDefaultImage/${idUsuario}`;
      }

      const headers = this.cargarHeaders();
      const formData: FormData = new FormData();
        formData.append('imagenProfile', file);
        formData.append('firstName', user.firstName!);
        formData.append('lastName', user.lastName!);
        formData.append('phoneNumber', user.phoneNumber!);
        formData.append('location', user.location!);

        console.log("fsdf")
        return this.http.put<Usuario>(url, formData, {headers});
    }

    updatePass(passEdit:any){
      let url = `${this.baseUrl}/profile/updatePass`;


      const headers = this.cargarHeaders();
      const formData: FormData = new FormData();
        formData.append('passwordOld', passEdit.passwordOld);
        formData.append('passwordNew', passEdit.passwordNew);
        formData.append('passwordNew2', passEdit.passwordNew2);

        return this.http.put<Usuario>(url, formData, {headers});
    }


}
