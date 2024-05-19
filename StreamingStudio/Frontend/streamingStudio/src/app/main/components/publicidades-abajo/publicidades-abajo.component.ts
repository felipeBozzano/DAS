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

  constructor(private publicidadesService: PublicationService){}

  processPublicidades(){
    this.publicidad_abajo_izquierda = this.publicidades.filter(pub => pub.id_tipo_banner === 3);
    this.publicidad_abajo_derecha = this.publicidades.filter(pub => pub.id_tipo_banner === 4);
    console.log("publicidad_abajo_izquierda: ", this.publicidad_abajo_izquierda);
    console.log("publicidad_abajo_derecha: ", this.publicidad_abajo_derecha);
  }

  ngOnInit(): void {
    this.publicidades = this.publicidadesService.getCurrenPublications();
    this.processPublicidades()
  }


}
