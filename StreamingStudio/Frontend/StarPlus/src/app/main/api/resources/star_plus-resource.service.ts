import { Injectable } from '@angular/core';
import { IResourceMethodObservable, Resource, ResourceAction, ResourceParams, ResourceRequestBodyType, ResourceRequestMethod, ResourceResponseBodyType } from '@kkoehn/ngx-resource-core';
import { environment } from 'src/environments/environment';
import {IUser} from '../models/IUser.model';
import {ILogin} from '../models/login.model';
import {ILoginResponse} from '../models/ILoginResponse.model';



@Injectable()
@ResourceParams({
  pathPrefix: `${environment.apiUrl}/star_plus`
})
export class Star_plusResourceService extends Resource{

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/login',
    headers: {'Access-Control-Allow-Origin': '*'},
    requestBodyType: ResourceRequestBodyType.JSON,
    responseBodyType: ResourceResponseBodyType.Json
  })
  login!: IResourceMethodObservable<any, any>;

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/create_user',
    requestBodyType: ResourceRequestBodyType.JSON,
    responseBodyType: ResourceResponseBodyType.Json
  })
  registro!: IResourceMethodObservable<IUser,void>;

}
