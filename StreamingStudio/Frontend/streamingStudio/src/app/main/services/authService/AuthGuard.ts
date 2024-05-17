// auth.guard.ts
import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from './AuthService';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    // Verifica si el usuario está autenticado
    if (this.authService.isLoggedIn()) {
      return true; // Permitir acceso a la ruta
    } else {
      // Si el usuario no está autenticado, redirigirlo a la página de inicio de sesión
      this.router.navigate(['']);
      return false; // Impedir acceso a la ruta
    }
  }
}
