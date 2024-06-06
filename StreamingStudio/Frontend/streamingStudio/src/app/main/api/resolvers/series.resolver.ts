import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {StreamingStudioResources} from '../resources/streaming-studio.services';
import {ISeriesModelResponse} from '../models/ISeries.model';

@Injectable()
export class SeriesResolver implements Resolve<ISeriesModelResponse> {
  constructor(private _service: StreamingStudioResources) { }

  resolve(route: ActivatedRouteSnapshot): ISeriesModelResponse | Observable<ISeriesModelResponse> | Promise<ISeriesModelResponse> {
    console.log(route.queryParams['id_cliente'])
    return this._service.series({id_cliente: route.queryParams['id_cliente']});
  }
}

