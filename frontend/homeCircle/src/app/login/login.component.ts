import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  mensajeLogin="";
  email="";
  logueado=false;

  loguearUsuario(){
    this.logueado=true;
    this.mensajeLogin="Bievenido a tu perfil, " +this.email;
  }


}
