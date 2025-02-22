import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-atualizar-cliente',
  templateUrl: './atualizar-cliente.component.html',
  styleUrls: ['./atualizar-cliente.component.css'],
})
export class AtualizarClienteComponent {
  constructor(private router: Router) {}

  atualizarCliente() {
    // Lógica para atualizar cliente
    this.router.navigate(['/clientes']);
  }
}
