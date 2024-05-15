import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {IListadoFederaciones} from '../models/IListadoFederacion.model';
import {StreamingStudioResources} from '../resources/streaming-studio.services';

@Injectable()
export class ResolverService implements Resolve<IListadoFederaciones> {

  constructor(private _service: StreamingStudioResources) {
  }

  resolve(): IListadoFederaciones | Observable<IListadoFederaciones> | Promise<IListadoFederaciones> {
    return this._service.federaciones();
  }
}

  @Injectable()
  export class ListadoFederacionesResolver implements Resolve<IListadoFederaciones> {
  constructor(private _service: StreamingStudioResources) { }

  resolve(route: ActivatedRouteSnapshot): IListadoFederaciones | Observable<IListadoFederaciones> | Promise<IListadoFederaciones> {
    console.log("id_cliente: ", this._service.federaciones({id_cliente: route.paramMap.get('id_cliente')}));
    return this._service.federaciones({id_cliente: route.paramMap.get('id_cliente')});
  }
}
