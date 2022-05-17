import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { LoginRespuesta } from 'src/app/interfaces/interface';

import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

datosCorrectos:boolean=false;
public emailPattern: string = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";

@ViewChild('miFormulario') miFormulario!: NgForm;

initForm = {
  email: "",
  password: ""
}

constructor(private router:Router, private loginService:LoginService) { }

ngOnInit(): void {
}

emailValido(): boolean {
  return  this.miFormulario?.controls['email']?.touched && this.miFormulario?.controls['email']?.invalid;
}

passValido():boolean {
  return this.miFormulario?.controls['password']?.touched && this.miFormulario?.controls['password']?.invalid
}

submitFormulario() {
  if(this.miFormulario.valid){
    this.login();
  }
}

login(){
        let solucion: string;
        let respuesta: LoginRespuesta = {};


    let email = this.miFormulario.value.email;
    let password= this.miFormulario.value.password;
        this.loginService.login(email, password).subscribe({

          next: resp => {
            respuesta = resp;
            if(respuesta.jwt_token != null){
              localStorage.setItem('jwt', respuesta.jwt_token);
              this.router.navigate(['home']);
              solucion = "true";
            }
          },
          error(error){
            solucion = "error";
            localStorage.removeItem('jwt');
            Swal.fire({
              title: 'Error al inciar sesión',
              text: 'Los datos introducidos no son correctos',
              icon: 'error',
              confirmButtonText: 'Ok'
            })
          }




        })

}

camposVacios(){

  if(this.miFormulario.value.email=="" && this.miFormulario.value.password==""){

    this.datosCorrectos=true;
  }
}
}


