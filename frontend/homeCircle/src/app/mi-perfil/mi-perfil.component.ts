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
  nombreUsuario: string | null = null;
  gmailUsuario: string | null = null;
  tlfUsuario: number | null = null;
  dniUsuario: string | null = null;
  apellidosUsuario: string | null = null;
  sedeUsuario: string | null = null;
  cuentabancariaUsuario: string | null = null;

  // Formulario reactivo para editar el perfil
  editProfileForm!: FormGroup;

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    // Obtiene el usuario guardado en localStorage (si existe)
    const usuarioGuardado = localStorage.getItem('usuario');

    if (usuarioGuardado) {
      const usuarioParseado = JSON.parse(usuarioGuardado);

      // Asigna los datos del usuario a las variables
      this.nombreUsuario = usuarioParseado.name || 'Usuario';
      this.apellidosUsuario = usuarioParseado.apellidos || 'Apellidos no disponibles';
      this.gmailUsuario = usuarioParseado.email || 'Correo no disponible';
      this.tlfUsuario = usuarioParseado.telefono || null;
      this.dniUsuario = usuarioParseado.dni || 'DNI no disponible';
      this.sedeUsuario = usuarioParseado.sede || 'Sede no disponible';
      this.cuentabancariaUsuario = usuarioParseado.cuenta_banco || null;

      // Inicializa el formulario con los datos del usuario
      this.editProfileForm = this.fb.group({
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

      // Actualiza los datos en localStorage
      const usuarioGuardado = localStorage.getItem('usuario');
      if (usuarioGuardado) {
        const usuarioParseado = JSON.parse(usuarioGuardado);

        // Actualiza los datos en el objeto parseado
        usuarioParseado.name = updatedData.nombre;
        usuarioParseado.apellidos = updatedData.apellidos;
        usuarioParseado.email = updatedData.email;
        usuarioParseado.telefono = updatedData.telefono;
        usuarioParseado.sede = updatedData.sede;
        usuarioParseado.cuenta_banco = updatedData.cuenta_banco; // <-- usa siempre cuenta_bancaria

        // Guarda los datos actualizados en localStorage
        localStorage.setItem('usuario', JSON.stringify(usuarioParseado));

        // Actualiza las variables del componente
        this.nombreUsuario = updatedData.nombre;
        this.apellidosUsuario = updatedData.apellidos;
        this.gmailUsuario = updatedData.email;
        this.tlfUsuario = updatedData.telefono;
        this.sedeUsuario = updatedData.sede;
        this.cuentabancariaUsuario = updatedData.cuenta_banco;

        alert('Perfil actualizado correctamente');
      }
    } else {
      alert('Por favor, corrige los errores en el formulario.');
    }
  }
}
