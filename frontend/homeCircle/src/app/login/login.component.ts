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

  private apiUrl = '/api/auth/login';

  constructor(
    private http: HttpClient,
    private router: Router,
    private authService: AuthService // ✅ Inyecta el servicio
  ) {}

  loguearUsuario(): void {
  this.mensajeLogin = '';

  const credentials = { email: this.email, password: this.password };

  this.http.post<any>(this.apiUrl, credentials).subscribe(
    (response) => {
      console.log('Login exitoso', response);

      // ✅ Usamos el servicio para guardar el estado
      this.authService.login(response);

      this.mensajeLogin = 'Bienvenido a tu perfil, ' + this.email;
      this.logueado = true;

      setTimeout(() => {
        this.router.navigate(['/homeRegistrado']);
      }, 0);
    },
    (error) => {
      console.error('Error al iniciar sesión', error);
      this.mensajeLogin = 'Credenciales incorrectas. Inténtalo de nuevo.';
      this.logueado = false;
    }
  );
}
}