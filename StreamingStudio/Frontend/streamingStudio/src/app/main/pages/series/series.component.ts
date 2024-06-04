import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from "../../services/authService/AuthService";

@Component({
  selector: 'app-series',
  templateUrl: './series.component.html',
  styleUrls: ['./series.component.css']
})
export class SeriesComponent implements OnInit {

  public series: any
  public federaciones: any
  user: any;

  constructor(private route: ActivatedRoute, private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.user = this.authService.getCurrentUser();
    this.route.data.subscribe(data => {
      this.series = data['series'];
      this.federaciones = data['federaciones'];
    })
  }

  verDescripcion(id_contenido: String){
    let ruta: string = `/descripcion/${this.user.id_cliente}/${id_contenido}`;
    this.router.navigate([ruta]);
  }

}
