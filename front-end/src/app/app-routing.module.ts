import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { MenuComponent } from './menu/menu.component';
import { AtendenteListComponent } from './atendente/atendente-list.component';
import { AtendenteCadastroComponent } from './atendente/atendente-cadastro.component';
import { AtividadeListComponent } from './atividade/atividade-list.component';
import { AtividadeCadastroComponent } from './atividade/atividade-cadastro.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'menu', component: MenuComponent },
  {
    path: 'atendentes',
    children: [
      { path: '', component: AtendenteListComponent },
      { path: 'cadastro', component: AtendenteCadastroComponent },
      { path: 'editar/:id', component: AtendenteCadastroComponent },
    ],
  },
  {
    path: 'atividades',
    children: [
      { path: '', component: AtividadeListComponent },
      { path: 'cadastro', component: AtividadeCadastroComponent },
      { path: 'editar/:id', component: AtividadeCadastroComponent },
    ],
  },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
