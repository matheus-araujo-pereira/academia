import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AtividadeService, Atividade } from './atividade.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-atividade-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './atividade-list.component.html',
  styleUrls: ['./atividade-list.component.css'],
})
export class AtividadeListComponent implements OnInit {
  atividades: Atividade[] = [];
  // Exemplo: ID do cliente logado; na prática, obtenha isto por meio de serviço de autenticação
  currentClientId: number = 1;

  constructor(private service: AtividadeService, private router: Router) {}

  ngOnInit(): void {
    this.loadAtividades();
  }

  loadAtividades(): void {
    this.service.getAll().subscribe({
      next: (data) => {
        // Exibe todas as atividades sem filtrar pelo usuário logado
        this.atividades = data;
      },
      error: (err) => console.error(err),
    });
  }

  inscrever(atividadeId: number): void {
    this.service.inscrever(this.currentClientId, atividadeId).subscribe({
      next: () => alert('Inscrição realizada com sucesso'),
      error: (err) => console.error(err),
    });
  }

  cadastrar(): void {
    this.router.navigate(['/atividades/cadastro']);
  }
}
