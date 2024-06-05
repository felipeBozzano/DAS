import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {StreamingStudioResources} from '../../api/resources/streaming-studio.services';
import {IContenido} from '../../api/models/IContenido.model';
import {AuthService} from '../../services/./authService/AuthService';
import {IInformacionContenidoResponseModel} from '../../api/models/IInformacionContenidoResponse.model';
import {Router} from '@angular/router';
import * as localForage from 'localforage';
import {IGenero} from "../../api/models/IGenero.model";
import {IClasificacion} from "../../api/models/IClasificacion.model";
import { IContenidoResponse } from '../../api/models/IContenidoResponse.model';

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
  isVisible = true;
  uniqueContenido: any;

  constructor(private _fb: FormBuilder, private streamingStudioResources: StreamingStudioResources, private authService: AuthService, private router: Router) {
    this.formContenido = this._fb.group({
      titulo: new FormControl('', [Validators.maxLength(16)]),
      clasificacion: new FormControl(null, []),
      reciente: new FormControl(null, []),
      destacado: new FormControl(null, []),
      mas_visto: new FormControl(null, []),
      comedia: new FormControl(false, []),
      accion: new FormControl(false, []),
      drama: new FormControl(false, []),
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
    this.uniqueContenido = [];
  }

  clasificacionButton(value: 'P' | 'S' | null) {
    const currentValue = this.formContenido.get('clasificacion')?.value;
    this.formContenido.get('clasificacion')?.setValue(currentValue === value ? null : value);
  }

  recienteButton(value: 'V' | 'F' | null) {
    const currentValue = this.formContenido.get('reciente')?.value;
    this.formContenido.get('reciente')?.setValue(currentValue === value ? null : value);
  }

  destacadoButton(value: 'V' | 'F' | null) {
    const currentValue = this.formContenido.get('destacado')?.value;
    this.formContenido.get('destacado')?.setValue(currentValue === value ? null : value);
  }

  masVistoButton(value: 'V' | 'F' | null) {
    const currentValue = this.formContenido.get('mas_visto')?.value;
    this.formContenido.get('mas_visto')?.setValue(currentValue === value ? null : value);
  }

  async onSubmit() {
    if (this.formContenido.valid) {
      const {titulo, reciente, destacado, mas_visto, clasificacion, comedia, accion, drama} = this.formContenido.value
      this.uniqueContenido = []
      const generos: IGenero = {
        drama: drama,
        accion: accion,
        comedia: comedia
      }

      const filtro: IContenido = {
        id_cliente: parseInt(this.id_cliente),
        titulo: titulo,
        reciente: reciente,
        destacado: destacado,
        mas_visto: mas_visto,
        clasificacion: clasificacion,
        generos: generos
      }

      console.log(filtro)

      this.streamingStudioResources.contenido(filtro).subscribe((response) => {
          this.contenido = null;
          this.contenido = response
          const imageUrls = new Set();

          response.forEach(item => {
            if (!imageUrls.has(item.url_imagen)) {
              this.uniqueContenido.push(item);
              imageUrls.add(item.url_imagen);
            }
          });

          if (this.contenido.length === 0) {
            this.isVisible = true;
          }
        },
        (error) => {
          console.error('Error en la solicitud:', error);
        }
      );
    }
  }

  navigateTo(id_contenido: any) {
    const path = `/descripcion/${this.id_cliente}/${id_contenido}`
    console.log("path: ", path);
    this.router.navigate([`/${path}`]);
  }

  closeCard() {
    this.isVisible = false;
  }
}
