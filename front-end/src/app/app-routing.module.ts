import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { MenuComponent } from './menu/menu.component';
import { AtendenteCadastroComponent } from './atendente/atendente-cadastro.component';
import { AtendenteListComponent } from './atendente/atendente-list.component';

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
  { path: '', redirectTo: '/login', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
