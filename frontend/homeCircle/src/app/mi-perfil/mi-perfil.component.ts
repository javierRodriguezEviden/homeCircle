import { HttpClient, HttpHeaders } from '@angular/common/http';
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
    const gmailUsuario = localStorage.getItem('email');
    const token = localStorage.getItem('token');

    if (gmailUsuario && token) {
      const emailCodificado = encodeURIComponent(gmailUsuario);
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

      this.http.get<any>(
        `http://localhost:8020/usuarios/search/${emailCodificado}`,
        { headers }
      ).subscribe(
        (response) => {
          // Asigna los datos del usuario a las variables
          //this.idUsuario = response.id; // <-- Importante para update
          this.nombreUsuario = response.nombre || response.name || 'Usuario';
          this.apellidosUsuario = response.apellidos || 'Apellidos no disponibles';
          this.gmailUsuario = response.email || 'Correo no disponible';
          this.tlfUsuario = response.telefono || null;
          this.dniUsuario = response.dni || 'DNI no disponible';
          this.sedeUsuario = response.sede || 'Sede no disponible';
          //this.cuentabancariaUsuario = response.cuenta_banco || null;

          // Inicializa el formulario con los datos del usuario
          this.editProfileForm = this.fb.group({
            nombre: [this.nombreUsuario, Validators.required],
            apellidos: [this.apellidosUsuario, Validators.required],
            email: [this.gmailUsuario, [Validators.required, Validators.email]],
            telefono: [this.tlfUsuario, Validators.required],
            dni: [this.dniUsuario, Validators.required],
            sede: [this.sedeUsuario, Validators.required],
            //cuenta_banco: [this.cuentabancariaUsuario, Validators.required],
          });
        },
        (error) => {
          console.log('No se pudo obtener la información del usuario', error);
        }
      );
    } else {
      console.log('No hay usuario logueado o no hay token');
    }
  }

  // Método para actualizar el perfil
  updateProfile(): void {
    if (this.editProfileForm.valid && this.idUsuario) {
      const updatedData = this.editProfileForm.value;
      const token = localStorage.getItem('token');
      const headers = token ? new HttpHeaders().set('Authorization', `Bearer ${token}`) : undefined;

      this.http.put<any>(
        `http://localhost:8020/usuarios/${this.idUsuario}`,
        updatedData,
        { headers }
      ).subscribe(
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
    } else {
      alert('Por favor, corrige los errores en el formulario.');
    }
  }
}