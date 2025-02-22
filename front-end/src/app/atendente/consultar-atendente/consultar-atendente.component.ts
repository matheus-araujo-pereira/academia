import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-consultar-atendente',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './consultar-atendente.component.html',
  styleUrls: ['./consultar-atendente.component.css'],
})
export class ConsultarAtendenteComponent {
  atendente: any = null;

  constructor(private router: Router) {}

  consultarAtendente(cpf: string) {
    // Lógica para consultar atendente pelo CPF
    // Exemplo de atendente encontrado
    this.atendente = {
      nome: 'João Silva',
      endereco: 'Rua Exemplo, Número 123, Bairro Exemplo, Cidade-Estado',
      telefone: '(11) 12345-6789',
      cpf: '123.456.789-00',
      rg: '12.345.678-9',
      dataNascimento: '01/01/1980',
      email: 'joao.silva@example.com',
      cep: '12345-678',
    };
  }

  atualizarAtendente() {
    this.router.navigate(['/atualizar-atendente']);
  }

  excluirAtendente() {
    // Lógica para excluir atendente
    this.atendente = null;
  }
}
