import { Component, OnInit } from '@angular/core';
import {StreamingStudioResources} from '../../api/resources/streaming-studio.services';
import {ActivatedRoute} from '@angular/router';
import {IFinalizarFederacion} from '../../api/models/IFinalizarFederacion.model';

@Component({
  selector: 'app-usuario-federacion',
  templateUrl: './usuario-federacion.component.html',
  styleUrls: ['./usuario-federacion.component.css']
})
export class UsuarioFederacionComponent implements OnInit {
  public id_cliente: string = '';
  public id_plataforma: string = '';
  public codigo_de_transaccion: string = '';


  constructor(private streamingStudioResources: StreamingStudioResources, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.route.url.subscribe(urlSegments => {
        // Los segmentos de la URL están en el array urlSegments
        const segments = urlSegments.map(segment => segment.path);
        this.id_cliente = segments[1];
        this.id_plataforma = segments[3];
      });
      this.route.queryParams.subscribe(params => {
        this.codigo_de_transaccion = params['codigo_de_transaccion'];
      });
      const federacion: IFinalizarFederacion = {
        id_cliente: parseInt(this.id_cliente),
        id_plataforma: this.id_plataforma,
        codigo_de_transaccion: this.codigo_de_transaccion
      }
      console.log(federacion);
      this.streamingStudioResources.finalizar_federacion(federacion).subscribe((repsonse) =>{
        window.location.href = "http://localhost:4200/federaciones"
      })
    })
  }
}
