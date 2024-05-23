import { Component, Input } from '@angular/core';
import {PublicationService} from '../../services/publicationService/publicationService';

interface Publicidad {
  id_tipo_banner: number;
  id_publicidad: number;
  url_de_imagen: string;
  url_de_publicidad: string;
}

@Component({
  selector: 'app-publicidad-izquierda',
  templateUrl: './publicidades-izquierda.component.html',
  styleUrls: ['./publicidades-izquierda.component.css']
})
export class PublicidadIzquierdaComponent {
  @Input() public no_exclusiva: any = null;
  @Input() public exclusiva: any = null;
  public publicidades: Publicidad[] = [];
  public publicidad_abajo_izquierda: any;
  public publicidad_arriba_izquierda: any;
  public publicidades_abajo_izquierda_random: any;
  public publicidades_arriba_izquierda_random: any;

  constructor(private publicidadesService: PublicationService){}

  processPublicidades(){
    this.publicidad_arriba_izquierda = this.publicidades.filter(pub => pub.id_tipo_banner === 1 || pub.id_tipo_banner === 5);
    this.publicidad_abajo_izquierda = this.publicidades.filter(pub => pub.id_tipo_banner === 4);
    this.publicidades_arriba_izquierda_random = this.obtenerElementoAleatorio(this.publicidad_arriba_izquierda);
    this.publicidades_abajo_izquierda_random = this.obtenerElementoAleatorio(this.publicidad_abajo_izquierda);
  }

  obtenerElementoAleatorio<T>(array: T[]): T {
    const indiceAleatorio = Math.floor(Math.random() * array.length);
    return array[indiceAleatorio];
  }
  ngOnInit(): void {
    this.publicidades = this.publicidadesService.getCurrenPublications();
    this.processPublicidades()
  }


}
