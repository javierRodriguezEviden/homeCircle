import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CasaService } from '../casa-servicio';
import { Casa } from '../models/casa.model';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-mis-casas',
  templateUrl: './mis-casas.component.html',
  styleUrls: ['./mis-casas.component.css'],
  standalone: false
})
export class MisCasasComponent implements OnInit {

  casas: Casa[] = [];
  idUsuario: number | null = null;
  rentForm: any;
  fb: any;
  selectedImage: any;
  additionalImages: any;

  constructor(private casaServicio: CasaService) {}

  ngOnInit(): void {
    this.obtenerIdUsuarioDesdeToken();
  }

  obtenerIdUsuarioDesdeToken(): void {
    const token = localStorage.getItem('token'); //Guardo el token
    if (token) {
      try {
        const payload = JSON.parse(atob(token.split('.')[1])); 
        this.idUsuario = payload.id_usuario; // Este campo tiene que estar
        this.obtenerCasas();
      } catch (e) {
        console.error('Error al decodificar el token');
      }
    } else {
      console.error('No hay token en localStorage');
    }
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
    if (this.idUsuario !== null) {
      this.casaServicio.getCasasPorUsuario(this.idUsuario).subscribe(
        (data) => {
          this.casas = data;
        },
        (error) => {
          console.error('Error al cargar las casas', error);
        }
      );
    }
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