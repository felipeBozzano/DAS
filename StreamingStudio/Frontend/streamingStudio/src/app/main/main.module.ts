
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
import {ListadoFederacionesResolver} from './api/resolvers/listado-federaciones.service';
import {ContenidoComponent} from './pages/contenido/contenido.component';
import {HomeResolver} from './api/resolvers/home.resolver';
import {PublicidadDerechaComponent} from './components/publicidades-derecha/publicidad-derecha.component';
import {PublicationService} from './services/publicationService/publicationService';
import {DescripcionComponent} from './pages/descripcion/descripcion.component';
import {DescripcionContenidoResolverResolver} from './api/resolvers/descripcion-contenido.resolver';
import {DescripcionContenidoHomeResolverResolver} from './api/resolvers/descripcion-contenido-home.resolver';
import { PublicidadIzquierdaComponent } from './components/publicidades-izquierda/publicidades-izquierda.component';
import {UsuarioComponent} from './pages/usuario/usuario.component';
import {PeliculasComponent} from './pages/peliculas/peliculas.component';
import {SeriesComponent} from './pages/series/series.component';
import {PeliculasResolver} from './api/resolvers/peliculas.resolver';
import {SeriesResolver} from './api/resolvers/series.resolver';
import {SplashComponent} from './pages/splash/splash.component';
import {UsuarioFederacionComponent} from './pages/usuario-federacion/usuario-federacion.component';


// @ts-ignore
@NgModule({
  declarations: [
    FederacionesComponent,
    HeaderComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    ContenidoComponent,
    PublicidadDerechaComponent,
    PublicidadIzquierdaComponent,
    DescripcionComponent,
    UsuarioComponent,
    PeliculasComponent,
    SeriesComponent,
    SplashComponent,
    UsuarioFederacionComponent
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
    DescripcionContenidoHomeResolverResolver,
    PeliculasResolver,
    SeriesResolver
  ]
})
export class MainModule { }
