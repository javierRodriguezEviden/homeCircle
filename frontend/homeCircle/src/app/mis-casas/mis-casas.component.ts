import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CasaService } from '../casa-servicio';
import { Casa } from '../models/casa.model';

@Component({
  selector: 'app-mis-casas',
  templateUrl: './mis-casas.component.html',
  styleUrls: ['./mis-casas.component.css'],
  standalone: false
})
export class MisCasasComponent implements OnInit {

  casas: Casa[] = [];
  idUsuario: number | null = null;

  constructor(private casaServicio: CasaService) {}

  ngOnInit(): void {
  }

  
  
}