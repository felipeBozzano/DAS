import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AuthService} from '../../services/./authService/AuthService';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {StreamingStudioResources} from '../../api/resources/streaming-studio.services';
import { IListadoFederaciones } from '../../api/models/IListadoFederacion.model';
import {filter} from 'rxjs/operators';
import {IUser} from '../../api/models/IUser.model';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {IContenido} from '../../api/models/IContenido.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isLoggedIn = true; // Suponiendo que este valor determina si el usuario está logueado
  userMenuVisible = false;
  advancedSearchVisible = false;
  @Input() pageTitle: string = '';
  @Input() id_cliente: string = '';
  @Output() mensajeEvent = new EventEmitter<string>();

  public formContenido!: FormGroup;

  constructor(private authService: AuthService, private router: Router, private _fb: FormBuilder, private streamingStudioResources: StreamingStudioResources) {
    this.formContenido = this._fb.group({
      titulo: new FormControl('',[ Validators.maxLength(16)]),
      reciente: new FormControl(false,[]),
      destacado: new FormControl(false,[]),
      mas_visto: new FormControl(false,[]),
      clasificacion: new FormControl('',[Validators.maxLength(16)]),
      genero: new FormControl('',[ Validators.maxLength(16)]),
    })
  }

  toggleUserMenu() {
    this.userMenuVisible = !this.userMenuVisible;
  }

  navigateTo(path: string) {
    this.router.navigate([`/${path}`]);
    this.enviarMensaje(this.id_cliente);
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
    this.id_cliente = this.authService.getCurrentUser().id_cliente;
  }

  navigateToFederaciones() {
    const ruta = `usuario/${this.id_cliente}/federaciones`
    this.router.navigate(["usuario", this.id_cliente, "federaciones"]);
  }

  enviarMensaje(response: any) {
    this.mensajeEvent.emit(this.id_cliente);
  }

  navigateToHome(){
    this.router.navigate(['/home'], { queryParams: {id_cliente: this.id_cliente }});
  }
}
