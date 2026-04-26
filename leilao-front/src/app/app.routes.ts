import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';
import { LeilaoFormComponent } from './components/leilao-form/leilao-form.component';
import { leilaoResolver } from './guards/leilao.resolver';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'leilao' },
  { path: 'leilao', loadComponent: () => import('./components/leilao/leilao.component').then(m => m.LeilaoComponent)},
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'leilao/new', component: LeilaoFormComponent, resolve: {leilao: leilaoResolver }},
  { path: 'leilao/edit/:id', loadComponent: () => import('./components/leilao-form/leilao-form.component').then(m => m.LeilaoFormComponent), resolve: {leilao: leilaoResolver}},

];
