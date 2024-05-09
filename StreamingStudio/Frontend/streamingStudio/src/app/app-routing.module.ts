import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './main/pages/home/home.component';
import {AppComponent} from './app.component';
import {LoginComponent} from './main/pages/login/login.component';
import {RegisterComponent} from './main/pages/register/register.component';
import {AuthGuard} from './AuthGuard';

const routes: Routes = [
  { path: '', redirectTo: '/login.ts', pathMatch: 'full' }, // Ruta por defecto a tu formulario de inicio de sesión
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'register', component: RegisterComponent },
  // Otras rutas que necesites
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
