import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Casa } from './models/casa.model';

@Injectable({
  providedIn: 'root'
})
export class CasaService {

  private apiUrl = 'http://localhost:8020/casas'; 

  constructor(private http: HttpClient) {}

  getCasas(): Observable<Casa[]> {
    return this.http.get<Casa[]>(this.apiUrl);
  }
}