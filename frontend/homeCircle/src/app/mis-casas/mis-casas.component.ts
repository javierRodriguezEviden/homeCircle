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

  constructor(
    private casaServicio: CasaServicio
  ) {}

  ngOnInit(): void {
    this.obtenerCasas();
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