import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-mi-perfil',
  standalone: false,
  templateUrl: './mi-perfil.component.html',
  styleUrls: ['./mi-perfil.component.css']
})
export class MiPerfilComponent implements OnInit {
  // Variables para almacenar los datos del usuario
  idUsuario?: number;
  nombreUsuario: string | null = null;
  apellidosUsuario: string | null = null;
  gmailUsuario: string | null = null;
  tlfUsuario: number | null = null;
  dniUsuario: string | null = null;
  sedeUsuario: string | null = null;
  cuentabancariaUsuario: string | null = null;

  // Formulario reactivo para editar el perfil
  editProfileForm!: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpClient) { }

  ngOnInit(): void {
    // Obtiene el usuario guardado en localStorage (si existe)
    const usuarioGuardado = localStorage.getItem('usuario');

    if (usuarioGuardado) {
      const usuarioParseado = JSON.parse(usuarioGuardado);

      // Asigna los datos del usuario a las variables
      this.idUsuario = usuarioParseado.id;
      this.nombreUsuario = usuarioParseado.nombre || usuarioParseado.name || 'Usuario';
      this.apellidosUsuario = usuarioParseado.apellidos || 'Apellidos no disponibles';
      this.gmailUsuario = usuarioParseado.email || 'Correo no disponible';
      this.tlfUsuario = usuarioParseado.telefono || null;
      this.dniUsuario = usuarioParseado.dni || 'DNI no disponible';
      this.sedeUsuario = usuarioParseado.sede || 'Sede no disponible';
      this.cuentabancariaUsuario = usuarioParseado.cuenta_banco || null;

      
      // Inicializa el formulario con los datos del usuario
      this.editProfileForm = this.fb.group({
        id: [this.idUsuario, Validators.required],
        nombre: [this.nombreUsuario, Validators.required],
        apellidos: [this.apellidosUsuario, Validators.required],
        email: [this.gmailUsuario, [Validators.required, Validators.email]],
        telefono: [this.tlfUsuario, Validators.required],
        dni: [this.dniUsuario, Validators.required],
        sede: [this.sedeUsuario, Validators.required],
        cuenta_banco: [this.cuentabancariaUsuario, Validators.required],
      });
    } else {
      console.log('No hay usuario guardado en localStorage');
    }
  }

  // Método para actualizar el perfil
  updateProfile(): void {
    if (this.editProfileForm.valid) {
      const updatedData = this.editProfileForm.value;
      const usuarioGuardado = localStorage.getItem('usuario');

      if (usuarioGuardado) {
        const usuarioParseado = JSON.parse(usuarioGuardado);
        const userId = usuarioParseado.id;

        this.http.put<any>(`http://localhost:8020/usuarios/${userId}`, updatedData).subscribe(
          (response) => {
            // Actualiza localStorage y variables solo si el backend responde OK
            localStorage.setItem('usuario', JSON.stringify(response));
            this.idUsuario = response.id;
            this.nombreUsuario = response.nombre || response.name || 'Usuario';
            this.apellidosUsuario = response.apellidos || 'Apellidos no disponibles';
            this.gmailUsuario = response.email || 'Correo no disponible';
            this.tlfUsuario = response.telefono || null;
            this.dniUsuario = response.dni || 'DNI no disponible';
            this.sedeUsuario = response.sede || 'Sede no disponible';
            this.cuentabancariaUsuario = response.cuenta_banco || null;
            alert('Perfil actualizado correctamente');
          },
          (error) => {
            alert('Error al actualizar el perfil en el servidor.');
          }
        );
      }
    } else {
      alert('Por favor, corrige los errores en el formulario.');
    }
  }
}