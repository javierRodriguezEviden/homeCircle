import { Component } from '@angular/core';
import { AuthService } from './auth.service'; // ✅ Importa el servicio
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  standalone: false
})
export class AppComponent {

  estaLogueado = false;

  constructor(private authService: AuthService, private router: Router) {}

 ngOnInit(): void {
    this.authService.isLoggedIn$.subscribe(loggedIn => {
      this.estaLogueado = loggedIn; // ✅ Actualizamos el valor aquí
      console.log('Estado de login:', this.estaLogueado);
    });
  }

  logout() {
    this.authService.logout(); // ✅ Llama al logout del servicio
    this.estaLogueado = false; // ✅ Actualiza el estado
    //Se puede quitar, el boton funciona perfectamente sin esto, pero con esto reconducimos a la pagina del login.
    this.router.navigate(['/login'])
  }
}