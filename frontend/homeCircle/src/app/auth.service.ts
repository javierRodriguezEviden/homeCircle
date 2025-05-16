import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // Usamos un BehaviorSubject para mantener el estado actualizado
  private usuarioLogueado = new BehaviorSubject<boolean>(this.tieneUsuarioEnStorage());
  public readonly estaLogueado$ = this.usuarioLogueado.asObservable();

  constructor() {}

  // Llamamos cuando el usuario inicia sesión
  login(usuario: any): void {
    localStorage.setItem('usuario', JSON.stringify(usuario));
    this.usuarioLogueado.next(true);
  }

  // Llamamos cuando el usuario cierra sesión
  logout(): void {
    localStorage.removeItem('usuario');
    this.usuarioLogueado.next(false);
  }

  // Verificamos si hay un usuario guardado en localStorage
  private tieneUsuarioEnStorage(): boolean {
    return !!localStorage.getItem('usuario');
  }

  // Opcional: obtener los datos del usuario
  getUsuario(): any {
    const usuarioStr = localStorage.getItem('usuario');
    return usuarioStr ? JSON.parse(usuarioStr) : null;
  }
}