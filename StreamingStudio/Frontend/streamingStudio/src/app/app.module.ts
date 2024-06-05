import { BrowserModule } from '@angular/platform-browser';
import {ErrorHandler, NgModule} from '@angular/core';
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
import {SafePipe} from "./main/Pipes/SafePipe";


@NgModule({
    declarations: [
        AppComponent,
        SpinnerComponent,
        SafePipe
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
        {provide: HTTP_INTERCEPTORS, useClass: AppHttpInterceptor, multi: true},
        {provide: ErrorHandler, useClass: AppErrorHandler},
    ],
    exports: [
        SpinnerComponent,
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
