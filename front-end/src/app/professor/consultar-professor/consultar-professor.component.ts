import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-consultar-professor',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './consultar-professor.component.html',
  styleUrls: ['./consultar-professor.component.css'],
})
export class ConsultarProfessorComponent {
  professor: any = null;

  constructor(private router: Router) {}

  consultarProfessor(cpf: string) {
    // Lógica para consultar professor pelo CPF
    // Exemplo de professor encontrado
    this.professor = {
      nome: 'Matheus Araujo Pereira',
      endereco:
        'Rua Francisco Rollemberg Ramos, Número 54, Conjunto Orlando Dantas, Bairro São Conrado, Aracaju-SE',
      telefone: '(79) 99998-4882',
      cpf: '063.189.135-80',
      rg: '3.579-876-9',
      dataNascimento: '19/06/2001',
      email: 'matheusaraujopereira@academico.ufs.br',
      cep: '49042-590',
    };
  }

  atualizarProfessor() {
    this.router.navigate(['/atualizar-professor']);
  }

  excluirProfessor() {
    // Lógica para excluir professor
    this.professor = null;
  }
}
