import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: false,
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  mensajeLogin: string = '';
  logueado: boolean = false;

  // Para almacenar los errores de validación
  errores: { [key: string]: string } = {};

  private apiUrl = '/api/auth/login';

  constructor(private http: HttpClient, private router: Router) { }

  loguearUsuario(): void {
    this.errores = {};
    this.mensajeLogin = '';
    this.logueado = false;

    const credentials = { email: this.email, password: this.password };

    this.http.post<any>(this.apiUrl, credentials).subscribe(
      (response) => {
        localStorage.setItem('usuario', JSON.stringify(response));
        this.mensajeLogin = 'Bienvenido a tu perfil, ' + this.email;
        this.logueado = true;
        setTimeout(() => {
          this.router.navigate(['/homeRegistrado']);
        }, 1000);
      },
      (error) => {
        if (error.status === 400 && error.error) {
          // Si hay un array de errores de validación, los mostramos todos juntos
          if (Array.isArray(error.error.errors)) {
            this.mensajeLogin = error.error.errors.join('\n');
          } else if (error.error.message) {
            // Si es un mensaje único (por ejemplo, usuario no existe o contraseña incorrecta)
            this.mensajeLogin = error.error.message;
          } else {
            this.mensajeLogin = 'Error inesperado. Inténtalo de nuevo.';
          }
        } else {
          this.mensajeLogin = 'Error inesperado. Inténtalo de nuevo.';
        }
        this.logueado = false;
      }
    );
  }
}