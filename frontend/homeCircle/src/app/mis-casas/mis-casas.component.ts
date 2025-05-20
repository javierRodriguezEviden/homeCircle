import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CasaServicio, Casa } from '../casa-servicio';

@Component({
  selector: 'app-mis-casas',
  templateUrl: './mis-casas.component.html',
  styleUrls: ['./mis-casas.component.css'],
  standalone: false
})
export class MisCasasComponent implements OnInit {

  casas: Casa[] = [];
  idUsuario: number | null = null;

  constructor(private casaServicio: CasaServicio) {}

  ngOnInit(): void {
    this.obtenerIdUsuarioDesdeToken();
  }

  obtenerIdUsuarioDesdeToken(): void {
    const token = localStorage.getItem('auth_token'); //Guardo el token
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
}