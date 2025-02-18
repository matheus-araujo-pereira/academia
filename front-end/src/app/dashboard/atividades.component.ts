import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-atividades',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container mt-5">
      <h2>Atividades</h2>
      <!-- ...conteúdo das atividades... -->
      <p>Listagem e detalhes das atividades.</p>
    </div>
  `
})
export class AtividadesComponent { }
