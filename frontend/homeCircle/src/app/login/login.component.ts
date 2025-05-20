import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

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
  errores: { [key: string]: string } = {};

  private apiUrl = 'http://localhost:8020/auth/login'; // Asegúrate de que sea la URL correcta

  constructor(
    private http: HttpClient,
    private router: Router,
    private authService: AuthService
  ) { }

  loguearUsuario(): void {
    this.errores = {};
    this.mensajeLogin = '';
    this.logueado = false;

    const credentials = { email: this.email, password: this.password};

    this.http.post<any>(this.apiUrl, credentials).subscribe(
      (response) => {
        // Si la autenticación es exitosa
        console.log('Login exitoso', response);

        // Guardamos el usuario en localStorage
        localStorage.setItem('usuario', JSON.stringify(response));
        localStorage.setItem('token', response.token);
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
