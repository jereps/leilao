import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';
import { LeilaoComponent } from './components/leilao/leilao.component';
import { LeilaoFormComponent } from './components/leilao-form/leilao-form.component';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'leilao' },
  { path: 'leilao', loadComponent: () => import('./components/leilao/leilao.component').then(m => m.LeilaoComponent) },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'leilao/new', component: LeilaoFormComponent }
];
