// auth.service.ts
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private isAuthenticated = false;

  constructor() { }

  // Método para verificar si el usuario está autenticado
  isLoggedIn() {
    return this.isAuthenticated;
  }

  // Método para realizar el inicio de sesión
  login() {
    // Aquí podrías realizar la lógica de inicio de sesión
    this.isAuthenticated = true;
  }

  // Método para realizar el cierre de sesión
  logout() {
    // Aquí podrías realizar la lógica de cierre de sesión
    this.isAuthenticated = false;
  }
}
