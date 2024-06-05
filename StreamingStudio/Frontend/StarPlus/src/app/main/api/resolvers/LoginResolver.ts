import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {IVerificacionAutorizacionResponseModel} from '../models/IVerificacionAutorizacionResponse.model';
import {StarPlusResourceService} from '../resources/starPlus-resource.service';

@Injectable()
export class LogiResolver implements Resolve<IVerificacionAutorizacionResponseModel> {
  constructor(private _service: StarPlusResourceService) { }

  resolve(route: ActivatedRouteSnapshot): IVerificacionAutorizacionResponseModel | Observable<IVerificacionAutorizacionResponseModel> | Promise<IVerificacionAutorizacionResponseModel> {
    return this._service.verificar_autorizacion({codigo_de_transaccion: route.queryParams['codigo_de_transaccion']});
  }
}