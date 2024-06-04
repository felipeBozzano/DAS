import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HomeComponent} from './pages/home/home.component';
import {LoginComponent} from './pages/login/login.component';
import {RegisterComponent} from './pages/register/register.component';
import {AuthGuard} from './services/authService/AuthGuard';
import {ListadoFederacionesResolver} from './api/resolvers/listado-federaciones.service';
import {FederacionesComponent} from './pages/federaciones/federaciones.component';
import {ContenidoComponent} from './pages/contenido/contenido.component';
import {HomeResolver} from './api/resolvers/home.resolver';
import {DescripcionContenidoResolverResolver} from './api/resolvers/descripcion-contenido.resolver';
import {DescripcionComponent} from './pages/descripcion/descripcion.component';
import {DescripcionContenidoHomeResolverResolver} from './api/resolvers/descripcion-contenido-home.resolver';
import {UsuarioComponent} from './pages/usuario/usuario.component';
import {SeriesComponent} from './pages/series/series.component';
import {PeliculasComponent} from './pages/peliculas/peliculas.component';
import {PeliculasResolver} from './api/resolvers/peliculas.resolver';
import {SeriesResolver} from './api/resolvers/series.resolver';
import {SplashComponent} from './pages/splash/splash.component';
import {UsuarioFederacionComponent} from './pages/usuario-federacion/usuario-federacion.component';

const routes: Routes = [
  { path: '', component: SplashComponent},
  {path: 'login', component: LoginComponent},
  {path: 'home', component: HomeComponent, canActivate: [AuthGuard], resolve: {home: HomeResolver}},
  {path: 'register', component: RegisterComponent},
  {path: 'contenido', component: ContenidoComponent, canActivate: [AuthGuard]},
  {path: 'series', component: SeriesComponent, canActivate: [AuthGuard], resolve: {series: SeriesResolver, federaciones: ListadoFederacionesResolver} },
  {path: 'peliculas', component: PeliculasComponent, canActivate: [AuthGuard], resolve: {peliculas: PeliculasResolver, federaciones: ListadoFederacionesResolver}},
  {path: 'mi-usuario/:id_cliente', component: UsuarioComponent, canActivate: [AuthGuard]},
  {path: 'usuario/:id_cliente/finalizar_federacion/:id_plataforma', component: UsuarioFederacionComponent},
  {
    path: 'descripcion/:id_cliente/:id_contenido',
    component: DescripcionComponent,
    resolve: {descripcion: DescripcionContenidoHomeResolverResolver},
  },
  {
    path: 'usuario/federaciones',
    component: FederacionesComponent,
    resolve: {federaciones: ListadoFederacionesResolver},
    canActivate: [AuthGuard]
  }
  // Otras rutas que necesites
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class MainRoutingModule {
}
