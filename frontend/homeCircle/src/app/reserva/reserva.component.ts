import { Component, OnInit } from '@angular/core';
import { Casa } from '../models/casa.model';
import { CasaService } from '../casa-servicio';

@Component({
  selector: 'app-reserva',
  templateUrl: './reserva.component.html',
  styleUrls: ['./reserva.component.css'],
  standalone: false
})
export class ReservaComponent implements OnInit {

  casas: Casa[] = [];
  loading: boolean = true;

  constructor(private casaService: CasaService) {}

  ngOnInit(): void {
    this.casaService.getCasas().subscribe({
      next: (data) => {
        console.log('Datos recibidos:', data); //Para depurar

        this.casas = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar las casas:', err);
        this.loading = false;
      }
    });
  }

  seleccionarCasa(casa: Casa) {
  console.log('Casa seleccionada:', casa);
  // Aqui podriamos poner algo más especifico como unas caracteristicas de la casa, un componente que sea más especifico a la casa o la reserva directamente
}
}