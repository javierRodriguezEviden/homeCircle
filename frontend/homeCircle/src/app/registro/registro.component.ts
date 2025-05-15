import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

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
  confirmPassword = '';
  dni = '';
  telefono = '';
  sede = '';
  cuenta_banco = '';

  mensajeRegistro = '';
  registrado = false;

  // Para almacenar los errores de validación
  errores: { [key: string]: string } = {};

  constructor(private http: HttpClient, private router: Router) { }

  registrarUsuario(event: Event) {
    event.preventDefault(); // Evita que el formulario recargue la página

    this.errores = {}; // Reinicia los errores

    // Verifica que las contraseñas coincidan
    if (this.password !== this.confirmPassword) {
      this.errores['confirmPassword'] = 'Las contraseñas no coinciden.';
      return;
    }

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
      next: (response:any) => {
        this.registrado = true;
        this.mensajeRegistro = 'Registro exitoso. Bienvenido a HomeCircle.';
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2000);
      },
      error: (error) => {
        if (error.status === 400) {
          this.registrado = false;
          this.errores = error.error; // Muestra el mensaje del backend
          this.mensajeRegistro = error.error.message;
        } else {
          this.mensajeRegistro = 'Error inesperado. Inténtalo de nuevo.';
          this.registrado = false;
        }
      }
    });
  }
}
