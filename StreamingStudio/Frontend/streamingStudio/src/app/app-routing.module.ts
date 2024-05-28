import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {SplashComponent} from './main/pages/splash/splash.component';

const routes: Routes = [
  { path: '', loadChildren: () => import('./main/main.module').then(m => m.MainModule)},
  { path: '**', component: SplashComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
