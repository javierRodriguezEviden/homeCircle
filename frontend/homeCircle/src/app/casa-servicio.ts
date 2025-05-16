import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Casa {
  id: number;
  tipo: string;
  direccion: string;
  cp: string;
  localidad: string;
  provincia: string;
  pais: string;
  precio: number;
}

@Injectable({
  providedIn: 'root'
})
export class CasaServicio {

  private apiUrl = 'http://localhost:8080/api/casas'; //aqui se deberia meter la api de la casa si esta ya

  constructor(private http: HttpClient) {}

  getCasasPorUsuario(idUsuario: number): Observable<Casa[]> {
    return this.http.get<Casa[]>(`${this.apiUrl}/usuario/${idUsuario}`);
  }
}