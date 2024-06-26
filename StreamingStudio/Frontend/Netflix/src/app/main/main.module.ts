
import { NgModule } from '@angular/core';
import {LoginComponent} from './pages/login/login.component';
import {RegisterComponent} from './pages/register/register.component';
import {CommonModule} from '@angular/common';
import {MainRoutingModule} from './main-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HeaderComponent} from './components/header/header.component';
import {NetflixResourceService} from './api/resources/netflix-resource.service';


// @ts-ignore
@NgModule({
  declarations: [
    HeaderComponent,
    LoginComponent,
    RegisterComponent,
  ],
  imports: [
    CommonModule,
    MainRoutingModule,
    ReactiveFormsModule,
    FormsModule,
  ],
  providers: [
    NetflixResourceService
  ]
})
export class MainModule { }
