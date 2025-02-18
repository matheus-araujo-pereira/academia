import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EntrarComponent } from './auth/entrar.component';
import { MenuPrincipalComponent } from './dashboard/menu-principal.component';
import { DadosPessoaisComponent } from './dashboard/dados-pessoais.component';
import { AtividadesComponent } from './dashboard/atividades.component';
import { TreinosComponent } from './dashboard/treinos.component';
import { CadastrarClienteComponent } from './admin/cadastrar-cliente.component';
import { CadastrarFuncionarioComponent } from './admin/cadastrar-funcionario.component';
import { CadastrarProfessorComponent } from './admin/cadastrar-professor.component';
import { ConsultarProfessorComponent } from './admin/consultar-professor.component';
import { AtualizarProfessorComponent } from './admin/atualizar-professor.component';
import { ExcluirProfessorComponent } from './admin/excluir-professor.component';

export const routes: Routes = [
  { path: '', redirectTo: 'entrar', pathMatch: 'full' },
  { path: 'entrar', component: EntrarComponent },
  {
    path: 'dashboard',
    component: MenuPrincipalComponent,
    children: [
      { path: 'dados-pessoais', component: DadosPessoaisComponent },
      { path: 'atividades', component: AtividadesComponent },
      { path: 'treinos', component: TreinosComponent },
      { path: 'cadastrar-cliente', component: CadastrarClienteComponent },
      { path: 'cadastrar-funcionario', component: CadastrarFuncionarioComponent },
      { path: 'cadastrar-professor', component: CadastrarProfessorComponent },
      { path: 'consultar-professor', component: ConsultarProfessorComponent },
      { path: 'atualizar-professor', component: AtualizarProfessorComponent },
      { path: 'excluir-professor', component: ExcluirProfessorComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
