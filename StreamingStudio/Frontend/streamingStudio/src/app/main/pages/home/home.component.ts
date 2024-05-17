import {Component, OnInit, ViewChild} from '@angular/core';
import {AuthService} from '../../AuthService';
import {HeaderComponent} from '../../components/header/header.component';
import {ActivatedRoute} from '@angular/router';
import {IListadoFederaciones} from '../../api/models/IListadoFederacion.model';
import {IHome} from '../../api/models/IHome.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  currentUser: any;
  client_id: string = ''
  contenido: any | null;
  private home!: IHome;
  id_cliente: any;
  publicidadesTipo1: any[] = [];
  publicidadesTipo3: any[] = [];
  publicidadesTipo5: any[] = [];
  otrasPublicidades: any[] = [];
  destacados: any[] = [];
  masVistos: any[] = [];
  recientes: any[] = [];

  constructor(private authService: AuthService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.client_id = this.currentUser.id_cliente;
    console.log("user: ", this.currentUser);
    console.log("client_id: ", this.client_id);
    this.route.data.subscribe(data => {
      console.log("Data: ", data);
      this.home = data['home'];
      console.log("home: ", this.home);
    })
    this.processData(this.home);
  }

  processData(data: any): void {
    // Organizar las publicidades
    const publicidades = data.Publicidades;
    this.publicidadesTipo1 = publicidades['1'] || [];
    this.publicidadesTipo3 = publicidades['3'] || [];
    this.publicidadesTipo5 = publicidades['5'] || [];
    this.otrasPublicidades = Object.keys(publicidades)
      .filter(key => !['1', '3', '5'].includes(key))
      .flatMap(key => publicidades[key]);

    // Organizar los contenidos
    this.destacados = this.extractContenido(data.Destacado);
    this.masVistos = this.extractContenido(data.Mas_Visto);
    this.recientes = this.extractContenido(data.Reciente);
  }

  extractContenido(contenidoData: any): any[] {
    return Object.keys(contenidoData).flatMap(key => contenidoData[key]);
  }

  recibirMensaje(response: any) {
    this.contenido = response;
    console.log("desde el home: ", this.contenido);
  }

}
