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
import {IFinalizarFederacion} from '../models/IFinalizarFederacion.model';
import {IUrlDeContenido} from "../models/IUrlDeContenido.model";
import {IUrlDeContenidoResponse} from "../models/IUrlDeContenidoResponse.model";
import {IListadoFederacionesResponse} from "../models/IListadoFederacionResponse.model";
import {IComenzarFederacionModel} from "../models/IComenzarFederacion.model";
import {IComenzarFederacionResponseModel} from "../models/IComenzarFederacionResponse.model";
import {IFederacionFinalizadaModel} from '../models/IFederacionFinalizada.model';
import {IPeliculasModelResonse} from '../models/IPeliculas.model';
import {ISeriesModelResponse} from '../models/ISeries.model';

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
    method: ResourceRequestMethod.Post,
    path: `/usuario/federaciones`,
    requestBodyType: ResourceRequestBodyType.JSON,
    responseBodyType: ResourceResponseBodyType.Json
  })
  listar_federaciones!: IResourceMethodObservable<IListadoFederaciones, IListadoFederacionesResponse>;

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: `/usuario/comenzar_federacion`,
    requestBodyType: ResourceRequestBodyType.JSON,
    responseBodyType: ResourceResponseBodyType.Json
  })
  comenzar_federacion!: IResourceMethodObservable<IComenzarFederacionModel, IComenzarFederacionResponseModel>;

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
    path: `/usuario/{!id_cliente}/finalizar_federacion/{!id_plataforma}`,
    requestBodyType: ResourceRequestBodyType.JSON,
    responseBodyType: ResourceResponseBodyType.Json
  })
  finalizar_federacion!: IResourceMethodObservable<IFinalizarFederacion, IFederacionFinalizadaModel>;

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: `/informacion_contenido/mostrar_video`,
    requestBodyType: ResourceRequestBodyType.JSON,
    responseBodyType: ResourceResponseBodyType.Json
  })
  obtener_url_de_contenido!: IResourceMethodObservable<{iUrlDeContenido: IUrlDeContenido}, IUrlDeContenidoResponse>;

  @ResourceAction({
    method: ResourceRequestMethod.Get,
    path: '/series',
    // requestBodyType: ResourceRequestBodyType.JSON,
    responseBodyType: ResourceResponseBodyType.Json
  })
  series!: IResourceMethodObservable<{ id_cliente: number }, ISeriesModelResponse>;

  @ResourceAction({
    method: ResourceRequestMethod.Get,
    path: '/peliculas',
    // requestBodyType: ResourceRequestBodyType.JSON,
    responseBodyType: ResourceResponseBodyType.Json
  })
  peliculas!: IResourceMethodObservable<{ id_cliente: number }, IPeliculasModelResonse>;

  @ResourceAction({
    method: ResourceRequestMethod.Get,
    path: `/clic_publicidad`,
    responseBodyType: ResourceResponseBodyType.Json
  })
  clic_publicidad!: IResourceMethodObservable<any, any>;

  @ResourceAction({
    method: ResourceRequestMethod.Get,
    path: `/clic_contenido`,
    responseBodyType: ResourceResponseBodyType.Json
  })
  clic_contenido!: IResourceMethodObservable<any, any>;

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: `/desvincular`,
    requestBodyType: ResourceRequestBodyType.JSON,
    responseBodyType: ResourceResponseBodyType.Json
  })
  desvincular!: IResourceMethodObservable<any, any>;

  @ResourceAction({
    method: ResourceRequestMethod.Put,
    path: `/actualizar_usuario`,
    requestBodyType: ResourceRequestBodyType.JSON,
    responseBodyType: ResourceResponseBodyType.Json
  })
  actualizar_usuario!: IResourceMethodObservable<any, any>;

  @ResourceAction({
    method: ResourceRequestMethod.Put,
    path: `/eliminar_usuario`,
    requestBodyType: ResourceRequestBodyType.JSON,
    responseBodyType: ResourceResponseBodyType.Json
  })
  eliminar_usuario!: IResourceMethodObservable<any, any>;
}
