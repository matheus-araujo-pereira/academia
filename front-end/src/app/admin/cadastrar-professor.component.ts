import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-cadastrar-professor',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container mt-5">
      <div class="card">
        <div class="card-header">
          <h2>Cadastrar Professor</h2>
        </div>
        <div class="card-body">
          <form>
            <!-- Exemplo de campos para cadastro -->
            <div class="mb-3">
              <label for="nomeProfessor" class="form-label">Nome</label>
              <input id="nomeProfessor" name="nomeProfessor" type="text" class="form-control">
            </div>
            <div class="mb-3">
              <label for="emailProfessor" class="form-label">E-mail</label>
              <input id="emailProfessor" name="emailProfessor" type="email" class="form-control">
            </div>
            <!-- ...outros campos necessários... -->
            <button type="submit" class="btn btn-primary">Cadastrar</button>
          </form>
        </div>
      </div>
    </div>
  `
})
export class CadastrarProfessorComponent { }
