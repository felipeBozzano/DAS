import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {IListadoFederaciones} from '../models/IListadoFederacion.model';
import {StreamingStudioResources} from '../resources/streaming-studio.services';
import {IHome} from '../models/IHome.model';

@Injectable()
export class HomeResolver implements Resolve<IHome> {
  constructor(private _service: StreamingStudioResources) { }

  resolve(route: ActivatedRouteSnapshot): IHome | Observable<IHome> | Promise<IHome> {
    console.log("route.params['id_cliente']: ", route.paramMap.get('id_cliente'));
    return this._service.home({id_cliente: route.params['id_cliente']});
  }
}
