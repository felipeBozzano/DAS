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
  private _route!: ActivatedRoute;
  private home!: IHome;
  id_cliente: any;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this._route.queryParams.subscribe(params => {
      this.id_cliente = +params['id_cliente'] || 0;
      console.log(this.id_cliente);
    })

    this.currentUser = this.authService.getCurrentUser();
    this.client_id = this.currentUser.id_cliente;
    console.log("user: ", this.currentUser);
    console.log("client_id: ", this.client_id);
    this._route.data.subscribe(data => {
      this.home = data['home'];
      console.log("home: ", this.home);
    })
  }

  recibirMensaje(response: any) {
    this.contenido = response;
    console.log("desde el home: ", this.contenido);
  }

}
