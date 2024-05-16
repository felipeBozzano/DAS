import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AuthService} from '../../AuthService';
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
  toggleAdvancedSearch() {
    this.advancedSearchVisible = !this.advancedSearchVisible;
  }
  async onSubmit() {
    if (this.formContenido.valid) {
      const {titulo, reciente, destacado, mas_visto, clasificacion, genero} = this.formContenido.value
      const filtro: IContenido = {
        id_cliente: parseInt(this.id_cliente),
        titulo: titulo,
        reciente: reciente,
        destacado: destacado,
        mas_visto: mas_visto,
        clasificacion: clasificacion,
        genero: genero
      }
      console.log("filtro: ", filtro);
      this.streamingStudioResources.contenido(filtro)
        .subscribe(
          (response) => {
            // Si la respuesta es exitosa, redirige al home
            console.log('Respuesta del servidor:', response);
            // aca mando del header al home la resuesta
            this.enviarMensaje(response);
          },
          (error) => {
            // Si hay un error en la respuesta, muestra un mensaje de error
            console.error('Error en la solicitud:', error);
            // Aquí puedes manejar el error y mostrar un mensaje de error al usuario
          }
        );
    }
  }

  enviarMensaje(response: any) {
    this.mensajeEvent.emit(response);
  }
}
