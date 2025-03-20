import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TreinoService, Treino } from './treino.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-treino-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './treino-list.component.html',
  styleUrls: ['./treino-list.component.css'],
})
export class TreinoListComponent implements OnInit {
  tipoUsuario = localStorage.getItem('tipoUsuario');
  login = localStorage.getItem('login') || '';
  treinos: Treino[] = [];

  constructor(private treinoService: TreinoService, private router: Router) {}

  ngOnInit(): void {
    this.loadTreinos();
  }

  loadTreinos(): void {
    if (!this.login) return;
    this.treinoService.getAll(this.login).subscribe({
      next: (data) => (this.treinos = data),
      error: (err) => console.error(err),
    });
  }

  novoTreino(): void {
    this.router.navigate(['/treinos/cadastro']);
  }

  editarTreino(id: number): void {
    this.router.navigate(['/treinos/editar', id]);
  }

  excluirTreino(id: number): void {
    if (confirm('Deseja excluir este treino?')) {
      this.treinoService.delete(id, this.login).subscribe({
        next: () => this.loadTreinos(),
        error: (err) => alert('Erro ao excluir: ' + err.error),
      });
    }
  }
}
