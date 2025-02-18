import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-atualizar-professor',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container mt-5">
      <div class="card">
        <div class="card-header">
          <h2>Atualizar Professor</h2>
        </div>
        <div class="card-body">
          <form>
            <!-- Exiba os dados atuais e permita a edição -->
            <div class="mb-3">
              <label for="nomeProfessor" class="form-label">Nome</label>
              <input id="nomeProfessor" name="nomeProfessor" type="text" class="form-control" value="Professor Exemplo">
            </div>
            <!-- ...outros campos... -->
            <button type="submit" class="btn btn-primary">Atualizar</button>
          </form>
        </div>
      </div>
    </div>
  `
})
export class AtualizarProfessorComponent { }
