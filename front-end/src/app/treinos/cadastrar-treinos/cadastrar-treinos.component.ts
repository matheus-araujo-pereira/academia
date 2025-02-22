import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cadastrar-treinos',
  templateUrl: './cadastrar-treinos.component.html',
  styleUrls: ['./cadastrar-treinos.component.css'],
})
export class CadastrarTreinosComponent {
  constructor(private router: Router) {}

  cadastrarTreino() {
    // Lógica para cadastrar treino
    this.router.navigate(['/treinos']);
  }
}
