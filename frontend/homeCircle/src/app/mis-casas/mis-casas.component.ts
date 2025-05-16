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
  idUsuario: number = 1; // Aquí puedes obtener el ID del usuario logueado desde AuthService o localStorage

  constructor(
    private casaServicio: CasaServicio
  ) {}

  ngOnInit(): void {
    this.obtenerCasas();
  }

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
}