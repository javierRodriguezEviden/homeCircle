import { Component, OnInit } from '@angular/core';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

// Decorador que define el componente principal de la app
@Component({
  selector: 'app-root', // Nombre del selector para usar este componente en HTML
  templateUrl: './app.component.html', // Ruta al archivo de plantilla HTML
  styleUrls: ['./app.component.css'],   // Ruta al archivo de estilos CSS
  standalone: false
})
export class AppComponent implements OnInit {
  title(title: any) {
    throw new Error('Method not implemented.');
  }

  // Variable que indica si el usuario está logueado
  estaLogueado = false;

  // Inyecta el servicio de autenticación y el router de Angular
  constructor(private authService: AuthService, private router: Router) {}

  // Método que se ejecuta al iniciar el componente
  ngOnInit(): void {
    // Se suscribe al observable del estado de autenticación
    this.authService.estaLogueado$.subscribe(loggedIn => {
      this.estaLogueado = loggedIn; // Actualiza la variable local según el estado
      console.log('Estado de autenticación, app.component, variable this.estaLogueado:', this.estaLogueado);
    });
  }

  // Método para cerrar sesión
  logout(): void {
    this.authService.logout(); // Llama al método logout del servicio (borra datos y emite false)
    this.router.navigate(['/login']); // Redirige al usuario a la página de login
  }
}