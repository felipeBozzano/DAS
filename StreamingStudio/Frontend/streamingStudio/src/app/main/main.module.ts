
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
import {ContenidoComponent} from './pages/contenido/contenido.component';
import {HomeResolver} from './api/resolvers/home.resolver';
import {PublicidadArribaComponent} from './components/publicidades-arriba/publicidad-arriba.component';
import {PublicationService} from './services/publicationService/publicationService';
import { PublicidadAbajoComponent } from './components/publicidades-abajo/publicidades-abajo.component';
import {DescripcionComponent} from './pages/descripcion/descripcion.component';
import {DescripcionContenidoResolverResolver} from './api/resolvers/descripcion-contenido.resolver';
import {DescripcionContenidoHomeResolverResolver} from './api/resolvers/descripcion-contenido-home.resolver';


// @ts-ignore
@NgModule({
  declarations: [
    FederacionesComponent,
    HeaderComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    ContenidoComponent,
    PublicidadArribaComponent,
    PublicidadAbajoComponent,
    DescripcionComponent
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
    StreamingStudioResources,
    PublicationService,
    DescripcionContenidoResolverResolver,
    DescripcionContenidoHomeResolverResolver
  ]
})
export class MainModule { }
