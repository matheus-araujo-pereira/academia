import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AtendenteService, Atendente } from './atendente.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

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

  loadAtendentes(): void {
    this.service.search(this.searchNome, this.searchCpf).subscribe({
      next: (data) => (this.atendentes = data),
      error: (err) => console.error(err),
    });
  }

  deleteAtendente(id: number): void {
    if (confirm('Deseja excluir esse atendente?')) {
      this.service.delete(id).subscribe({
        next: () => this.loadAtendentes(),
        error: (err) => console.error(err),
      });
    }
  }

  editAtendente(id: number): void {
    this.router.navigate(['/atendentes/editar', id]);
  }

  cadastrar(): void {
    this.router.navigate(['/atendentes/cadastro']);
  }

  search(): void {
    this.loadAtendentes();
  }
}
