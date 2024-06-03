import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/authService/AuthService';
import {ActivatedRoute} from '@angular/router';
import * as localForage from 'localforage';
import {StreamingStudioResources} from "../../api/resources/streaming-studio.services";
import {DomSanitizer, SafeHtml} from '@angular/platform-browser';
import {resourceChangeTicket} from '@angular/compiler-cli/src/ngtsc/core';

@Component({
  selector: 'app-descripcion',
  templateUrl: './descripcion.component.html',
  styleUrls: ['./descripcion.component.css']
})
export class DescripcionComponent implements OnInit {
  public currentUser: any;
  public id_cliente: string = '';
  public descripcion: any;
  public id_contenido: string = '';
  public video_flag: boolean = false;
  public video_tag_HTML: SafeHtml = '';

  constructor(private authService: AuthService, private route: ActivatedRoute, private streamingStudioResources: StreamingStudioResources, private sanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
    localForage.config({
      driver: localForage.LOCALSTORAGE,
      name: 'StreamingStudio'
    });
    this.currentUser = this.authService.getCurrentUser();
    this.id_cliente = this.currentUser.id_cliente;
    this.route.params.subscribe(params => {
      this.id_contenido = params["id_contenido"]
    })
    this.route.data.subscribe(data => {
      this.descripcion = data['descripcion'];
      console.log("this.descripcion: ", this.descripcion);
    })
  }

  obtenerUrlDeContenido(id_plataforma: number): void {
    const clic = {
      id_cliente: this.currentUser.id_cliente,
      id_plataforma: id_plataforma,
      id_contenido: this.id_contenido
    }
    console.log(clic);
    this.streamingStudioResources.clic_contenido(clic).subscribe(response =>{
      console.log(response);
    })
    console.log("Ejecutando obtenerUrlDeContenido")
    const body: any = {
      id_cliente: this.id_cliente,
      id_contenido: this.id_contenido,
      id_plataforma: id_plataforma,
    }
    console.log("Body: ", body)
    this.streamingStudioResources.obtener_url_de_contenido(body).subscribe((response) => {
      this.video_tag_HTML = this.sanitizer.bypassSecurityTrustHtml(response.url);
      this.video_flag = true;
      console.log("video_flag: ", this.video_flag)
      console.log("video_tag_html: ", this.video_tag_HTML)
    })
  }

}
