import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {StreamingStudioResources} from '../resources/streaming-studio.services';
import {IPeliculasModelResonse} from '../models/IPeliculas.model';

@Injectable()
export class PeliculasResolver implements Resolve<IPeliculasModelResonse> {
  constructor(private _service: StreamingStudioResources) { }

  resolve(route: ActivatedRouteSnapshot): IPeliculasModelResonse | Observable<IPeliculasModelResonse> | Promise<IPeliculasModelResonse> {
    return this._service.peliculas();
  }
}

