import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service'; // ✅ Importa el servicio

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: false
})
export class LoginComponent {

  email: string = '';
  password: string = '';
  mensajeLogin: string = '';
  logueado: boolean = false;

  // Para almacenar los errores de validación
  errores: { [key: string]: string } = {};

  private apiUrl = '/api/auth/login';

  constructor(
    private http: HttpClient,
    private router: Router,
    private authService: AuthService // ✅ Inyecta el servicio
  ) { }

  // Método para iniciar sesión
  loguearUsuario(): void {
    this.errores = {};
    this.mensajeLogin = '';
    this.logueado = false;

    const credentials = { email: this.email, password: this.password };

    this.http.post<any>(this.apiUrl, credentials).subscribe(
      (response) => {
        // Si la autenticación es exitosa
        console.log('Login exitoso', response);

        // Guardamos el usuario en localStorage
        localStorage.setItem('usuario', JSON.stringify(response));

        console.log('Login exitoso', response);

        // ✅ Usamos el servicio para guardar el estado
        this.authService.login(response);

        this.mensajeLogin = 'Bienvenido a tu perfil, ' + this.email;
        this.logueado = true;

        // Redirigir después de un breve delay
        setTimeout(() => {
          this.router.navigate(['/homeRegistrado']);
        }, 0);
      },
      (error) => {
        // Manejo de errores de validación y autenticación
        if (error.status === 400 && error.error) {
          // Si hay un array de errores de validación, los mostramos todos juntos con salto de línea
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
