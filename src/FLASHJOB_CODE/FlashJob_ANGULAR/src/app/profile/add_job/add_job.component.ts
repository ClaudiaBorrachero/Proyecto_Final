import { Categoria } from './../../interfaces/interface';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { CategoriaService } from './../../services/categoria.service';
import { AnuncioService } from 'src/app/services/anuncio.service';
import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';

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
    private anuncioService: AnuncioService,
    private messageService: MessageService,
    ) { }

  /**
  * Definimos variables
  */
  titulo:string='';
  botonRegistro:string = '';
  listaCategorias:Categoria[]=[];
  categoriaDefecto:string="Todas las categorias"
  tipoPrecioDefecto:string="Por horas";

  miFormulario: FormGroup = this.fb.group({
    titulo: ['', [Validators.required]],
    categoria: ['', [ Validators.required]],
    precio: ['', [ Validators.required  ]],
    tipoPrecio: ['', [ Validators.required  ]  ],
    descripcion: [''  ],
    // file: ['', [ Validators.required, Validators.pattern(this.ValidatorRegistroService.nombrePattern)]  ],
    ubicacion: ['' ],
    id:[''],
    imagen:['']
  });

  ngOnInit(): void {
    window.scrollTo(0,0)
    this.miFormulario.reset({
      titulo: '',
      categoria: '',
      precio: '',
      tipoPrecio: '',
      descripcion: '',
      ubicacion: '',
      id:'',
      imagen:''
    })

    this.titulo = "Nuevo Job";
    this.botonRegistro ="Subir anuncio";



    this.mostrarCategorias();
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

  campoNoValido( campo: string ) {

    // this.findInvalidControlsRecursive(this.miFormulario);
    return this.miFormulario.get(campo)?.invalid
    && this.miFormulario.get(campo)?.touched;
  }


}
