import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {StreamingStudioResources} from '../resources/streaming-studio.services';
import {ISeriesModelResponse} from '../models/ISeries.model';

@Injectable()
export class SeriesResolver implements Resolve<ISeriesModelResponse> {
  constructor(private _service: StreamingStudioResources) { }

  resolve(route: ActivatedRouteSnapshot): ISeriesModelResponse | Observable<ISeriesModelResponse> | Promise<ISeriesModelResponse> {
    return this._service.series();
  }
}

