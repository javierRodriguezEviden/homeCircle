import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // Observable que indica si el usuario está logueado, basado en si hay usuario en localStorage
  public usuarioLogueado = new BehaviorSubject<boolean>(this.tieneUsuarioEnStorage());
  public readonly estaLogueado$ = this.usuarioLogueado.asObservable();

  constructor(private http: HttpClient) {}

  /**
   * Envía las credenciales al backend (Spring) para hacer login.
   * Si el login es correcto, guarda el token y los datos del usuario en localStorage,
   * y actualiza el estado de autenticación.
   */
  login(credentials: { email: string; password: string }) {
    return this.http.post<{ token: string }>('http://localhost:8020/auth/login', credentials);
  }

  /**
   * Cierra la sesión eliminando el token y los datos del usuario del localStorage,
   * y actualiza el estado de autenticación.
   */
  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('usuario');
    this.usuarioLogueado.next(false);
  }

  /**
   * Comprueba si hay usuario guardado en localStorage (para saber si está logueado).
   */
  private tieneUsuarioEnStorage(): boolean {
    return !!localStorage.getItem('usuario');
  }

  /**
   * Devuelve el token JWT guardado (si existe).
   */
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  /**
   * Devuelve los datos del usuario guardados (si existen).
   */
  getUsuario(): any {
    const usuarioStr = localStorage.getItem('usuario');
    return usuarioStr ? JSON.parse(usuarioStr) : null;
  }
}