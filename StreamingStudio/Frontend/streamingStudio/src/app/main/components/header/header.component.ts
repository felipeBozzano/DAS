import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../AuthService';
import {ActivatedRoute, Router} from '@angular/router';
import {StreamingStudioResources} from '../../api/resources/streaming-studio.services';
import { IListadoFederaciones } from '../../api/models/IListadoFederacion.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isLoggedIn = true; // Suponiendo que este valor determina si el usuario está logueado
  userMenuVisible = false;
  id_cliente: string | null = null;

  constructor(private authService: AuthService, private router: Router, private route: ActivatedRoute, private streamingStudioResources: StreamingStudioResources) {}

  toggleUserMenu() {
    this.userMenuVisible = !this.userMenuVisible;
  }

  navigateTo(path: string) {
    this.router.navigate([`/${path}`]);
    this.userMenuVisible = false;
  }

  logout() {
    // Lógica de cierre de sesión
    this.authService.logout();
    this.userMenuVisible = false;
    this.router.navigate(['/login']);
  }

  isAuthenticated(): boolean {
    return this.authService.isLoggedIn();
  }

  ngOnInit(): void {
    this.id_cliente = this.route.snapshot.paramMap.get('id_cliente');
  }
}
