import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home-registrado',
  standalone: false, // Esto significa que usas módulos (NgModule), no standalone API
  templateUrl: './home-registrado.component.html',
  styleUrls: ['./home-registrado.component.css'] // Usa "styleUrls" si es un array
})
export class HomeRegistradoComponent implements OnInit {

  // Variables para almacenar los datos del usuario
  nombreUsuario: string | null = null;

  // Método que se ejecuta al inicializar el componente
  ngOnInit(): void {

    this.nombreUsuario = localStorage.getItem('nombre') || 'Usuario';


  }
}