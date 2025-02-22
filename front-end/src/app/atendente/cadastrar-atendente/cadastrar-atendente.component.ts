import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cadastrar-atendente',
  templateUrl: './cadastrar-atendente.component.html',
  styleUrls: ['./cadastrar-atendente.component.css'],
})
export class CadastrarAtendenteComponent {
  constructor(private router: Router) {}

  cadastrarAtendente() {
    // Lógica para cadastrar atendente
    this.router.navigate(['/atendente']);
  }
}
