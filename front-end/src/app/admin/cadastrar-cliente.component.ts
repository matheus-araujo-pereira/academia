import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-cadastrar-cliente',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container mt-5">
      <div class="card">
        <div class="card-header">
          <h2>Cadastrar Cliente</h2>
        </div>
        <div class="card-body">
          <form>
            <!-- ...campos do formulário... -->
            <div class="mb-3">
              <label for="clientName" class="form-label">Nome</label>
              <input id="clientName" name="clientName" type="text" class="form-control">
            </div>
            <!-- Outros campos podem ser adicionados aqui -->
            <button type="submit" class="btn btn-primary">Cadastrar</button>
          </form>
        </div>
      </div>
    </div>
  `
})
export class CadastrarClienteComponent { }
