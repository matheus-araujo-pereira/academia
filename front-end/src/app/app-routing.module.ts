/**
 * Módulo de roteamento que define as rotas da aplicação.
 * Inclui caminhos para login, menu, e funcionalidades de atendentes, professores e atividades.
 */
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { MenuComponent } from './menu/menu.component';
import { AtendenteListComponent } from './atendente/atendente-list.component';
import { AtendenteCadastroComponent } from './atendente/atendente-cadastro.component';
import { AtividadeListComponent } from './atividade/atividade-list.component';
import { AtividadeCadastroComponent } from './atividade/atividade-cadastro.component';
import { ProfessorCadastroComponent } from './professor/professor-cadastro.component';
import { ProfessorListComponent } from './professor/professor-list.component';
import { PlanoCadastroComponent } from './plano/plano-cadastro.component';
import { PlanoListComponent } from './plano/plano-list.component';

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
    path: 'professores',
    children: [
      { path: '', component: ProfessorListComponent },
      { path: 'cadastro', component: ProfessorCadastroComponent },
      { path: 'editar/:id', component: ProfessorCadastroComponent },
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
  {
    path: 'planos',
    children: [
      { path: '', component: PlanoListComponent },
      { path: 'cadastro', component: PlanoCadastroComponent },
      { path: 'editar/:id', component: PlanoCadastroComponent },
    ],
  },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
