import { Component, Input } from '@angular/core';
import {PublicationService} from '../../services/publicationService/publicationService';

interface Publicidad {
  id_tipo_banner: number;
  id_publicidad: number;
  url_de_imagen: string;
  url_de_publicidad: string;
}

@Component({
  selector: 'app-publicidad',
  templateUrl: './publicidad.component.html',
  styleUrls: ['./publicidad.component.css']
})
export class PublicidadComponent {
  @Input() publicidades: Publicidad[] = [];
  @Input() tipo: 'exclusiva' | 'no_exclusiva' = 'no_exclusiva';
  exclusivas: any  = []
  no_exclusivas: any = []

  constructor(private publicidadesService: PublicationService){}

  processPublicidades(){
    this.publicidades.map(pub => {
      if(pub.id_tipo_banner == 3 || pub.id_tipo_banner == 5 || pub.id_tipo_banner == 1){
        this.exclusivas.push(pub)
      }else{
        this.no_exclusivas.push(pub)
      }
    })
  }
  ngOnInit(): void {
    this.publicidades = this.publicidadesService.getCurrenPublications();
    this.processPublicidades()
  }


}
