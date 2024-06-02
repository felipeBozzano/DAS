import { Component, Input } from '@angular/core';
import {PublicationService} from '../../services/publicationService/publicationService';
import {AuthService} from '../../services/authService/AuthService';
import {StreamingStudioResources} from '../../api/resources/streaming-studio.services';

interface Publicidad {
  id_tipo_banner: number;
  id_publicidad: number;
  url_de_imagen: string;
  url_de_publicidad: string;
}

@Component({
  selector: 'app-publicidad-derecha',
  templateUrl: './publicidad-derecha.component.html',
  styleUrls: ['./publicidad-derecha.component.css']
})
export class PublicidadDerechaComponent {
  @Input() public no_exclusiva: any = null;
  @Input() public exclusiva: any = null;
  @Input() publicidades: Publicidad[] = [];
  public publicidad_abajo_derecha: any;
  public publicidad_arriba_derecha: any;
  public publicidades_arriba_derecha_random: any;
  public publicidades_abajo_derecha_radom: any;

  constructor(private publicidadesService: PublicationService, private authService: AuthService, private streamingStudioResources: StreamingStudioResources){}
  user: any

  processPublicidades(){
    this.publicidad_abajo_derecha = this.publicidades.filter(pub => pub.id_tipo_banner === 3 );
    this.publicidad_arriba_derecha = this.publicidades.filter(pub => pub.id_tipo_banner === 2 || pub.id_tipo_banner === 6);
    this.publicidades_arriba_derecha_random = this.obtenerElementoAleatorio(this.publicidad_arriba_derecha);
    this.publicidades_abajo_derecha_radom = this.obtenerElementoAleatorio(this.publicidad_abajo_derecha);
  }

  obtenerElementoAleatorio<T>(array: T[]): T {
    const indiceAleatorio = Math.floor(Math.random() * array.length);
    return array[indiceAleatorio];
  }

  ngOnInit(): void {
    this.user = this.authService.getCurrentUser();
    this.publicidades = this.publicidadesService.getCurrenPublications();
    this.processPublicidades()
  }

   registrarClic(id_publicidad: String){
     const clic = {
       id_cliente: this.user.id_cliente,
       id_publicidad: id_publicidad
     }
    this.streamingStudioResources.clic_publicidad(clic).subscribe(response =>{
      console.log(response);
    })
  }
}
