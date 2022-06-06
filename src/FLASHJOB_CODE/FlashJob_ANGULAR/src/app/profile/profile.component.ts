import { LoginService } from './../services/login.service';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Usuario } from './../interfaces/interface';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  constructor( private router: Router,
    private loginService: LoginService) { }

  usuarioRegistrado: Usuario = {
    email: '',
    password: '',
    firstName: '',
    lastName: '',
    phoneNumber: '',
    // fechaNacimiento: '',
    location: '',
  };

  ngOnInit(): void {
    this.conseguirUsuarioRegistrado();
    window.scrollTo(0, 0);
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
   * Método para cerrar sesión
   */
  cerrarSesion() {
    localStorage.removeItem('jwt');
    this.router.navigate(['home']);
  }

  conseguirUsuarioRegistrado() {
    this.loginService.validarToken().subscribe({
      next: (resp) => {
        this.usuarioRegistrado = resp;
      },
      error: (error) => {},
    });
  }

}
