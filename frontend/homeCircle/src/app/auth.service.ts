import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private usuarioLogueado = new BehaviorSubject<boolean>(this.tieneUsuarioEnStorage());
  public readonly estaLogueado$ = this.usuarioLogueado.asObservable();

  constructor(private http: HttpClient) {}

  // ✅ AHORA RECIBE UN OBJETO CON email Y password
  login(credentials: { email: string; password: string; nombre: string; apellidos: string; dni:string; sede:string; telefono:string; cuenta_banco:string;}): void {
    this.http.post<{ token: string }>('http://localhost:8020/auth/login', credentials).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.token);
        localStorage.setItem('usuario', JSON.stringify({ email: credentials.email, nombre: credentials.nombre, apellidos: credentials.apellidos, dni: credentials.dni, sede: credentials.sede, telefono: credentials.telefono, cuenta_banco: credentials.cuenta_banco }));
        this.usuarioLogueado.next(true);
      },
      error: (err) => {
        console.error('Error al iniciar sesión:', err);
        this.usuarioLogueado.next(false);
      }
    });
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('usuario');
    this.usuarioLogueado.next(false);
  }

  private tieneUsuarioEnStorage(): boolean {
    return !!localStorage.getItem('usuario');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getUsuario(): any {
    const usuarioStr = localStorage.getItem('usuario');
    return usuarioStr ? JSON.parse(usuarioStr) : null;
  }
}