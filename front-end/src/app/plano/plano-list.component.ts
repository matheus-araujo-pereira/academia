import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlanoService, Plano } from './plano.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-plano-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './plano-list.component.html',
  styleUrls: ['./plano-list.component.css'],
})
export class PlanoListComponent implements OnInit {
  planos: Plano[] = [];
  searchNome = '';

  constructor(private service: PlanoService, private router: Router) {}

  ngOnInit(): void {
    this.loadPlanos();
  }

  loadPlanos(): void {
    this.service.getAll().subscribe({
      next: (data) => {
        this.planos = data;
      },
      error: (err) => console.error(err),
    });
  }

  deletePlano(id: number): void {
    if (confirm('Deseja excluir este plano?')) {
      this.service.delete(id).subscribe({
        next: (msg) => {
          alert(msg);
          this.loadPlanos();
        },
        error: (err) => {
          alert('Erro ao excluir: ' + err.error);
        },
      });
    }
  }

  editPlano(id: number): void {
    this.router.navigate(['/planos/editar', id]);
  }

  cadastrar(): void {
    this.router.navigate(['/planos/cadastro']);
  }

  search(): void {
    this.searchNome = this.searchNome.trim();
    if (this.searchNome) {
      this.service.search(this.searchNome).subscribe({
        next: (data) => {
          this.planos = data;
        },
        error: (err) => console.error(err),
      });
    } else {
      this.loadPlanos();
    }
  }
}
