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

  // Variables para el formulario y mensajes
  email: string = '';
  password: string = '';
  mensajeLogin: string = '';
  logueado: boolean = false;
  errores: { [key: string]: string } = {};

  // URL del backend para login
  private apiUrl = 'http://localhost:8020/auth/login';

  // Inyecta servicios de Angular y tu AuthService
  constructor(
    private http: HttpClient,
    private router: Router,
    private authService: AuthService
  ) { }

  private decodificarToken(token: string): any {
    try {
      const payload = token.split('.')[1];
      const decoded = atob(payload.replace(/-/g, '+').replace(/_/g, '/'));
      return JSON.parse(decoded);
    } catch (e) {
      return null;
    }
  }

  // Método que se llama al hacer login
  loguearUsuario(): void {
    this.errores = {};
    this.mensajeLogin = '';
    this.logueado = false;

    // Crea el objeto con email y password
    const credentials = { email: this.email, password: this.password };

    // Hace la petición POST al backend para autenticar
    this.http.post<any>(this.apiUrl, credentials).subscribe(
      (response) => {
        localStorage.setItem('token', response.token);

        // Decodifica el token para obtener los datos del usuario
        const datosUsuario = this.decodificarToken(response.token);
        if (datosUsuario) {
          localStorage.setItem('email', datosUsuario.sub); // email del usuario
          localStorage.setItem('nombre', datosUsuario.nombre); // nombre del usuario
        }

        //!Console log de prueba
        //console.log('Usuario logueado response:', response);
        //console.log('Datos extraídos del token:', datosUsuario);

        this.authService.usuarioLogueado.next(true);
        this.mensajeLogin = response.message;
        setTimeout(() => {
          this.router.navigate(['/homeRegistrado']);
        }, 0);
      },
      (error) => {
        // Si hay error, muestra el mensaje correspondiente
        if (error.status === 400 && error.error) {
          if (Array.isArray(error.error.errors)) {
            this.mensajeLogin = error.error.errors.join('\n');
          } else if (error.error.message) {
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
