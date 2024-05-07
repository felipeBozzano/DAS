import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { SpinnerService } from './SpinnerService'; // Importa el servicio del spinner

@Injectable()
export class SpinnerInterceptor implements HttpInterceptor {
  constructor(private spinnerService: SpinnerService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    this.spinnerService.show(); // Muestra el spinner al comenzar la solicitud

    return next.handle(request).pipe(
      finalize(() => {
        this.spinnerService.hide(); // Oculta el spinner al finalizar la solicitud
      })
    );
  }
}
