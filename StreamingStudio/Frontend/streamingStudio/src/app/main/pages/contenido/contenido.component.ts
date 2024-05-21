import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {StreamingStudioResources} from '../../api/resources/streaming-studio.services';
import {IContenido} from '../../api/models/IContenido.model';
import {AuthService} from '../../services/./authService/AuthService';
import {IInformacionContenidoResponseModel} from '../../api/models/IInformacionContenidoResponse.model';
import {Router} from '@angular/router';
import * as localForage from 'localforage';

@Component({
  selector: 'app-contenido',
  templateUrl: './contenido.component.html',
  styleUrls: ['./contenido.component.css']
})
export class ContenidoComponent implements OnInit {
  currentUser: any;
  id_cliente: string = '';
  advancedSearchVisible = true;
  contenido: any;
  contenidoSeleccionado: IInformacionContenidoResponseModel | null = null;
  isVisible = true;

  constructor(private _fb: FormBuilder, private streamingStudioResources: StreamingStudioResources, private authService: AuthService, private router: Router) {
    this.formContenido = this._fb.group({
      titulo: new FormControl('',[ Validators.maxLength(16)]),
      reciente: new FormControl(false,[]),
      destacado: new FormControl(false,[]),
      mas_visto: new FormControl(false,[]),
      clasificacion: new FormControl('',[Validators.maxLength(16)]),
      genero: new FormControl('',[ Validators.maxLength(16)]),
    })
  }

  public formContenido!: FormGroup;

  ngOnInit(): void {
    localForage.config({
      driver: localForage.LOCALSTORAGE,
      name: 'StreamingStudio'
    });
    this.currentUser = this.authService.getCurrentUser();
    this.id_cliente = this.currentUser.id_cliente;
    this.contenido = null;
  }

  async onSubmit(){
    console.log("hola");
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
            this.contenido = response
            if(this.contenido.length === 0){
              this.isVisible = true;
            }
            this.advancedSearchVisible = !this.advancedSearchVisible;
          },
          (error) => {
            // Si hay un error en la respuesta, muestra un mensaje de error
            console.error('Error en la solicitud:', error);
            // Aquí puedes manejar el error y mostrar un mensaje de error al usuario
          }
        );
    }
  }

  navigateTo(id_contenido: any){
    const path = `/descripcion/${this.id_cliente}/${id_contenido}`
    console.log("path: ", path);
    this.router.navigate([`/${path}`]);
  }

  closeCard() {
    this.isVisible = false;
    this.advancedSearchVisible = !this.advancedSearchVisible;
  }
}
