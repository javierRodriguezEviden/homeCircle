import { Component, Input, AfterViewInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface ClimaDia {
  fecha: string;
  maxTemp: number;
  minTemp: number;
  descripcion: string;
  icono: string;
}

@Component({
  selector: 'app-clima-casas',
  standalone: false,
  templateUrl: './clima-casas.component.html',
  styleUrls: ['./clima-casas.component.css']
})
export class ClimaCasasComponent implements AfterViewInit {

  @Input() ciudad: string = 'Sevilla'; // Ciudad por defecto
  climaSemanal: ClimaDia[] = [];
  error: string = '';
  loading = false;

  diasSemana: string[] = ['Sábado', 'Miércoles', 'Domingo', 'Jueves', 'Lunes', 'Viernes', 'Martes'];

  private apiKey = '46a9d3f947864f778b0102022251605'; // Reemplaza por tu clave real
  private apiUrl = 'http://api.weatherapi.com/v1/forecast.json';

  constructor(private http: HttpClient) {}

  ngAfterViewInit(): void {
    this.obtenerClima();
  }

  obtenerClima(): void {
    const url = `${this.apiUrl}?key=${this.apiKey}&q=${this.ciudad}&days=7&aqi=no&alerts=no`;

    this.loading = true;
    this.error = '';
    this.climaSemanal = [];

    this.http.get(url).subscribe(
      (data: any) => {
        if (data && data.forecast && data.forecast.forecastday) {
          this.climaSemanal = data.forecast.forecastday.map((dia: any, index: number) => ({
            fecha: this.formatearFecha(dia.date_epoch, index),
            maxTemp: dia.day.maxtemp_c,
            minTemp: dia.day.mintemp_c,
            descripcion: dia.day.condition.text,
            icono: 'https:' + dia.day.condition.icon
          }));
        } else {
          this.error = 'No se encontraron datos del clima.';
        }

        this.loading = false;
      },
      (err) => {
        console.error('Error al obtener clima:', err);
        this.error = 'No se pudo cargar el clima. Verifica la ciudad e inténtalo de nuevo.';
        this.loading = false;
      }
    );
  }

  formatearFecha(timestamp: number, index: number): string {
    const fecha = new Date(timestamp * 1000); // Convertir a milisegundos
    return this.diasSemana[(fecha.getDay() + index) % 7] || '';
  }
}