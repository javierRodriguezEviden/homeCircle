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

  nombre: string = '';
  apellido: string = '';
  dni: string = '';
  telefono: string = '';
  sede: string = '';
  cuenta_banco:string = '';
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

    const credentials = { email: this.email, password: this.password, name: this.nombre, surname: this.apellido, dni: this.dni, phone: this.telefono, sede: this.sede, cuenta_banco: this.cuenta_banco };

    this.http.post<any>(this.apiUrl, credentials).subscribe({
  next: (response) => {
    console.log('Respuesta del backend:', response);

    const nombreRecibido = response.name || response.nombre; // 👈 Comprueba cómo te devuelve el nombre el backend
    const apellidoRecibido = response.surname || response.apellidos; // 👈 Comprueba cómo te devuelve el apellido el backend
    const dniRecibido = response.dni || response.dni; // 👈 Comprueba cómo te devuelve el dni el backend
    const telefonoRecibido = response.phone || response.telefono; // 👈 Comprueba cómo te devuelve el teléfono el backend
    const sedeRecibida = response.sede || response.sede; // 👈 Comprueba cómo te devuelve la sede el backend
    const cuenta_bancoRecibida = response.cuenta_banco || response.cuenta_banco; // 👈 Comprueba cómo te devuelve la cuenta bancaria el backend


    localStorage.setItem('token', response.token);
    localStorage.setItem('usuario', JSON.stringify({
      email: this.email,
      name: nombreRecibido, // 👈 Usa el nombre del backend si existe
      apellidos: apellidoRecibido, // 👈 Usa el apellido del backend si existe
      dni: dniRecibido, // 👈 Usa el dni del backend si existe
      telefono: telefonoRecibido, // 👈 Usa el teléfono del backend si existe
      sede: sedeRecibida, // 👈 Usa la sede del backend si existe
      cuenta_banco: cuenta_bancoRecibida // 👈 Usa la cuenta bancaria del backend si existe

    }));

    this.authService.login({
      email: this.email,
      password: this.password,
      nombre: nombreRecibido,
      apellidos: apellidoRecibido,
      dni: dniRecibido,
      sede: sedeRecibida,
      telefono: telefonoRecibido,
      cuenta_banco: cuenta_bancoRecibida
    });

    this.router.navigate(['/homeRegistrado']);
  },
  error: (error) => {
    console.error('Error al iniciar sesión:', error);
  }
});

}
}