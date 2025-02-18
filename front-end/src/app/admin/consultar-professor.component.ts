import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-consultar-professor',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container mt-5">
      <h2>Consultar Professor</h2>
      <!-- Campo de busca por nome -->
      <div class="mb-3">
        <input type="text" class="form-control" placeholder="Digite o nome do professor">
      </div>
      <!-- Lista de professores (exemplo estático) -->
      <ul class="list-group">
        <li class="list-group-item">Professor A</li>
        <li class="list-group-item">Professor B</li>
        <!-- ...lista dinâmica de professores... -->
      </ul>
    </div>
  `
})
export class ConsultarProfessorComponent { }
