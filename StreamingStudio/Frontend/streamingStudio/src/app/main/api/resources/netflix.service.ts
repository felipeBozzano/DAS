import { Injectable } from '@angular/core';
import { IResourceMethodObservable, Resource, ResourceAction, ResourceParams, ResourceRequestBodyType, ResourceRequestMethod, ResourceResponseBodyType } from '@kkoehn/ngx-resource-core';
import { environment } from 'src/environments/environment';
import {IVerificacionAutorizacionResponseModel} from '../models/VereficacionAutorizacion.model';

@Injectable()
@ResourceParams({
  pathPrefix: `${environment.apiUrlNetflix}/Netflix`
})
export class NetflixResources extends Resource{

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/usuario/{id_cliente}/obtener_token',
    requestBodyType: ResourceRequestBodyType.JSON,
    responseBodyType: ResourceResponseBodyType.Json
  })
  obtener_token!: IResourceMethodObservable<{id_cliente: number},IVerificacionAutorizacionResponseModel>;

}
