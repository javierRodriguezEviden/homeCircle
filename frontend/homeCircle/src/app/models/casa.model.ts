export interface Casa {
  id: number;
  direccion: string;
  localidad: string;
  provincia?: string;
  pais?: string;
  cp?: string;
  precio: number;
  tipo: string;


  //Dejamos esto comentado para más adelante
  /*usuario: any; 
  reservas: any[]; 
  imagenCasa: any[];*/
}