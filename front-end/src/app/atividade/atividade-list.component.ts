import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AtividadeService, Atividade } from './atividade.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-atividade-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './atividade-list.component.html',
  styleUrls: ['./atividade-list.component.css'],
})
export class AtividadeListComponent implements OnInit {
  atividades: Atividade[] = [];
  searchNome = '';

  constructor(private service: AtividadeService, private router: Router) {}

  ngOnInit(): void {
    this.loadAtividades();
  }

  loadAtividades(): void {
    this.service.getAll().subscribe({
      next: (data) => {
        this.atividades = data;
      },
      error: (err) => console.error(err),
    });
  }

  deleteAtividade(id: number): void {
    if (confirm('Deseja excluir essa atividade?')) {
      this.service.delete(id).subscribe({
        next: () => {
          alert('Atividade excluída com sucesso!');
          this.loadAtividades();
        },
        error: (err) => {
          alert('Erro ao excluir: ' + err?.error?.message);
        },
      });
    }
  }

  editAtividade(id: number): void {
    this.router.navigate(['/atividades/editar', id]);
  }

  cadastrar(): void {
    this.router.navigate(['/atividades/cadastro']);
  }

  search(): void {
    this.searchNome = this.searchNome.trim();
    if (this.searchNome) {
      this.atividades = this.atividades.filter((a) =>
        a.nome.toLowerCase().includes(this.searchNome.toLowerCase())
      );
    } else {
      this.loadAtividades();
    }
  }
}
