import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';
import { LeilaoFormComponent } from './components/leilao-form/leilao-form.component';
import { leilaoResolver } from './guards/leilao.resolver';
import { ImovelFormComponent } from './components/imovel-form/imovel-form.component';
import { imovelResolver } from './guards/imovel.resolver';
import { VeiculoFormComponent } from './components/veiculo-form/veiculo-form.component';
import { veiculoResolver } from './guards/veiculo.resolver';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'leilao' },
  { path: 'leilao', loadComponent: () => import('./components/leilao/leilao.component').then(m => m.LeilaoComponent)},
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'leilao/new', component: LeilaoFormComponent, resolve: {leilao: leilaoResolver }},
  { path: 'leilao/edit/:id', loadComponent: () => import('./components/leilao-form/leilao-form.component').then(m => m.LeilaoFormComponent), resolve: {leilao: leilaoResolver}},
  { path: 'leilao/veiculos/:id', loadComponent: () => import('./components/veiculo-list/veiculo-list.component').then(m => m.VeiculoListComponent) },
  { path: 'leilao/veiculos/:id/new', component: VeiculoFormComponent, resolve: {veiculo: veiculoResolver }},
  { path: 'leilao/veiculos/:id/edit/:idV', component: VeiculoFormComponent, resolve: {veiculo: veiculoResolver }},

  { path: 'leilao/imoveis/:id', loadComponent: () => import('./components/imovel-list/imovel-list.component').then(m => m.ImovelListComponent) },
  { path: 'leilao/imoveis/:id/new', component: ImovelFormComponent, resolve: {imovel: imovelResolver }},
  { path: 'leilao/imoveis/:id/edit/:idI', component: ImovelFormComponent, resolve: {imovel: imovelResolver }},


];
