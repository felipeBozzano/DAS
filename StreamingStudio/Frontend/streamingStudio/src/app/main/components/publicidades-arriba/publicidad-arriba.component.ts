import { Component, Input } from '@angular/core';
import {PublicationService} from '../../services/publicationService/publicationService';

interface Publicidad {
  id_tipo_banner: number;
  id_publicidad: number;
  url_de_imagen: string;
  url_de_publicidad: string;
}

@Component({
  selector: 'app-publicidad-arriba',
  templateUrl: './publicidad-arriba.component.html',
  styleUrls: ['./publicidad-arriba.component.css']
})
export class PublicidadArribaComponent {
  @Input() publicidades: Publicidad[] = [];
  @Input() tipo: 'exclusiva' | 'no_exclusiva' = 'no_exclusiva';
  public publicidad_arriba_izquierda: any;
  public publicidad_arriba_derecha: any;
  public publicidad_abajo_izquierda: any;
  public publicidad_abajo_derecha: any;
  public publicidades_arriba_izquierda_random: any;
  public publicidades_arriba_derecha_radom: any;

  constructor(private publicidadesService: PublicationService){}

  processPublicidades(){
    console.log("puublicidades: ", this.publicidades);
    this.publicidad_arriba_izquierda = this.publicidades.filter(pub => pub.id_tipo_banner === 1 || pub.id_tipo_banner === 5);
    this.publicidad_arriba_derecha = this.publicidades.filter(pub => pub.id_tipo_banner === 2 || pub.id_tipo_banner === 6);
    this.publicidad_abajo_izquierda = this.publicidades.filter(pub => pub.id_tipo_banner === 3);
    this.publicidad_abajo_derecha = this.publicidades.filter(pub => pub.id_tipo_banner === 4);
    this.publicidades_arriba_izquierda_random = this.publicidad_arriba_izquierda = this.obtenerElementoAleatorio(this.publicidad_arriba_izquierda);
    this.publicidades_arriba_derecha_radom = this.publicidad_arriba_izquierda = this.obtenerElementoAleatorio(this.publicidad_arriba_derecha);
    console.log("publicidades_arriba_izquierda_random: ", this.publicidades_arriba_izquierda_random);
    console.log("publicidades_arriba_derecha_radom: ", this.publicidades_arriba_derecha_radom);
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
