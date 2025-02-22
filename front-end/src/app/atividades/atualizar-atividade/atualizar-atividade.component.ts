import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-atualizar-atividade',
  templateUrl: './atualizar-atividade.component.html',
  styleUrls: ['./atualizar-atividade.component.css'],
})
export class AtualizarAtividadeComponent {
  constructor(private router: Router) {}

  atualizarAtividade() {
    // Lógica para atualizar atividade
    this.router.navigate(['/atividades']);
  }
}
