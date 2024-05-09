import { BrowserModule } from '@angular/platform-browser';
import {ErrorHandler, NgModule} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppComponent } from './app.component';
import { LoginComponent } from './main/pages/login/login.component';
import { HomeComponent } from './main/pages/home/home.component';
import {RouterModule} from '@angular/router';
import {AppRoutingModule} from './app-routing.module';
import { RegisterComponent } from './main/pages/register/register.component';
import { SpinnerComponent } from './main/components/spinner/spinner.component';
import {SpinnerService} from './SpinnerService';
import {SpinnerInterceptor} from './SpinnerInterceptor';
import {AppHttpInterceptor} from './core/interceptors/app-http-interceptor';
import {AppErrorHandler} from './core/handlers/app-error-handler';
import {LoaderComponent} from './core/layouts/loader/loader.component';
import {CoreModule} from './core/core.module';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    RegisterComponent,
    SpinnerComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule,
    AppRoutingModule,
    CoreModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AppHttpInterceptor, multi: true },
    { provide: ErrorHandler, useClass: AppErrorHandler }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
