import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home-registrado',
  standalone: false, // Esto significa que usas módulos (NgModule), no standalone API
  templateUrl: './home-registrado.component.html',
  styleUrls: ['./home-registrado.component.css'] // Usa "styleUrls" si es un array
})
export class HomeRegistradoComponent implements OnInit {

  // Variables para almacenar los datos del usuario
  nombreUsuario: string | null = null;
  gmailUsuario: string | null = null;
  tlfUsuario: Number | null = null;

  // Método que se ejecuta al inicializar el componente
  ngOnInit(): void {
    // Obtiene el usuario guardado en localStorage (si existe)
    const usuarioGuardado = localStorage.getItem('usuario');

    // Si el usaurio esta logueado, guarda datos en variables y las muestra
    if (usuarioGuardado) {
      const usuarioParseado = JSON.parse(usuarioGuardado);
      // Asigna nombre y apellidos, o 'Usuario' si no existen
      this.nombreUsuario = usuarioParseado.name || 'Usuario';
      // Asigna email, o mensaje por defecto si no existe
      this.gmailUsuario = usuarioParseado.email || 'Correo no disponible';
      // Asigna teléfono, o mensaje por defecto si no existe
      this.tlfUsuario = usuarioParseado.telefono || 'Telefono no disponible';

    } else {
      // Si no hay usuario guardado, muestra mensaje en consola
      console.log('No hay usuario guardado en localStorage');
    }
  }
/*
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
  }*/
}