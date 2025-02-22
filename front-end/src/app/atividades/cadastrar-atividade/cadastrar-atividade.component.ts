import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cadastrar-atividade',
  templateUrl: './cadastrar-atividade.component.html',
  styleUrls: ['./cadastrar-atividade.component.css'],
})
export class CadastrarAtividadeComponent {
  constructor(private router: Router) {}

  cadastrarAtividade() {
    // Lógica para cadastrar atividade
    this.router.navigate(['/atividades']);
  }
}
