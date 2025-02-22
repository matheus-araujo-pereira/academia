import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-consultar-atividade',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './consultar-atividade.component.html',
  styleUrls: ['./consultar-atividade.component.css'],
})
export class ConsultarAtividadeComponent {
  atividade: any = null;

  constructor(private router: Router) {}

  consultarAtividade(nome: string) {
    // Lógica para consultar atividade pelo nome
    // Exemplo de atividade encontrada
    this.atividade = {
      dataHora: '2022-01-01T10:00',
      nome: 'Aula de Yoga',
      descricao: 'Aula de Yoga para iniciantes',
      professor: 'Maria Silva',
    };
  }

  atualizarAtividade() {
    this.router.navigate(['/atualizar-atividade']);
  }

  excluirAtividade() {
    // Lógica para excluir atividade
    this.atividade = null;
  }
}
