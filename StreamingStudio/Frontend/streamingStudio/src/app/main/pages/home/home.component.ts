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
  public id_cliente: any;
  public destacados: any[] = [];
  public masVistos: any[] = [];
  public recientes: any[] = [];
  public publicidades: any[] = [];

  constructor(private authService: AuthService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.client_id = this.currentUser.id_cliente;
    console.log("user: ", this.currentUser);
    console.log("client_id: ", this.client_id);
    this.route.data.subscribe(data => {
      console.log("Data: ", data);
      this.home = data['home'];
      this.publicidades = Object.values(this.home.Publicidades).flat();
      this.processData(this.home);
      console.log("publicidades: ", this.publicidades);
      console.log("home: ", this.home);
    })
  }

  processData(data: any): void {
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
