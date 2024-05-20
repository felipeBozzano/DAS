import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LoginComponent} from './pages/login/login.component';
import {RegisterComponent} from './pages/register/register.component';


const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full'}, // Ruta por defecto a tu formulario de inicio de sesión
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  // Otras rutas que necesites
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class MainRoutingModule {
}
