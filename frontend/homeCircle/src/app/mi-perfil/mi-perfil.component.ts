import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-mi-perfil',
  standalone: false,
  templateUrl: './mi-perfil.component.html',
  styleUrl: './mi-perfil.component.css'
})
export class MiPerfilComponent implements OnInit{

// Variables para almacenar los datos del usuario
  nombreUsuario: string | null = null;
  gmailUsuario: string | null = null;
  tlfUsuario: Number | null = null;
  dniUsuario: string | null = null;
  apellidosUsuario: string | null = null;


  ngOnInit(): void {
    // Obtiene el usuario guardado en localStorage (si existe)
    const usuarioGuardado = localStorage.getItem('usuario');

    // Si el usaurio esta logueado, guarda datos en variables y las muestra
    if (usuarioGuardado) {
      const usuarioParseado = JSON.parse(usuarioGuardado);
      // Asigna nombre y apellidos, o 'Usuario' si no existen
      this.nombreUsuario = usuarioParseado.name || 'Usuario';
      // Asigna apellidos, o mensaje por defecto si no existe
      this.apellidosUsuario = usuarioParseado.apellidos || 'Apellidos no disponibles';
      // Asigna email, o mensaje por defecto si no existe
      this.gmailUsuario = usuarioParseado.email || 'Correo no disponible';
      // Asigna teléfono, o mensaje por defecto si no existe
      this.tlfUsuario = usuarioParseado.telefono || 'Telefono no disponible';
      // Asigna dni, o mensaje por defecto si no existe
      this.dniUsuario = usuarioParseado.dni || 'DNI no disponible';

    } else {
      // Si no hay usuario guardado, muestra mensaje en consola
      console.log('No hay usuario guardado en localStorage');
    }
  }
}
