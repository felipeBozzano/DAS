import { BrowserModule } from '@angular/platform-browser';
import {ErrorHandler, Injectable, NgModule} from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppComponent } from './app.component';
import {RouterModule} from '@angular/router';
import {AppRoutingModule} from './app-routing.module';
import { SpinnerComponent } from './main/components/spinner/spinner.component';
import {AppHttpInterceptor} from './core/interceptors/app-http-interceptor';
import {AppErrorHandler} from './core/handlers/app-error-handler';
import {CoreModule} from './core/core.module';
import { ResourceModule } from '@kkoehn/ngx-resource-handler-ngx-http';
import {MainModule} from './main/main.module';
import {StreamingStudioResources} from './main/api/resources/streaming-studio.services';

class IResourceRequestBody {
}

class IResourceResponseBody {
}

class ResourceResponse {
}

@NgModule({
  declarations: [
    AppComponent,
    SpinnerComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule,
    AppRoutingModule,
    CoreModule,
    MainModule,
    ResourceModule.forRoot(),
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AppHttpInterceptor, multi: true },
    { provide: ErrorHandler, useClass: AppErrorHandler },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
