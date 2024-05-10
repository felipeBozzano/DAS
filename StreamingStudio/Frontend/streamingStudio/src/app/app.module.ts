import { BrowserModule } from '@angular/platform-browser';
import {ErrorHandler, NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppComponent } from './app.component';
import { LoginComponent } from './main/pages/login/login.component';
import { HomeComponent } from './main/pages/home/home.component';
import {RouterModule} from '@angular/router';
import {AppRoutingModule} from './app-routing.module';
import { RegisterComponent } from './main/pages/register/register.component';
import { SpinnerComponent } from './main/components/spinner/spinner.component';
import {AppHttpInterceptor} from './core/interceptors/app-http-interceptor';
import {AppErrorHandler} from './core/handlers/app-error-handler';
import {CoreModule} from './core/core.module';
import {StreamingStudioResources} from './main/api/resources/streaming-studio.resources';
import {MatCardModule} from '@angular/material/card';
import {ResourceHandler, ResourceModule} from '@kkoehn/ngx-resource-core';
import {MainModule} from './main/main.module';

@NgModule({
  declarations: [
    AppComponent,
    SpinnerComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule,
    AppRoutingModule,
    CoreModule,
    MainModule,
    ResourceModule.forRoot()
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AppHttpInterceptor, multi: true },
    { provide: ErrorHandler, useClass: AppErrorHandler },
    StreamingStudioResources,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
