import {Component, Input, OnInit} from '@angular/core';
import {AuthService} from '../../AuthService';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {StreamingStudioResources} from '../../api/resources/streaming-studio.services';
import { IListadoFederaciones } from '../../api/models/IListadoFederacion.model';
import {filter} from 'rxjs/operators';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isLoggedIn = true; // Suponiendo que este valor determina si el usuario está logueado
  userMenuVisible = false;
  @Input() pageTitle: string = '';
  @Input() id_cliente: string = '';

  constructor(private authService: AuthService, private router: Router) {}

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
    console.log("this.id_cliente: ", this.id_cliente);

  }

  navigateToFederaciones() {
    const ruta = `usuario/${this.id_cliente}/federaciones`
    console.log("ruuta: ", ruta);
    this.router.navigate(["usuario", this.id_cliente, "federaciones"]);
  }
}
