import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { UserService } from '../shared/user.service';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css'],
})
export class MenuComponent {
  constructor(public userService: UserService) { }
  // Lógica para exibir menus conforme o perfil do usuário
  // Exemplo: role pode ser 'Administrador', 'Atendente', 'Professor' ou 'Cliente'

  // Pode-se adicionar métodos que determinem quais links exibir
}
