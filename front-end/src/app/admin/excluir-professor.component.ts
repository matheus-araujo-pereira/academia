import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-excluir-professor',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container mt-5">
      <h2>Excluir Professor</h2>
      <!-- Exemplo de busca e exclusão -->
      <div class="mb-3">
        <input type="text" class="form-control" placeholder="Digite o nome do professor para excluir">
      </div>
      <button class="btn btn-danger">Excluir</button>
    </div>
  `
})
export class ExcluirProfessorComponent { }
