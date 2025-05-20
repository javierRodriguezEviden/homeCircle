import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CasaServicio, Casa } from '../casa-servicio';

@Component({
  selector: 'app-mis-casas',
  templateUrl: './mis-casas.component.html',
  styleUrls: ['./mis-casas.component.css'],
  standalone: false
})
export class MisCasasComponent implements OnInit {

  casas: Casa[] = [];
  idUsuario: number = 1; // Aquí puedes obtener el ID del usuario logueado desde AuthService o localStorage
  rentForm!: FormGroup; // Formulario reactivo para editar la casa
  casaSeleccionada: Casa | null = null; // Almacena la casa seleccionada para editar
  selectedImage: string | null = null; // Foto principal
  additionalImages: string[] = [];    // Fotos adicionales

  constructor(
    private casaServicio: CasaServicio,
    private fb: FormBuilder // Inyectamos FormBuilder para crear el formulario reactivo
  ) {}

  ngOnInit(): void {
    this.inicializarFormulario();
    this.obtenerCasas();
  }

  // Inicializa el formulario reactivo
  inicializarFormulario(): void {
    this.rentForm = this.fb.group({
      direccion: ['', Validators.required],
      localidad: ['', Validators.required],
      cp: ['', Validators.required],
      pais: ['', Validators.required],
      precio: ['', Validators.required],
      tipo: ['', Validators.required]
    });
  }

  // Obtiene las casas del usuario
  obtenerCasas(): void {
    this.casaServicio.getCasasPorUsuario(this.idUsuario).subscribe(
      (data) => {
        this.casas = data;
      },
      (error) => {
        console.error('Error al cargar las casas', error);
      }
    );
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