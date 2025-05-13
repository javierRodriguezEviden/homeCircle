import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-registro',
  standalone: false,
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css'] // Asegúrate de que sea "styleUrls" en plural
})
export class RegistroComponent {
  nombre = '';
  apellidos = '';
  email = '';
  password = '';
  dni = '';
  telefono = '';
  sede = '';
  cuenta_banco = '';

  mensajeRegistro = '';
  registrado = false;

  constructor(private http: HttpClient) { }

  registrarUsuario(event: Event) {
    event.preventDefault(); // Evita que el formulario recargue la página


    const userData = {
      nombre: this.nombre,
      apellidos: this.apellidos,
      email: this.email,
      password: this.password,
      dni: this.dni,
      telefono: this.telefono,
      sede: this.sede,
      cuenta_banco: this.cuenta_banco
    };

    this.http.post('http://localhost:8020/auth/register', userData).subscribe({
      next: (response) => {
        this.registrado = true;
        this.mensajeRegistro = 'Registro exitoso. Bienvenido a HomeCircle.';
      },
      error: (error) => {
        if (error.status === 400) {
          this.mensajeRegistro = error.error; // Muestra el mensaje del backend
        } else {
          this.mensajeRegistro = 'Error inesperado. Inténtalo de nuevo.';
        }
      }
    });
  }
}