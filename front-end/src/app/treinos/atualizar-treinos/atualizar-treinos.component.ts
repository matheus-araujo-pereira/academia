import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-atualizar-treinos',
  templateUrl: './atualizar-treinos.component.html',
  styleUrls: ['./atualizar-treinos.component.css'],
})
export class AtualizarTreinosComponent {
  constructor(private router: Router) {}

  atualizarTreino() {
    // Lógica para atualizar treino
    this.router.navigate(['/treinos']);
  }
}
