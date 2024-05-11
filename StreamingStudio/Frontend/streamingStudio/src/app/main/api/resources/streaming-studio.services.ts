import { Injectable } from '@angular/core';
import { IResourceMethodObservable, Resource, ResourceAction, ResourceParams, ResourceRequestBodyType, ResourceRequestMethod, ResourceResponseBodyType } from '@kkoehn/ngx-resource-core';
import { environment } from 'src/environments/environment';
import {ILogin} from '../models/login.model';
import {ILoginResponse} from '../models/ILoginResponse.model';
import {IUser} from '../models/IUser.model';

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
}
