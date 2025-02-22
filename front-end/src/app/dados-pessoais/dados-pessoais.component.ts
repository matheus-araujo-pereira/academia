import { Component } from '@angular/core';

@Component({
  selector: 'app-dados-pessoais',
  templateUrl: './dados-pessoais.component.html',
  styleUrls: ['./dados-pessoais.component.css'],
})
export class DadosPessoaisComponent {
  nome = 'Matheus Araujo Pereira';
  plano = 'Premium';
  endereco =
    'Rua Francisco Rollemberg Ramos, Número 54, Conjunto Orlando Dantas, Bairro São Conrado, Aracaju-SE';
  telefone = '(79) 99998-4882';
  cpf = '063.189.135-80';
  rg = '3.579-876-9';
  dataNascimento = '19/06/2001';
  email = 'matheusaraujopereira@academico.ufs.br';
  cep = '49042-590';
}
