import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AtendenteService, Atendente } from './atendente.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

/**
 * Componente responsável por exibir a lista de atendentes,
 * incluindo busca, edição e exclusão.
 */
@Component({
  selector: 'app-atendente-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './atendente-list.component.html',
  styleUrls: ['./atendente-list.component.css'],
})
export class AtendenteListComponent implements OnInit {
  atendentes: Atendente[] = [];
  searchNome = '';
  searchCpf = '';

  constructor(private service: AtendenteService, private router: Router) {}

  ngOnInit(): void {
    this.loadAtendentes();
  }

  /**
   * Carrega a lista de atendentes do serviço, filtrando se necessário.
   */
  loadAtendentes(): void {
    this.service.search(this.searchNome, this.searchCpf).subscribe({
      next: (data) => {
        console.log('Retornou:', data);
        this.atendentes = data.sort((a, b) => a.nome.localeCompare(b.nome));
      },
      error: (err) => console.error(err),
    });
  }

  /**
   * Exclui um atendente pelo ID, solicitando confirmação antes.
   */
  deleteAtendente(id: number): void {
    if (confirm('Deseja excluir esse atendente?')) {
      this.service.delete(id).subscribe({
        next: () => {
          alert('Atendente excluído com sucesso!');
          this.loadAtendentes();
        },
        error: (err) => {
          alert('Erro ao excluir: ' + err?.error?.message);
        },
      });
    }
  }

  /**
   * Redireciona para tela de edição de um atendente específico.
   */
  editAtendente(id: number): void {
    this.router.navigate(['/atendentes/editar', id]);
  }

  /**
   * Redireciona para a tela de cadastro de um novo atendente.
   */
  cadastrar(): void {
    this.router.navigate(['/atendentes/cadastro']);
  }

  /**
   * Realiza a busca de acordo com os filtros searchNome e searchCpf.
   */
  search(): void {
    this.searchNome = this.searchNome.trim();
    this.searchCpf = this.searchCpf.trim();
    console.log('Buscando com:', this.searchNome, this.searchCpf);
    this.loadAtendentes();
  }
}
