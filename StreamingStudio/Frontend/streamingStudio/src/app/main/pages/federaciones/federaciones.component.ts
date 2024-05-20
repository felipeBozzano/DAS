import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {IListadoFederaciones} from '../../api/models/IListadoFederacion.model';
import {AuthService} from '../../services/authService/AuthService';

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

  constructor(private authService: AuthService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.id_cliente = this.currentUser.id_cliente;
    this.route.data.subscribe(data => {
      this.federaciones = data['federaciones'];
      this.plataformas_a_federar =  this.federaciones.Plataformas_a_federar;
      this.plataformas_federadas = this.federaciones.Plataformas_federadas;
    })
  }
}
