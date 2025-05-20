import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sobre-nosotros',
  standalone: false,
  templateUrl: './sobre-nosotros.component.html',
  styleUrl: './sobre-nosotros.component.css'
})
export class SobreNosotrosComponent implements OnInit {

  estaLogueado = false;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    // Escuchar cambios en el estado de autenticación
    this.authService.estaLogueado$.subscribe(loggedIn => {
      this.estaLogueado = loggedIn;
      console.log('Estado de autenticación:', this.estaLogueado);
    });
  }

  logout(): void {
    this.authService.logout(); // Borramos el usuario del localStorage y emite false
    this.router.navigate(['/login']); // Redirigir a login una vez que se cierra sesión
  }
}