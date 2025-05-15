import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service'; // Asegúrate que la ruta sea correcta

@Component({
  selector: 'app-home-registrado',
  standalone: false, // Esto significa que usas módulos (NgModule), no standalone API
  templateUrl: './home-registrado.component.html',
  styleUrls: ['./home-registrado.component.css'] // Usa "styleUrls" si es un array
})
export class HomeRegistradoComponent implements OnInit {

  // ✅ Inyectamos el servicio en el constructor
  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    // ✅ Te suscribes al observable para saber si está logueado
    this.authService.isLoggedIn$.subscribe(loggedIn => {
      if (loggedIn) {
        const user = this.authService.getUsuario();
        console.log('Usuario logueado:', user);
        // Aquí puedes cargar datos personalizados del usuario
      }
    });
  }
}