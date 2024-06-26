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
  @Input() pageTitle: string = '';
  @Input() id_cliente: string = '';
  @Output() advancedSearchVisible = "true";
  @Output() mensajeEvent = new EventEmitter<string>();
  menuActive: boolean = false;

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
    if(this.authService.isLoggedIn()) {
      this.id_cliente = this.authService.getCurrentUser().id_cliente;
    }
  }

  navigateToSeries(){
    const idCliente: number = Number(this.id_cliente);
    console.log("ruta: ", `series?id_cliente=${this.id_cliente}`)
    this.router.navigate(['series'], { queryParams: {id_cliente: idCliente }});
  }

  navigateToPeliculas(){
    const idCliente: number = Number(this.id_cliente);
    console.log("ruta: ", `peliculas?id_cliente=${this.id_cliente}`)
    this.router.navigate(['peliculas'], { queryParams: {id_cliente: idCliente }});
  }

  navigateToFederaciones() {
    // const ruta = `usuario/federaciones`
    this.router.navigate(["usuario/federaciones"]);
  }

  enviarMensaje(response: any) {
    this.mensajeEvent.emit(this.id_cliente);
  }

  enviarMensajeContenido(response: any) {
    this.mensajeEvent.emit(this.advancedSearchVisible);
  }

  navigateToHome(){
    this.router.navigate(['/home'], { queryParams: {id_cliente: this.id_cliente }});
  }

  navigateToUser(){
    this.router.navigate([`mi-usuario/${this.id_cliente}`]);
  }

  toggleMenu() {
    this.menuActive = !this.menuActive;
  }

  protected readonly toString = toString;
}
