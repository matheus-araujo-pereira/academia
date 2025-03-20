import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Treino, TreinoService } from './treino.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-treino-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './treino-list.component.html',
  styleUrls: ['./treino-list.component.css'],
})
export class TreinoListComponent implements OnInit {
  treinos: Treino[] = [];
  searchDescricao = '';

  constructor(private service: TreinoService, private router: Router) {}

  ngOnInit(): void {
    this.loadTreinos();
  }

  loadTreinos(): void {
    const descricao = this.searchDescricao.trim();
    if (descricao) {
      this.service.search(descricao).subscribe({
        next: (data) => (this.treinos = data),
        error: (err) => console.error(err),
      });
    } else {
      this.service.getAll().subscribe({
        next: (data) => (this.treinos = data),
        error: (err) => console.error(err),
      });
    }
  }

  deleteTreino(id?: number): void {
    if (id && confirm('Deseja excluir este treino?')) {
      this.service.delete(id).subscribe({
        next: () => {
          alert('Treino excluído com sucesso!');
          this.loadTreinos();
        },
        error: (err) => alert('Erro ao excluir: ' + err?.error?.message),
      });
    }
  }

  editTreino(id?: number): void {
    if (id) {
      this.router.navigate(['/treinos/editar', id]);
    }
  }

  cadastrar(): void {
    this.router.navigate(['/treinos/cadastro']);
  }

  search(): void {
    this.loadTreinos();
  }
}
