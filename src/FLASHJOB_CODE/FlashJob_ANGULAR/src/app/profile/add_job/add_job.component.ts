import { Anuncio } from 'src/app/interfaces/interface';
import Swal from 'sweetalert2';
import { Categoria, LoginRespuesta } from './../../interfaces/interface';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { CategoriaService } from './../../services/categoria.service';
import { AnuncioService } from 'src/app/services/anuncio.service';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
// import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-add_job',
  templateUrl: './add_job.component.html',
  styleUrls: ['./add_job.component.css']
})
export class Add_JobComponent implements OnInit {

  constructor(private fb: FormBuilder,
    private http: HttpClient,
    private router:Router,
    private categoriaService:CategoriaService,
    private anuncioService: AnuncioService
    // private messageService: MessageService,
    ) { }


//Variables editar anuncio
  //Comprobamos si es un anuncio para editar o no
  @Input() edit=false;

  //Con esto conseguimos el anuncio que queremos editar, el cual lleva consigo el id del anuncio
  @Input() anuncioEditar:Anuncio= {};
  titulo:string='';
  botonRegistro:string = '';
  /**
   * Lo emitimos para que se recargue el componente de mostrar los anuncios solicitados, una vez que hemos actualizado el anuncio
   */
   @Output()
   recargarListado = new EventEmitter();

  /**
  * Definimos variables
  */
  listaCategorias:Categoria[]=[];
  categoriaDefecto:string="Todas las categorias"
  // tipoPrecioDefecto:string="Por horas";
  selectedFiles?: FileList;
  currentFile?: any = "NotSelected";

  miFormulario: FormGroup = this.fb.group({
    title: ['', [Validators.required]],
    category: ['', [ Validators.required]],
    price: ['', [ Validators.required  ]],
    // tipoPrecio: ['', [ Validators.required  ]  ],
    description: [''  ],
    // file: ['', [ Validators.required, Validators.pattern(this.ValidatorRegistroService.nombrePattern)]  ],
    location: ['' ],
    id:[''],
    file:['']
  });

  ngOnInit(): void {
    window.scrollTo(0,0)
    this.miFormulario.reset({
      title: '',
      category: '',
      price: '',
      // tipoPrecio: '',
      description: '',
      location: '',
      id:'',
      file:''
    })

    this.titulo = "Añade un nuevo Job";
    this.botonRegistro ="Subir anuncio";

    this.mostrarCategorias();
  }

   /**
     * Si accedemos a este componente a través de editar anuncio, rellenaremos los campos con el anuncio que nos hemos traido
     */
    ngOnChanges(): void{
      this.miFormulario.reset({
        title: this.anuncioEditar.title,
        category: this.anuncioEditar.categoryJ?.name,
        price: this.anuncioEditar.price,
        description: this.anuncioEditar.description,
        location: this.anuncioEditar.location,
        id: this.anuncioEditar.id

      })
      console.log(this.miFormulario.get("category")?.value);
      this.categoriaDefecto=this.miFormulario.get("category")?.value,
      this.titulo= "Editar Job";
      this.botonRegistro = "Actualizar anuncio"
    }

  /**
   * Este método sirve para obtener los archivos seleccionados
   * @param event
   */
   selectFile(event: any): void {
    this.selectedFiles = event.target.files;
  }

  /**
       * Este metodo recibira un usuario del formulario y llamara
       * a registerService para realizar una peticion post de añadir
       * el usuario
       */
   addJob(){

      if (this.selectedFiles) {

        const file: File | null = this.selectedFiles.item(0);
        if (file) {
          this.currentFile = file;
        }
      }
          let respuesta: Anuncio = {};
          let solucion: string;
      const anuncio = {
        "title": this.miFormulario.get("title")?.value,
        "category": this.miFormulario.get("category")?.value,
        "price": this.miFormulario.get("price")?.value,
        // "tipoPrecio": this.miFormulario.get("tipoPrecio")?.value,
        "description": this.miFormulario.get("description")?.value,
        "location": this.miFormulario.get("location")?.value
    }

    console.log("joderrrrr"+this.miFormulario.get("category")?.value)

    this.anuncioService.addAnuncio(anuncio, this.currentFile).subscribe({

      next:resp => {
        respuesta = resp;
        // this.anuncioEditadoCorrectamente();
        console.log(resp);
        this.miFormulario.reset()
        location.replace(`anuncio-detalle/${respuesta.id}`);
      //  if(respuesta.jwt_token != null){
      //    localStorage.setItem('jwt', respuesta.jwt_token);
      //    this.router.navigate(['home']);
      //    solucion = "true";
      //  }
    },
    error: (err: any) => {
      solucion = "error";
      //  localStorage.removeItem('jwt');
      Swal.fire({
        title: 'Error al crear anuncio',
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

    /**
     * Metodo para editar un anuncio, recibimos un anuncio modificado y hacemos la llamada mandando el anuncio que queremos modificar y el
     * nuevo anuncio con los cambios modificados
     */
    editarAnuncio(){
      if (this.selectedFiles) {

        const file: File | null = this.selectedFiles.item(0);
        if (file) {
          this.currentFile = file;
        }
      }
    let respuesta: LoginRespuesta = {};
    let solucion: string;
    const anuncio = {
      "title": this.miFormulario.get("title")?.value,
      "category": this.miFormulario.get("category")?.value,
      "price": this.miFormulario.get("price")?.value,
      "description": this.miFormulario.get("description")?.value,
      "location": this.miFormulario.get("location")?.value,
      "id":this.miFormulario.get("id")?.value




    }
    this.anuncioService.editAnuncio(anuncio, this.currentFile).subscribe({

      next:resp => {
        respuesta = resp;

        //emitimos el evento para que se recargue el componente
        this.recargarListado.emit("");
        // window.location.reload();

      },
      error: (err: any) => {
        solucion = "error";
        //  localStorage.removeItem('jwt');
        Swal.fire({
          title: 'Error al editar anuncio',
          text: 'Vuelve a intentarlo',
          icon: 'error',
          confirmButtonText: 'Ok'
        })
        this.currentFile = undefined;
      }
    })

  this.selectedFiles = undefined;

}

  /**
   * Metodo para mostrar la lista de categorias, la cual la recibe del servicio categoriaService
   */
  mostrarCategorias(){
    this.categoriaService.mostrarCategorias()
    .subscribe( resp => {
      this.listaCategorias=resp;

    })
  }

  /**
       * Este metodo llamará al metodo añadirAnuncio y marcará todos los campos del formulario como marcados
       */
   submitFormulario() {

    if(this.botonRegistro == 'Actualizar anuncio'){
      console.log("claudia guapa")
      this.editarAnuncio();
    }
    else{
      this.addJob()

    }


    this.miFormulario.markAllAsTouched();

  }

  campoNoValido( campo: string ) {

    // this.findInvalidControlsRecursive(this.miFormulario);
    return this.miFormulario.get(campo)?.invalid
    && this.miFormulario.get(campo)?.touched;
  }


}
