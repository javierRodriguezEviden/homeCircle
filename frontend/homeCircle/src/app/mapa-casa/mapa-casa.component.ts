import { Component, AfterViewInit } from '@angular/core';
import * as L from 'leaflet';
import { HttpClient } from '@angular/common/http';

// Interfaz para representar una casa
interface Casa {
  coordenadas: [number, number];
  direccion: string;
  info?: string; // Información adicional (opcional)
}

@Component({
  selector: 'app-mapa-casa',
  templateUrl: './mapa-casa.component.html',
  styleUrls: ['./mapa-casa.component.css'],
  standalone: false
})
export class MapaCasaComponent implements AfterViewInit {

  // Para el campo de búsqueda
  direccionBuscada: string = '';

  private map!: L.Map;

  // Lista de casas simulada
  casas: Casa[] = [
    { coordenadas: [40.4168, -3.7038], direccion: 'Casa en Madrid'  },
    { coordenadas: [41.3874, 2.1686], direccion: 'Casa en Barcelona' },
    { coordenadas: [39.4699, -0.3763], direccion: 'Casa en Valencia'},
    { coordenadas: [39.8675, -4.0205], direccion: 'Casa en Toledo'},
    { coordenadas: [40.0692, -2.1238], direccion: 'Casa en Cuenca'},
    { coordenadas: [40.0208, -3.5600], direccion: 'Casa en Aranjuez'},
    { coordenadas: [39.75987, -3.16452], direccion: 'Casa en Corral de Almaguer'},


  ];

  constructor(private http: HttpClient) {}

  ngAfterViewInit(): void {
    this.initMap();
    this.addMarkers();
  }

  // Inicializa el mapa
  initMap(): void {
    const ubicacionInicial: [number, number] = [40.0, -4.0]; // Centro de España
    this.map = L.map('map').setView(ubicacionInicial, 6);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap'
    }).addTo(this.map);
  }

  // Añade marcadores de las casas al mapa
  addMarkers(): void {
    this.casas.forEach(casa => {
      const marker = L.marker(casa.coordenadas as L.LatLngExpression)
        .addTo(this.map);

      // Popup con información extendida
      marker.bindPopup(`<b>${casa.direccion}</b><br>${casa.info || ''}`);
    });
  }

  // Busca una dirección usando la API de Nominatim
  buscarDireccion(): void {
    if (!this.direccionBuscada.trim()) {
      alert('Por favor, introduce una dirección o lugar');
      return;
    }

    const url = `https://nominatim.openstreetmap.org/search?format=json&q= ${encodeURIComponent(this.direccionBuscada)}`;

    this.http.get(url).subscribe((result: any) => {
      if (result && result.length > 0) {
        const lat = parseFloat(result[0].lat);
        const lon = parseFloat(result[0].lon);

        // Centrar el mapa en la ubicación encontrada
        this.map.flyTo([lat, lon] as L.LatLngExpression, 13);

        // Añadir marcador temporal
        const marker = L.marker([lat, lon] as L.LatLngExpression)
          .addTo(this.map)
          .bindPopup(`Ubicación buscada: <b>${this.direccionBuscada}</b>`)
          .openPopup();

      } else {
        alert('No se encontró la dirección');
      }
    }, error => {
      console.error('Error al buscar dirección:', error);
      alert('Hubo un error al buscar la dirección');
    });
  }
}