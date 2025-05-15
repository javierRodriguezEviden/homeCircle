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

  private apiUrl = '/api/auth/login';

  constructor(private http: HttpClient, private router: Router) {}

  loguearUsuario(): void {
    // Limpiar mensajes anteriores
    this.mensajeLogin = '';

    

    // Datos a enviar
    const credentials = { email: this.email, password: this.password };

    // Llamada al backend
    this.http.post<any>(this.apiUrl, credentials).subscribe(
      (response) => {
        console.log('Login exitoso', response);

        // Guardamos el usuario en localStorage
        localStorage.setItem('usuario', JSON.stringify(response));

        // Mostrar mensaje de bienvenida
        this.mensajeLogin = 'Bienvenido a tu perfil, ' + this.email;
        this.logueado = true;

        // Redirigir después de un breve delay
        setTimeout(() => {
          this.router.navigate(['/homeRegistrado']); // Ruta protegida
        }, 1000);
      },
      (error) => {
        console.error('Error al iniciar sesión', error);
        this.mensajeLogin = 'Credenciales incorrectas. Inténtalo de nuevo.';
        this.logueado = false;
      }
    );
  }
}