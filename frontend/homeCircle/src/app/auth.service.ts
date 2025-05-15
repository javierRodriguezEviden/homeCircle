/** 
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router) {}

  // Verificar si el usuario está logueado
  isLoggedIn(): boolean {
    return !!localStorage.getItem('usuario');
  }

  // Método para cerrar sesión
  logout(): void {
    localStorage.removeItem('usuario');
    this.router.navigate(['/login']);
  }
}
  */
 
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loggedIn = new BehaviorSubject<boolean>(false);
  private userData: any = null;

  // Observable para que otros componentes escuchen cambios
  public readonly isLoggedIn$ = this.loggedIn.asObservable();

  constructor() {}

  // Método para actualizar el estado de login
  login(userData: any): void {
    localStorage.setItem('usuario', JSON.stringify(userData));
    this.userData = userData;
    this.loggedIn.next(true);
  }

  // Método para obtener los datos del usuario
  getUsuario(): any {
    if (!this.userData) {
      const storedUser = localStorage.getItem('usuario');
      if (storedUser) {
        this.userData = JSON.parse(storedUser);
        this.loggedIn.next(true);
      }
    }
    return this.userData;
  }

  // Método para cerrar sesión
  logout(): void {
    localStorage.removeItem('usuario');
    this.userData = null;
    this.loggedIn.next(false);
  }
}