import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cadastrar-cliente',
  templateUrl: './cadastrar-cliente.component.html',
  styleUrls: ['./cadastrar-cliente.component.css'],
})
export class CadastrarClienteComponent {
  constructor(private router: Router) {}

  cadastrarCliente() {
    // Lógica para cadastrar cliente
    this.router.navigate(['/clientes']);
  }
}
