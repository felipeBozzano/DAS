import { Injectable } from '@angular/core';
import { IResourceMethodObservable, Resource, ResourceAction, ResourceParams, ResourceRequestBodyType, ResourceRequestMethod, ResourceResponseBodyType } from '@kkoehn/ngx-resource-core';
import { environment } from 'src/environments/environment';
import {ILogin} from '../models/login.model';
import {ILoginResponse} from '../models/ILoginResponse.model';
import {IUser} from '../models/IUser.model';
import {IListadoFederaciones} from '../models/IListadoFederacion.model';
import {IContenido} from '../models/IContenido.model';
import {IContenidoResponse} from '../models/IContenidoResponse.model';
import {IInformacionContenidoResponseModel} from '../models/IInformacionContenidoResponse.model';
import {IHome} from '../models/IHome.model';
import {IFinalizarFederacion} from '../models/IFinalizarFederacion.modal';
import {IUrlDeContenido} from "../models/IUrlDeContenido.model";
import {IUrlDeContenidoResponse} from "../models/IUrlDeContenidoResponse.model";

@Injectable()
@ResourceParams({
  pathPrefix: `${environment.apiUrl}/ss`
})
export class StreamingStudioResources extends Resource{

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/login_user',
    requestBodyType: ResourceRequestBodyType.JSON,
    responseBodyType: ResourceResponseBodyType.Json
  })
  login!: IResourceMethodObservable<ILogin,ILoginResponse>;

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/create_user',
    requestBodyType: ResourceRequestBodyType.JSON,
    responseBodyType: ResourceResponseBodyType.Json
  })
  registro!: IResourceMethodObservable<IUser,void>;

  @ResourceAction({
    method: ResourceRequestMethod.Get,
    path: `/usuario/{!id_cliente}/federaciones`,
    responseBodyType: ResourceResponseBodyType.Json
  })
  federaciones!: IResourceMethodObservable<{ id_cliente: string }, IListadoFederaciones>;

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: `/contenido_por_filtros`,
    requestBodyType: ResourceRequestBodyType.JSON,
    responseBodyType: ResourceResponseBodyType.Json
  })
  contenido!: IResourceMethodObservable<IContenido, IContenidoResponse[]>;

  @ResourceAction({
    method: ResourceRequestMethod.Get,
    path: `/informacion_contenido/{!id_contenido}/{!id_cliente}`,
    responseBodyType: ResourceResponseBodyType.Json
  })
  descripcion!: IResourceMethodObservable<{ id_cliente: number, id_contenido: string }, IInformacionContenidoResponseModel>;

  @ResourceAction({
    method: ResourceRequestMethod.Get,
    path: `/home`,
    responseBodyType: ResourceResponseBodyType.Json
  })
  home!: IResourceMethodObservable<{ id_cliente: number }, IHome>;

  @ResourceAction({
    method: ResourceRequestMethod.Get,
    path: `/publicidades_activas`,
    responseBodyType: ResourceResponseBodyType.Json
  })
  publicidades!: IResourceMethodObservable<{}, IHome>;

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: `/usuario/{id_cliente}/finalizar_federacion/{id_plataforma}`,
    requestBodyType: ResourceRequestBodyType.JSON,
    responseBodyType: ResourceResponseBodyType.Json
  })
  finalizar_federacion!: IResourceMethodObservable<{iFinalizarFederacion: IFinalizarFederacion, id_cliente: number, id_plataforma:number}, IHome>;

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: `/informacion_contenido/mostrar_video`,
    requestBodyType: ResourceRequestBodyType.JSON,
    responseBodyType: ResourceResponseBodyType.Json
  })
  obtener_url_de_contenido!: IResourceMethodObservable<{iUrlDeContenido: IUrlDeContenido}, IUrlDeContenidoResponse>;
}
