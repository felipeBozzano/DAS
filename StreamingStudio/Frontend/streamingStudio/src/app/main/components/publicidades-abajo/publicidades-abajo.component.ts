import { Component, Input } from '@angular/core';
import {PublicationService} from '../../services/publicationService/publicationService';

interface Publicidad {
  id_tipo_banner: number;
  id_publicidad: number;
  url_de_imagen: string;
  url_de_publicidad: string;
}

@Component({
  selector: 'app-publicidad-abajo',
  templateUrl: './publicidades-abajo.component.html',
  styleUrls: ['./publicidades-abajo.component.css']
})
export class PublicidadAbajoComponent {
  public publicidades: Publicidad[] = [];
  public publicidad_abajo_izquierda: any;
  public publicidad_abajo_derecha: any;
  public publicidades_abajo_izquierda_random: any;
  public publicidades_abajo_derecha_random: any;

  constructor(private publicidadesService: PublicationService){}

  processPublicidades(){
    this.publicidad_abajo_izquierda = this.publicidades.filter(pub => pub.id_tipo_banner === 3);
    this.publicidad_abajo_derecha = this.publicidades.filter(pub => pub.id_tipo_banner === 4);
    //this.publicidades_abajo_izquierda_random = this.obtenerElementoAleatorio(this.publicidad_abajo_izquierda);
    //this.publicidades_abajo_derecha_random = this.obtenerElementoAleatorio(this.publicidad_abajo_derecha);
    //console.log("publicidades_abajo_izquierda_random: ", this.publicidades_abajo_izquierda_random);
    //console.log("publicidades_abajo_derecha_radom: ", this.publicidades_abajo_derecha_random);
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
