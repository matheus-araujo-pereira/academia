/**
 * Componente para exibir a lista de professores.
 * Permite busca, exclusão e navegação para edição/cadastro de professores.
 */
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Professor, ProfessorService } from './professor.service';

@Component({
  selector: 'app-professor-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './professor-list.component.html',
  styleUrls: ['./professor-list.component.css'],
})
export class ProfessorListComponent implements OnInit {
  professors: Professor[] = [];
  searchNome = '';
  searchCpf = '';

  constructor(private service: ProfessorService, private router: Router) {}

  /**
   * Inicializa o componente e carrega a lista de professores.
   */
  ngOnInit(): void {
    this.loadProfessores();
  }

  /**
   * Carrega os professores a partir do serviço, aplicando filtros.
   */
  loadProfessores(): void {
    this.service
      .search(this.searchNome.trim(), this.searchCpf.trim())
      .subscribe({
        next: (data) => {
          this.professors = data;
        },
        error: (err) => console.error(err),
      });
  }

  /**
   * Exclui o professor com o ID informado.
   */
  deleteProfessor(id: number): void {
    if (confirm('Deseja excluir este professor?')) {
      this.service.delete(id).subscribe({
        next: () => {
          alert('Professor excluído com sucesso!');
          this.loadProfessores();
        },
        error: (err) => {
          alert('Erro ao excluir: ' + err?.error?.message);
        },
      });
    }
  }

  /**
   * Redireciona para o formulário de edição do professor.
   */
  editProfessor(id: number): void {
    this.router.navigate(['/professores/editar', id]);
  }

  /**
   * Redireciona para o formulário de cadastro de um novo professor.
   */
  cadastrar(): void {
    this.router.navigate(['/professores/cadastro']);
  }

  /**
   * Aciona a busca dos professores com base nos filtros.
   */
  search(): void {
    this.loadProfessores();
  }
}
