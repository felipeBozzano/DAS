import { Injectable } from '@angular/core';
import {Resolve} from '@angular/router';
import {Observable} from 'rxjs';
import {StreamingStudioResources} from '../resources/streaming-studio.services';
import {IListadoFederacionesResponse} from "../models/IListadoFederacionResponse.model";
import {IListadoFederaciones} from "../models/IListadoFederacion.model";
import {AuthService} from "../../services/authService/AuthService";

@Injectable()
export class ListadoFederacionesResolver implements Resolve<IListadoFederacionesResponse> {
  constructor(private _authService: AuthService, private _service: StreamingStudioResources) { }

  resolve(): IListadoFederacionesResponse | Observable<IListadoFederacionesResponse> | Promise<IListadoFederacionesResponse> {
    const cliente: IListadoFederaciones = {id_cliente: String(this._authService.getCurrentUser().id_cliente)}
    return this._service.listar_federaciones(cliente);
  }
}
