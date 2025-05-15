import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
 
@Component({
  selector: 'app-alquilar',
  templateUrl: './alquilar.component.html',
  standalone: false,
  styleUrls: ['./alquilar.component.css'],
})
export class AlquilarComponent {
  rentForm: FormGroup; // Formulario reactivo
  selectedImage: string | null = null; // Para previsualizar la foto principal
  additionalImages: string[] = []; // Para previsualizar fotos adicionales
 
  constructor(private fb: FormBuilder) {
    // Inicialización del formulario reactivo
      this.rentForm = this.fb.group({
      direccion: ['', Validators.required],
      ciudad: ['', Validators.required],
      provincia: ['', Validators.required],
      codigoPostal: ['', Validators.required],
      pais: ['', Validators.required],
      precio: ['', Validators.required],
    });
  }
 
  // Manejar cambio de archivo en el input oculto (foto principal)
  onFileChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.selectedImage = e.target.result; // Previsualización de la imagen
      };
      reader.readAsDataURL(file);
    }
  }
 
  // Manejar archivos adicionales
  onAdditionalFilesChange(event: any): void {
    const files = event.target.files;
    for (let i = 0; i < files.length; i++) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.additionalImages.push(e.target.result); // Previsualización de las imágenes
      };
      reader.readAsDataURL(files[i]);
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
        this.selectedImage = e.target.result; // Previsualización de la imagen
      };
      reader.readAsDataURL(file);
    }
  }
}
 
 
   
 
  /*// Enviar formulario
  onSubmit(): void {
    if (this.rentForm.valid) {
      console.log('Datos del formulario:', this.rentForm.value);
      console.log('Foto principal:', this.selectedImage);
      console.log('Fotos adicionales:', this.additionalImages);
      alert('¡Formulario enviado correctamente!');
    } else {
      alert('Por favor, completa todos los campos obligatorios.');
    }
  }*/