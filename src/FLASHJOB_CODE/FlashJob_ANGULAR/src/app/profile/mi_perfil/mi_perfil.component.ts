import { LoginService } from './../../services/login.service';
import { AnuncioService } from 'src/app/services/anuncio.service';
import { environment } from './../../../environments/environment.prod';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { RegisterService } from './../../services/register.service';
import { ValidatorRegistroService } from './../../services/validatorRegistro.service';
import { Validators, FormGroup, FormBuilder } from '@angular/forms';
import { LoginRespuesta, Usuario } from 'src/app/interfaces/interface';
import { Component, OnInit } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-mi_perfil',
  templateUrl: './mi_perfil.component.html',
  styleUrls: ['./mi_perfil.component.css'],
})
export class Mi_perfilComponent implements OnInit {
  miFormulario: FormGroup = this.fb.group(
    {
      email: [
        '',
        [
          Validators.required,
          Validators.pattern(this.ValidatorRegistroService.emailPattern),
        ],
        [this.ValidatorRegistroService],
      ],
      password: ['', [Validators.required, Validators.minLength(6)]],
      password2: ['', [Validators.required]],
      lastName: [
        '',
        [
          Validators.required,
          Validators.pattern(this.ValidatorRegistroService.apellidosPattern),
        ],
      ],
      location: ['', [Validators.required]],
      firstName: [
        '',
        [
          Validators.required,
          Validators.pattern(this.ValidatorRegistroService.nombrePattern),
        ],
      ],
      // nacimiento: ['', [ Validators.required]  ],
      phoneNumber: [
        '',
        [
          Validators.required,
          Validators.pattern(this.ValidatorRegistroService.telefonoPattern),
        ],
      ],
    },
    {
      validators: [
        this.ValidatorRegistroService.camposIguales('password', 'password2'),
        // , this.validatorService.validarEmail('email')
      ],
    }
  );

  solucion: string = '';
  // private baseUrl: string = environment.baseUrl;

  get emailErrorMsg(): string {
    const errors = this.miFormulario.get('email')?.errors!;
    if (errors['required']) {
      return 'Email es obligatorio';
    } else if (errors['pattern']) {
      return 'El valor ingresado no tiene formato de correo';
    } else if (errors['emailTomado']) {
      return 'El email ya está en uso';
    }

    return '';
  }

  constructor(
    private anuncioService: AnuncioService,
    private loginService: LoginService,
    private fb: FormBuilder,
    private ValidatorRegistroService: ValidatorRegistroService,
    private registerService: RegisterService,
    private http: HttpClient,
    private router: Router
  ) {}

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
    this.miFormulario.reset({
      email: '',
      password: '',
      password2: '',
      lastName: '',
      location: '',
      // nacimiento: ''
    });
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

  campoNoValido(campo: string) {
    // this.findInvalidControlsRecursive(this.miFormulario);
    return (
      this.miFormulario.get(campo)?.invalid &&
      this.miFormulario.get(campo)?.touched
    );
  }

  /**
   * Metodo para ver donde falla el formulario
   */
  // public findInvalidControlsRecursive(formToInvestigate:FormGroup|FormArray):string[] {
  //   var invalidControls:string[] = [];
  //   let recursiveFunc = (form:FormGroup|FormArray) => {
  //     Object.keys(form.controls).forEach(field => {
  //       const control = form.get(field);
  //       if (control?.invalid) invalidControls.push(field);
  //       if (control instanceof FormGroup) {
  //         recursiveFunc(control);
  //       } else if (control instanceof FormArray) {
  //         recursiveFunc(control);
  //       }
  //     });
  //   }
  //   recursiveFunc(formToInvestigate);
  //   console.log(invalidControls)
  //   console.log(this.miFormulario.valid + "gfgdfgdgdfgfdgfdgdfg")
  //   return invalidControls;
  // }

  submitFormulario() {
    this.register();

    this.miFormulario.markAllAsTouched();
  }

  comprobarRespuestaLogin() {
    if (this.solucion == 'true') {
    } else if (this.solucion == 'incorrect') {
    } else {
    }
  }

  /**
   * Este metodo recibira un usuario del formulario y llamara
   * a registerService para realizar una peticion post de añadir
   * el usuario
   */
  register() {
    let respuesta: LoginRespuesta = {};
    let solucion: string;
    const user = {
      email: this.miFormulario.get('email')?.value,
      password: this.miFormulario.get('password')?.value,
      firstName: this.miFormulario.get('firstName')?.value,
      lastName: this.miFormulario.get('lastName')?.value,
      phoneNumber: this.miFormulario.get('phoneNumber')?.value,
      location: this.miFormulario.get('location')?.value,
      // "fechaNacimiento": this.miFormulario.get("nacimiento")?.value,
    };
    this.registerService.register(user).subscribe({
      next: (resp) => {
        respuesta = resp;
        if (respuesta.jwt_token != null) {
          localStorage.setItem('jwt', respuesta.jwt_token);
          this.router.navigate(['home']);
          solucion = 'true';
        }
      },
      error(error) {
        solucion = 'error';
        localStorage.removeItem('jwt');
        Swal.fire({
          title: 'Error al inciar sesión',
          text: 'Vuelve a intentarlo',
          icon: 'error',
          confirmButtonText: 'Ok',
        });
      },
    });
  }
}
