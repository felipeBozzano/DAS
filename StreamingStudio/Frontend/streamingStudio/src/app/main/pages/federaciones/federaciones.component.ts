import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {IListadoFederaciones} from '../../api/models/IListadoFederacion.model';

@Component({
  selector: 'app-federaciones',
  templateUrl: './federaciones.component.html',
  styleUrls: ['./federaciones.component.css']
})
export class FederacionesComponent implements OnInit {

  private _route!: ActivatedRoute;
  private federaciones!: IListadoFederaciones;

  constructor() { }

  ngOnInit(): void {
    console.log("hola");
    this._route.data.subscribe(data => {
      this.federaciones = data['federaciones'];
      console.log("federaciones: ", this.federaciones);
    })
  }
}
