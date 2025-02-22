import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-atualizar-atendente',
  templateUrl: './atualizar-atendente.component.html',
  styleUrls: ['./atualizar-atendente.component.css'],
})
export class AtualizarAtendenteComponent {
  constructor(private router: Router) {}

  atualizarAtendente() {
    // Lógica para atualizar atendente
    this.router.navigate(['/atendentes']);
  }
}
