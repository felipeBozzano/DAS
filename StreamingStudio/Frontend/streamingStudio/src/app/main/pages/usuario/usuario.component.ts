import { Component, OnInit } from '@angular/core';
import {StreamingStudioResources} from '../../api/resources/streaming-studio.services';
import {ActivatedRoute} from '@angular/router';
import {IFinalizarFederacion} from '../../api/models/IFinalizarFederacion.model';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.css']
})
export class UsuarioComponent implements OnInit {
  codigoTransaccion: any;
  id_cliente: any
  id_plataforma: any;

  constructor(private route: ActivatedRoute, private streamingStudioResources: StreamingStudioResources) { }

  ngOnInit(): void {
  this.route.queryParams.subscribe(params => {
    this.codigoTransaccion = params['codigo_de_transaccion'];
  });
    this.id_cliente = this.route.snapshot.paramMap.get('id_cliente');
    this.id_plataforma = this.route.snapshot.paramMap.get('id_plataforma');
    const federacion: IFinalizarFederacion = {
      codigo_de_transaccion: this.codigoTransaccion,
      id_cliente: this.id_cliente,
      id_plataforma: this.id_plataforma
    }
    console.log("federacion: ", federacion);
      this.streamingStudioResources.finalizar_federacion(federacion).subscribe((response) =>{
        console.log(response);
      })
  }

}
