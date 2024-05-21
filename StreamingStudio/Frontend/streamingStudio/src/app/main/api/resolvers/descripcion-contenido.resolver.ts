import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {IListadoFederaciones} from '../models/IListadoFederacion.model';
import {StreamingStudioResources} from '../resources/streaming-studio.services';
import {IInformacionContenidoResponseModel} from '../models/IInformacionContenidoResponse.model';

@Injectable()
export class DescripcionContenidoResolverResolver implements Resolve<IInformacionContenidoResponseModel> {
  constructor(private _service: StreamingStudioResources) { }

  resolve(route: ActivatedRouteSnapshot): IInformacionContenidoResponseModel | Observable<IInformacionContenidoResponseModel> | Promise<IInformacionContenidoResponseModel> {
    return this._service.descripcion({id_cliente: route.params['id_cliente'], id_contenido: route.params['id_contenido']});
  }
}
