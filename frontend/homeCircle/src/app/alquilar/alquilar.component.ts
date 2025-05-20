import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-alquilar',
  templateUrl: './alquilar.component.html',
  styleUrls: ['./alquilar.component.css'],
  standalone: false
})
export class AlquilarComponent {
  rentForm: FormGroup;
  selectedImage: string | null = null;
  additionalImages: string[] = [];

  mensajeCreacion = '';
  añadido = false;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {
    this.rentForm = this.fb.group({
      direccion: ['', Validators.required],
      localidad: ['', Validators.required],
      provincia: ['', Validators.required],
      cp: ['', Validators.required],
      pais: ['', Validators.required],
      precio: ['', Validators.required],
      tipo: ['', Validators.required],
    });
  }

  registrarCasa(event: Event) {
    event.preventDefault();

    if (this.rentForm.invalid) {
      this.mensajeCreacion = 'Por favor completa todos los campos obligatorios.';
      this.añadido = false;
      return;
    }

    const userData = this.rentForm.value;

    // Obtener el token desde localStorage
    const token = localStorage.getItem('token');

    // Configurar las cabeceras con el token
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + token,
      'Content-Type': 'application/json'
    });

    // Hacer la petición POST con las cabeceras
    this.http.post('http://localhost:8020/casas', userData, { headers }).subscribe({
      next: (response: any) => {
        this.añadido = true;
        this.mensajeCreacion = 'Casa creada con éxito. Redirigiendo...';
        setTimeout(() => {
          this.router.navigate(['/miPerfil']);
        }, 1000);
      },
      error: (error) => {
        console.error('Error al crear la casa:', error);

        this.añadido = false;
        if (error.status === 401) {
          this.mensajeCreacion = 'No estás autenticado. Inicia sesión.';
        } else if (error.status === 403) {
          this.mensajeCreacion = 'Acceso denegado. No tienes permisos suficientes.';
        } else if (error.status === 400 && error.error?.message) {
          this.mensajeCreacion = error.error.message;
        } else {
          this.mensajeCreacion = 'Error inesperado. Inténtalo más tarde.';
        }
      }
    });
  }

  // Manejar cambio de archivo en el input oculto (foto principal)
  onFileChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.selectedImage = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }

  // Manejar archivos adicionales
  onAdditionalFilesChange(event: any): void {
    const files = event.target.files;
    for (let i = 0; i < files.length; i++) {
      const file = files[i];
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.additionalImages.push(e.target.result);
      };
      reader.readAsDataURL(file);
    }
  }

  onDragOver(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
  }

  onDragLeave(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
  }

  onDrop(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    const files = event.dataTransfer?.files;
    if (files && files.length > 0) {
      const file = files[0];
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.selectedImage = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }
}