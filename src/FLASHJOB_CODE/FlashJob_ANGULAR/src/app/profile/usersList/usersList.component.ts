import { JwtHelperService } from '@auth0/angular-jwt';
import { MessageService } from 'primeng/api';
import { Usuario } from './../../interfaces/interface';
import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-usersList',
  templateUrl: './usersList.component.html',
  styleUrls: ['./usersList.component.css']
})
export class UsersListComponent implements OnInit {

  constructor(private usuarioService : UserService,
    private messageService: MessageService) { }

  first = 0;
  rows = 10;
  listaUsersAdmin:Usuario[]=[];

  ngOnInit() {
    this.mostrarUsersAdmin();
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
 * Método para mostrar todos los users siendo admin
 */
 mostrarUsersAdmin(){
  this.usuarioService.mostrarUsersAdmin().subscribe( resp=> {
    this.listaUsersAdmin=resp;
  })
}

/**
 * Este método borrará un user, le pasamos un id de user por parámatro y resuelve la petición de usuarioService, en el caso de que
 * se borre el user se llamará al método correspondiente para que se muestre el mensaje adecuado
 * @param idUsuario
 */
 borrarUser(idUsuario:number){

  this.usuarioService.borrarUser(idUsuario).subscribe(
   resp => {
      this.showSuccess()
      this.mostrarUsersAdmin()
     //  setTimeout(function() {window.location.reload()}, 1000)

     },error => {
       this.showError();
     })
     // window.location.reload();

}
/**
 * Este método muestra el mensaje verde con el texto indicado si ha sido posible borrar un user
 */
 showSuccess() {
  this.messageService.add({severity:'success', summary: 'Eliminado', detail: 'El usuario ha sido eliminado'});
}
/**
 * Este método muestra el mensaje rojo con el texto indicado si no ha sido posible borrar un user
 */
 showError() {
  this.messageService.add({severity:'error', summary: 'Error', detail: 'No se ha podido eliminar el usuario'});
}

}
