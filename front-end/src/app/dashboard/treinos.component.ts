import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-treinos',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container mt-5">
      <h2>Treinos</h2>
      <!-- ...conteúdo dos treinos... -->
      <p>Informações e planos de treino.</p>
    </div>
  `
})
export class TreinosComponent { }
