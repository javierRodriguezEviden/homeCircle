import { Component } from '@angular/core';

@Component({
  selector: 'app-registro',
  standalone: false,
  templateUrl: './registro.component.html',
  styleUrl: './registro.component.css'
})
export class RegistroComponent {

  mensajeRegistro="";
  registrado = false;
  nombre ="";
  apellidos ="";

  registrarUsuario() {

    this.registrado=true;
    this.mensajeRegistro ="Bienvendido "+this.nombre+" "+this.apellidos+" a HomeCircle, tu hogar en la nube." ;
}
}
