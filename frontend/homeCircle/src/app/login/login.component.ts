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
        // Si el login es correcto, guarda usuario y token en localStorage
        localStorage.setItem('usuario', JSON.stringify(response));
        localStorage.setItem('token', response.token);

        // Llama al servicio de autenticación para actualizar el estado
        this.authService.login(credentials).subscribe(
          (response) => {
            // Si todo va bien, vuelve a guardar token y usuario
            localStorage.setItem('token', response.token);
            localStorage.setItem('usuario', JSON.stringify({ email: credentials.email }));
            // Marca como logueado y muestra mensaje
            // (No deberías acceder a usuarioLogueado directamente, mejor hacerlo solo en el servicio)
            this.authService.usuarioLogueado.next(true);
            this.mensajeLogin = 'Bienvenido a tu perfil, ' + this.email;
            // Redirige al usuario a la página principal
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
      },
    );
  }
}
