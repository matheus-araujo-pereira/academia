import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Exercicio, ExercicioService } from './exercicio.service';

@Component({
  selector: 'app-exercicio-list',
  templateUrl: './exercicio-list.component.html',
  styleUrls: ['./exercicio-list.component.css'],
})
export class ExercicioListComponent implements OnInit {
  exercicios: Exercicio[] = [];
  searchNome = '';

  constructor(private service: ExercicioService, private router: Router) {}

  ngOnInit(): void {
    this.loadExercicios();
  }

  loadExercicios(): void {
    const nome = this.searchNome.trim();
    if (nome) {
      this.service.search(nome).subscribe({
        next: (data) => (this.exercicios = data),
        error: (err) => console.error(err),
      });
    } else {
      this.service.getAll().subscribe({
        next: (data) => (this.exercicios = data),
        error: (err) => console.error(err),
      });
    }
  }

  deleteExercicio(id?: number): void {
    if (id && confirm('Deseja excluir este exercício?')) {
      this.service.delete(id).subscribe({
        next: () => {
          alert('Exercício excluído com sucesso!');
          this.loadExercicios();
        },
        error: (err) => alert('Erro ao excluir: ' + err?.error?.message),
      });
    }
  }

  editExercicio(id?: number): void {
    if (id) {
      this.router.navigate(['/exercicios/editar', id]);
    }
  }

  cadastrar(): void {
    this.router.navigate(['/exercicios/cadastro']);
  }

  search(): void {
    this.loadExercicios();
  }
}
