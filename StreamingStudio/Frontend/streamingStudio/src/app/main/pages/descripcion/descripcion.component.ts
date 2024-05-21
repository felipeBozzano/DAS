import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/authService/AuthService';
import {ActivatedRoute} from '@angular/router';
import * as localForage from 'localforage';
import {StreamingStudioResources} from "../../api/resources/streaming-studio.services";

@Component({
  selector: 'app-descripcion',
  templateUrl: './descripcion.component.html',
  styleUrls: ['./descripcion.component.css']
})
export class DescripcionComponent implements OnInit {
  public currentUser: any;
  public id_cliente: string = '';
  public descripcion: any;
  public video: any;

  constructor(private authService: AuthService, private route: ActivatedRoute, private streamingStudioResources: StreamingStudioResources) { }

  ngOnInit(): void {
    localForage.config({
      driver: localForage.LOCALSTORAGE,
      name: 'StreamingStudio'
    });
    this.currentUser = this.authService.getCurrentUser();
    this.id_cliente = this.currentUser.id_cliente;
    this.route.data.subscribe(data => {
      this.descripcion = data['descripcion'];
      console.log("this.descripcion: ", this.descripcion);
    })
  }

  obtenerUrlDeContenido(): void {
    this.streamingStudioResources.obtener_url_de_contenido()
  }

}
