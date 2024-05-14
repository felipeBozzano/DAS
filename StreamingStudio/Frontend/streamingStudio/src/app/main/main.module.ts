
import { NgModule } from '@angular/core';
import {StreamingStudioResources} from './api/resources/streaming-studio.services';
import {HomeComponent} from './pages/home/home.component';
import {LoginComponent} from './pages/login/login.component';
import {RegisterComponent} from './pages/register/register.component';
import {CommonModule} from '@angular/common';
import {MainRoutingModule} from './main-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HeaderComponent} from './components/header/header.component';
import {FederacionesComponent} from './pages/federaciones/federaciones.component';


// @ts-ignore
@NgModule({
  declarations: [
    FederacionesComponent,
    HeaderComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    CommonModule,
    MainRoutingModule,
    ReactiveFormsModule,
    FormsModule,
  ],
  providers: [
    StreamingStudioResources
  ]
})
export class MainModule { }