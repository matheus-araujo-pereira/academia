import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dados-pessoais',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container mt-5">
      <div class="card">
        <div class="card-header">
          <h2>Dados Pessoais</h2>
        </div>
        <div class="card-body">
          <!-- ...conteúdo dos dados pessoais... -->
          <p>Exibir informações do usuário.</p>
        </div>
      </div>
    </div>
  `
})
export class DadosPessoaisComponent { }
