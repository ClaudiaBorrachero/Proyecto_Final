import { Component, OnInit } from '@angular/core';
import { FormArray, Validators } from '@angular/forms';
import { FormBuilder, FormGroup} from '@angular/forms';
import { ValidatorRegistroService } from 'src/app/services/validatorRegistro.service';
import { LoginRespuesta } from 'src/app/interfaces/interface';
import { environment } from '../../../environments/environment';
import Swal from 'sweetalert2';
import { HttpClient } from '@angular/common/http';
import { RegisterService } from 'src/app/services/register.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  miFormulario: FormGroup = this.fb.group({
  email: ['', [Validators.required, Validators.pattern(this.ValidatorRegistroService.emailPattern)],[this.ValidatorRegistroService]],
  password: ['', [ Validators.required, Validators.minLength(6)]  ],
  password2: ['', [ Validators.required]  ],
  lastName: ['', [ Validators.required, Validators.pattern(this.ValidatorRegistroService.apellidosPattern)]  ],
  location: ['', [ Validators.required]  ],
  firstName: ['', [ Validators.required, Validators.pattern(this.ValidatorRegistroService.nombrePattern)]  ],
  // nacimiento: ['', [ Validators.required]  ],
  phoneNumber: ['', [ Validators.required, Validators.pattern(this.ValidatorRegistroService.telefonoPattern)]]
},
{
  validators: [this.ValidatorRegistroService.camposIguales('password', 'password2')
  // , this.validatorService.validarEmail('email')
],
}
);

solucion: string = "";
private baseUrl: string = environment.baseUrl;




get emailErrorMsg(): string {

  const errors = this.miFormulario.get('email')?.errors!;
  if ( errors['required'] ) {
    return 'Email es obligatorio';
  } else if ( errors['pattern'] ) {
    return 'El valor ingresado no tiene formato de correo';
  }
  else if ( errors['emailTomado'] ) {
    return 'El email ya está en uso';
  }

  return '';
}





constructor( private fb: FormBuilder,
  private ValidatorRegistroService: ValidatorRegistroService,
  private registerService: RegisterService,
  private http: HttpClient,
  private router:Router ) { }


  ngOnInit(): void {

    this.miFormulario.reset({
      email: '',
      password: '',
      password2: '',
      lastName: '',
      location: ''
    })
  }
  campoNoValido( campo: string ) {

    // this.findInvalidControlsRecursive(this.miFormulario);
    return this.miFormulario.get(campo)?.invalid
    && this.miFormulario.get(campo)?.touched;
  }

    submitFormulario() {

      this.register()


      this.miFormulario.markAllAsTouched();

    }

    comprobarRespuestaLogin(){
      if(this.solucion == "true"){

      }
      else if(this.solucion == "incorrect"){

      }
      else{

      }
    }


    /**
     * Este metodo recibira un usuario del formulario y llamara
     * a registerService para realizar una peticion post de añadir
     * el usuario
     */
    register(){
      let respuesta: LoginRespuesta = {};
      let solucion: string;
      const user = {
        "email": this.miFormulario.get("email")?.value,
        "password": this.miFormulario.get("password")?.value,
        "firstName": this.miFormulario.get("firstName")?.value,
        "lastName": this.miFormulario.get("lastName")?.value,
        "phoneNumber": this.miFormulario.get("phoneNumber")?.value,
        "location": this.miFormulario.get("location")?.value,
        // "fechaNacimiento": this.miFormulario.get("nacimiento")?.value,


    }
    this.registerService.register(user).subscribe({

      next:resp => {
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
         title: 'Error al realizar el registro',
         text: 'Vuelve a intentarlo',
         icon: 'error',
         confirmButtonText: 'Ok'
       })
     }




   })
    }





}


