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

const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch: "full"}, // Ruta por defecto a tu formulario de inicio de sesión
  {path: 'login', component: LoginComponent},
  {path: 'home', component: HomeComponent, canActivate: [AuthGuard], resolve: {home: HomeResolver}},
  {path: 'register', component: RegisterComponent},
  {path: 'contenido', component: ContenidoComponent, canActivate: [AuthGuard]},
  {
    path: 'descripcion/:id_cliente/:id_contenido',
    component: DescripcionComponent,
    resolve: {descripcion: DescripcionContenidoHomeResolverResolver},
    canActivate: [AuthGuard]
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
