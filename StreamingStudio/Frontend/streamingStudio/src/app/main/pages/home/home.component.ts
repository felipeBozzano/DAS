import {Component, OnInit, ViewChild} from '@angular/core';
import {AuthService} from '../../services/authService/AuthService';
import {ActivatedRoute, Router} from '@angular/router';
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

  constructor(private authService: AuthService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.client_id = this.currentUser.id_cliente;
    this.route.data.subscribe(data => {
      this.home = data['home'];
      this.processData(this.home);
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
  }

  navigateTo(id_contenido: any){
    const path = `/descripcion/${this.client_id}/${id_contenido}`
    this.router.navigate([`/${path}`]);
  }
}
