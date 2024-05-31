import { Injectable } from '@angular/core';
import { IResourceMethodObservable, Resource, ResourceAction, ResourceParams, ResourceRequestBodyType, ResourceRequestMethod, ResourceResponseBodyType } from '@kkoehn/ngx-resource-core';
import { environment } from 'src/environments/environment';
import {IUser} from '../models/IUser.model';
import {ILogin} from '../models/login.model';
import {ILoginResponse} from '../models/ILoginResponse.model';
import {INuevaAutorizacionModel} from '../models/INuevaAutorizacion.mode';
import {IAutorizacionModel} from '../models/IAutorizacion.model';
import {IVerificacionAutorizacionResponseModel} from '../models/IVerificacionAutorizacionResponse.model';


@Injectable()
@ResourceParams({
  pathPrefix: `${environment.apiUrl}/prime_video`
})
export class PrimeVideoResourceService extends Resource{

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/login_user',
    requestBodyType: ResourceRequestBodyType.JSON,
    responseBodyType: ResourceResponseBodyType.Json
  })
  login!: IResourceMethodObservable<ILogin, ILoginResponse>;

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/create_user',
    requestBodyType: ResourceRequestBodyType.JSON,
    responseBodyType: ResourceResponseBodyType.Json
  })
  registro!: IResourceMethodObservable<IUser,void>;

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/verificar_autorizacion',
    requestBodyType: ResourceRequestBodyType.JSON,
    responseBodyType: ResourceResponseBodyType.Json
  })
  verificar_autorizacion!: IResourceMethodObservable<{codigo_de_transaccion: string}, IVerificacionAutorizacionResponseModel>;

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/crear_autorizacion',
    requestBodyType: ResourceRequestBodyType.JSON,
    responseBodyType: ResourceResponseBodyType.Json
  })
  crear_autorizacion!: IResourceMethodObservable<INuevaAutorizacionModel, IAutorizacionModel>;

}