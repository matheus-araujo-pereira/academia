import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-consultar-treinos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './consultar-treinos.component.html',
  styleUrls: ['./consultar-treinos.component.css'],
})
export class ConsultarTreinosComponent {
  treino: any = null;

  constructor(private router: Router) {}

  consultarTreino(nomeTreino: string) {
    // Lógica para consultar treino pelo nome
    // Exemplo de treino encontrado
    this.treino = {
      nomeProfessor: 'Carlos Silva',
      nomeTreino: 'Treino A',
      dataCriacao: '01/01/2022',
      exercicios: [
        {
          nome: 'Supino',
          descricao: 'Supino reto',
          carga: '50kg',
          repeticoes: '10',
          series: '3',
        },
        {
          nome: 'Agachamento',
          descricao: 'Agachamento livre',
          carga: '60kg',
          repeticoes: '12',
          series: '4',
        },
      ],
    };
  }

  atualizarTreino() {
    this.router.navigate(['/atualizar-treinos']);
  }

  excluirTreino() {
    // Lógica para excluir treino
    this.treino = null;
  }
}
