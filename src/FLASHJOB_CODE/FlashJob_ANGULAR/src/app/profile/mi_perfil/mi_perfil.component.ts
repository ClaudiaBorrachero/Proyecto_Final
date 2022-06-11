import { UserService } from 'src/app/services/user.service';
import { Byte } from '@angular/compiler/src/util';
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

  miFormularioDatosPerfil: FormGroup = this.fb.group(
    {
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
      phoneNumber: [
        '',
        [
          Validators.required,
          Validators.pattern(this.ValidatorRegistroService.telefonoPattern),
        ],
      ],
    }
  );
  miFormularioDatosPass: FormGroup = this.fb.group(
    {
      passwordOld: [
        '',
        [],
      ],
      location: ['', [Validators.required]],
      passwordNew: [
        '',
        [ Validators.required, Validators.minLength(6)],
      ],
      passwordNew2: [
        '',
        [ Validators.required, Validators.minLength(6)],
      ],
    },
    {
      validators: [this.ValidatorRegistroService.camposIguales('passwordNew', 'passwordNew2')
      // , this.validatorService.validarEmail('email')
    ],
    }
  );


  solucion: string = '';
  // private baseUrl: string = environment.baseUrl;

  constructor(
    private anuncioService: AnuncioService,
    private loginService: LoginService,
    private fb: FormBuilder,
    private ValidatorRegistroService: ValidatorRegistroService,
    private registerService: RegisterService,
    private http: HttpClient,
    private router: Router,
    private usuarioService: UserService
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
  selectedFiles?: FileList;
  currentFile?: any = "NotSelected";
  previsualizarImagen: any='';

  ngOnInit(): void {
    this.conseguirUsuarioRegistrado();
    window.scrollTo(0, 0);

    this.miFormularioDatosPerfil.markAllAsTouched();
  }

/**
   * Este método sirve para obtener los archivos seleccionados
   * @param event
   */
 selectFile(event: any): void {
  this.selectedFiles = event.target.files;
  if (this.selectedFiles){
    const reader = new FileReader();
    reader.onload = (event) => {
      this.previsualizarImagen=event.target?.result;
    }
    reader.readAsDataURL(event.target.files[0]);
  } else {
    this.previsualizarImagen='';
  }
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

  /**
   * Método que llama a getImage del servicio y transforma un array de bytes en una url correspondiente a una imagen
   * @param file
   * @returns
   */
   getImage(file: Byte[]) {
    return this.usuarioService.getImage(file);
  }

  /**
   * Metodo para conseguir el usuario que está logueado
   */

  conseguirUsuarioRegistrado() {
    this.loginService.validarToken().subscribe({
      next: (resp) => {
        this.usuarioRegistrado = resp;
        this.miFormularioDatosPerfil.reset({
          firstName: this.usuarioRegistrado.firstName,
          lastName: this.usuarioRegistrado.lastName,
          phoneNumber: this.usuarioRegistrado.phoneNumber,
          location: this.usuarioRegistrado.location
        });
      },
      error: (error) => {},
    });

  }

  /**
   * Metodo para mostrar mensajes en los campos de editar perfil
   * @param campo
   * @returns
   */
  campoNoValido(campo: string) {
    // this.findInvalidControlsRecursive(this.miFormulario);
    return (
      this.miFormularioDatosPerfil.get(campo)?.invalid &&
      this.miFormularioDatosPerfil.get(campo)?.touched
    );
  }

  /**
   *Metodo para mostrar mensaje en los campos de password
   * @param campo
   * @returns
   */
  campoNoValidoPass(campo: string) {
    // this.findInvalidControlsRecursive(this.miFormulario);
    return (
      this.miFormularioDatosPass.get(campo)?.invalid &&
      this.miFormularioDatosPass.get(campo)?.touched
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
    this.updateProfile();

    this.miFormularioDatosPerfil.markAllAsTouched();
  }

  /**
   * Metodo para subir el formulario de actualizar contraseña
   */
  submitFormularioPass() {
    const passEdit: any = {
      passwordOld: this.miFormularioDatosPass.get('passwordOld')?.value,
      passwordNew: this.miFormularioDatosPass.get('passwordNew')?.value,
      passwordNew2: this.miFormularioDatosPass.get('passwordNew2')?.value
  }


  this.usuarioService.updatePass(passEdit).subscribe({

    next:resp => {

      location.reload();
  },
  error: (err: any) => {
    //  localStorage.removeItem('jwt');
    Swal.fire({
      title: 'Error al editar perfil',
      text: 'Vuelve a intentarlo',
      icon: 'error',
      confirmButtonText: 'Ok'
    })
    this.currentFile = undefined;
  }
  });
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
  // register() {
  //   let respuesta: LoginRespuesta = {};
  //   let solucion: string;
  //   const user = {
  //     firstName: this.miFormularioDatosPerfil.get('firstName')?.value,
  //     lastName: this.miFormularioDatosPerfil.get('lastName')?.value,
  //     phoneNumber: this.miFormularioDatosPerfil.get('phoneNumber')?.value,
  //     location: this.miFormularioDatosPerfil.get('location')?.value,
  //     // "fechaNacimiento": this.miFormulario.get("nacimiento")?.value,
  //   };
  //   this.registerService.register(user).subscribe({
  //     next: (resp) => {
  //       respuesta = resp;
  //       if (respuesta.jwt_token != null) {
  //         localStorage.setItem('jwt', respuesta.jwt_token);
  //         this.router.navigate(['home']);
  //         solucion = 'true';
  //       }
  //     },
  //     error(error) {
  //       solucion = 'error';
  //       localStorage.removeItem('jwt');
  //       Swal.fire({
  //         title: 'Error al inciar sesión',
  //         text: 'Vuelve a intentarlo',
  //         icon: 'error',
  //         confirmButtonText: 'Ok',
  //       });
  //     },
  //   });
  // }


  updateProfile() {
    if (this.selectedFiles) {

      const file: File | null = this.selectedFiles.item(0);
      if (file) {
        this.currentFile = file;
      }
    }

        let respuesta: any = {};
        let solucion: string;
    const userEditar: any = {
      firstName: this.miFormularioDatosPerfil.get('firstName')?.value,
      lastName: this.miFormularioDatosPerfil.get('lastName')?.value,
      phoneNumber: this.miFormularioDatosPerfil.get('phoneNumber')?.value,
      location: this.miFormularioDatosPerfil.get('location')?.value,
  }


  this.usuarioService.updateProfile(userEditar, this.currentFile, this.usuarioRegistrado.email).subscribe({

    next:resp => {
      respuesta = resp;
      // this.anuncioEditadoCorrectamente();
      console.log(resp);
      this.miFormularioDatosPerfil.reset()
      location.reload();
  },
  error: (err: any) => {
    solucion = "error";
    //  localStorage.removeItem('jwt');
    Swal.fire({
      title: 'Error al editar perfil',
      text: 'Vuelve a intentarlo',
      icon: 'error',
      confirmButtonText: 'Ok'
    })
    this.currentFile = undefined;
  },
})
// }
this.selectedFiles = undefined;
// }
  }



}
