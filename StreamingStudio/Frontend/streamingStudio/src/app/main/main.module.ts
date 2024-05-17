
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
import {ListadoFederacionesResolver} from './api/resolvers/resolver.service';
import {HttpClientModule} from '@angular/common/http';
import {ContenidoComponent} from './pages/contenido/contenido.component';
import {HomeResolver} from './api/resolvers/home.resolver';
import {PublicidadComponent} from './components/publicidad/publicidad.component';


// @ts-ignore
@NgModule({
  declarations: [
    FederacionesComponent,
    HeaderComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    ContenidoComponent,
    PublicidadComponent
  ],
  imports: [
    CommonModule,
    MainRoutingModule,
    ReactiveFormsModule,
    FormsModule,
  ],
  providers: [
    HomeResolver,
    ListadoFederacionesResolver,
    StreamingStudioResources
  ]
})
export class MainModule { }
