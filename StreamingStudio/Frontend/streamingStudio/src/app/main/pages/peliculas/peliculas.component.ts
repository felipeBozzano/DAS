import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from "../../services/authService/AuthService";

@Component({
  selector: 'app-peliculas',
  templateUrl: './peliculas.component.html',
  styleUrls: ['./peliculas.component.css']
})
export class PeliculasComponent implements OnInit {

  public peliculas: any;
  public federaciones: any
  constructor(private route: ActivatedRoute, private authService: AuthService, private router: Router) { }
  user: any;
  ngOnInit(): void {
    this.user = this.authService.getCurrentUser();
    this.route.data.subscribe(data => {
      this.peliculas = data['peliculas'];
      this.federaciones = data['federaciones'];
      console.log(this.peliculas);
    })
  }

  verDescripcion(id_contenido: String){
    let ruta: string = `/descripcion/${this.user.id_cliente}/${id_contenido}`;
    this.router.navigate([ruta]);
  }

}
