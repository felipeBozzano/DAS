// auth.service.ts
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private isAuthenticated = false;
  private currentUser: any = null;

  constructor() {
    const savedUser = localStorage.getItem('currentUser');
    if (savedUser) {
      this.currentUser = JSON.parse(savedUser);
    }
  }

  // Método para verificar si el usuario está autenticado
  isLoggedIn() {
    return this.isAuthenticated;
  }

  // Método para realizar el inicio de sesión
  login(user: any) {
    // Aquí podrías realizar la lógica de inicio de sesión
    this.isAuthenticated = true;
    this.currentUser = user;
    localStorage.setItem('currentUser', JSON.stringify(user));
  }

  // Método para realizar el cierre de sesión
  async logout() {
    // Aquí podrías realizar la lógica de cierre de sesión
    this.isAuthenticated = false;
    this.currentUser = null;
    localStorage.removeItem('currentUser');
  }

  getCurrentUser(): any {
    return this.currentUser;
  }
}
