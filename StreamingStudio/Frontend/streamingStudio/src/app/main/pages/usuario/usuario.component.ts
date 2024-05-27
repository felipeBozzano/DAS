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
  id_cliente: any

  constructor(private route: ActivatedRoute, private streamingStudioResources: StreamingStudioResources) { }

  ngOnInit(): void {
    this.id_cliente = this.route.snapshot.paramMap.get('id_cliente');
  }

}
