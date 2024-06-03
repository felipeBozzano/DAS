import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {AuthService} from "../../services/authService/AuthService";

@Component({
  selector: 'app-series',
  templateUrl: './series.component.html',
  styleUrls: ['./series.component.css']
})
export class SeriesComponent implements OnInit {

  public series: any
  user: any;

  constructor(private route: ActivatedRoute, private authService: AuthService) { }

  ngOnInit(): void {
    this.user = this.authService.getCurrentUser();
    this.route.data.subscribe(data => {
      this.series = data['series'];
      console.log(this.series);
    })
  }

  verDescripcion(id_contenido: String){
    let ruta: string = `/descripcion/${this.user.id_cliente}/${id_contenido}`;
    window.location.href = ruta;
  }

}
