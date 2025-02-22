import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-consultar-cliente',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './consultar-cliente.component.html',
  styleUrls: ['./consultar-cliente.component.css'],
})
export class ConsultarClienteComponent {
  cliente: any = null;

  constructor(private router: Router) {}

  consultarCliente(cpf: string) {
    // Lógica para consultar cliente pelo CPF
    // Exemplo de cliente encontrado
    this.cliente = {
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

  atualizarCliente() {
    this.router.navigate(['/atualizar-cliente']);
  }

  excluirCliente() {
    // Lógica para excluir cliente
    this.cliente = null;
  }
}
