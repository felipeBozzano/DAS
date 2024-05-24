import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {IListadoFederaciones} from '../../api/models/IListadoFederacion.model';
import {AuthService} from '../../services/authService/AuthService';
import * as localForage from 'localforage';
import {StreamingStudioResources} from '../../api/resources/streaming-studio.services';
import {IFinalizarFederacion} from '../../api/models/IFinalizarFederacion.model';
import {IComenzarFederacionModel} from "../../api/models/IComenzarFederacion.model";

@Component({
  selector: 'app-federaciones',
  templateUrl: './federaciones.component.html',
  styleUrls: ['./federaciones.component.css']
})
export class FederacionesComponent implements OnInit {

  public federaciones: any;
  private currentUser: any;
  public id_cliente: string = '';
  public plataformas_a_federar: any;
  public plataformas_federadas: any;
  public isModalOpen = false;
  public plataforma_seleccionada: any;

  constructor(private authService: AuthService, private route: ActivatedRoute, private streamingStudioResources: StreamingStudioResources, private router: Router) { }

  ngOnInit(): void {
    localForage.config({
      driver: localForage.LOCALSTORAGE,
      name: 'StreamingStudio'
    });
    this.currentUser = this.authService.getCurrentUser();
    this.id_cliente = this.currentUser.id_cliente;
    this.route.data.subscribe(data => {
      this.federaciones = data['federaciones'];
      this.plataformas_a_federar =  this.federaciones.Plataformas_a_federar;
      console.log("plataformas_a_federar: ", this.plataformas_a_federar);
      this.plataformas_federadas = this.federaciones.Plataformas_federadas;
    })
  }

  openModal(plataforma: any): void {
    this.plataforma_seleccionada = plataforma;
    this.isModalOpen = true;
  }

  closeModal(event: MouseEvent): void {
    event.preventDefault();
    this.isModalOpen = false;
  }

  navigateToLogin(plataforma: any, tipo_de_transaccion: string): void {
    console.log(plataforma)

    const info_federacion: IComenzarFederacionModel = {
      id_plataforma: plataforma.id_plataforma,
      id_cliente: this.id_cliente,
      tipo_de_transaccion: tipo_de_transaccion
    }

    this.streamingStudioResources.comenzar_federacion(info_federacion).subscribe((response) => {
      console.log(response.url_login_registro_plataforma + '?codigo_de_transaccion=' + response.codigo_de_transaccion);
      const ruta: any = response.url_login_registro_plataforma + '?codigo_de_transaccion=' + response.codigo_de_transaccion
      window.location.href = ruta;
    })
  }

  navigateToRegister(plataforma: any, tipo_de_transaccion: string): void {
    console.log(plataforma)

    const info_federacion: IComenzarFederacionModel = {
      id_plataforma: plataforma.id_plataforma,
      id_cliente: this.id_cliente,
      tipo_de_transaccion: tipo_de_transaccion
    }

    this.streamingStudioResources.comenzar_federacion(info_federacion).subscribe((response) => {
      const ruta: any = response.url_login_registro_plataforma + '?codigo_de_transaccion=' + response.codigo_de_transaccion
      window.location.href = ruta;
    })
  }

  finalizarFederacion(plataforma: any){
    console.log(plataforma);
    const federacionAFinalizar: any = {
        codigo_de_transaccion: "codigo_de_transaccion_1",
        id_cliente_plataforma: "id_cliente_plataforma_1"
    }
    this.streamingStudioResources.finalizar_federacion(federacionAFinalizar, plataforma.id_plataforma, this.id_cliente).subscribe((response) => {
      console.log(response);
      this.router.navigate(['/federaciones'])
    })

  }
}
