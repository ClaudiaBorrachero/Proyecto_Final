import { LoginService } from 'src/app/services/login.service';
import { Usuario } from './../../interfaces/interface';
import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { AnuncioService } from 'src/app/services/anuncio.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  @Input() busqueda: string='';
  constructor(private anuncioService:AnuncioService,private router:Router, private loginService:LoginService) { }

  public isLogged : boolean = false;
  userRegistered : Usuario ={
    email: '',
    password: '',
    firstName: '',
    lastName: '',
    phoneNumber: '',
    location: ''
  }

  ngOnInit():void {
    this.onCheckUser();
  }

  onCheckUser():void{
    this.loginService.validarToken().subscribe({

      next: resp => {
        this.userRegistered = resp;
      }, error(error){

      }

    })
  }

}
