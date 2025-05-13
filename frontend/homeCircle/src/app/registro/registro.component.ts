import { Component } from '@angular/core';

@Component({
  selector: 'app-registro',
  standalone: false,
  templateUrl: './registro.component.html',
  styleUrl: './registro.component.css'
})
export class RegistroComponent {

  mensajeRegistro = "";
  registrado = false;
  nombre = "";
  apellidos = "";
  email = "";
  telefono = "";
  contrasena = "";
  confirmarContrasena = "";


  registrarUsuario() {
    if (this.contrasena !== this.confirmarContrasena) {
      this.mensajeRegistro = "Las contraseñas no coinciden.";
      this.registrado = false;
      return;
    }

    const usuario = {
      nombre: this.nombre,
      apellidos: this.apellidos,
      email: this.email,
      telefono: this.telefono,
      contrasena: this.contrasena
    };

    // Enviar los datos al backend usando fetch
    fetch('http://localhost:8020/api/auth/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(usuario)
    })
      .then(response => {
        if (response.ok) {
          this.registrado = true;
          this.mensajeRegistro = "Registro exitoso.";
        } else {
          this.registrado = false;
          this.mensajeRegistro = "Error al registrar el usuario.";
          console.error("Error en la respuesta del servidor:", response.statusText);
        }
      })
      .catch(error => {
        this.registrado = false;
        this.mensajeRegistro = "Error al registrar el usuario.";
        console.error("Error en la solicitud:", error);
      });
  }

}

