import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClienteService, Cliente } from './cliente.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-cliente-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cliente-list.component.html',
  styleUrls: ['./cliente-list.component.css'],
})
export class ClienteListComponent implements OnInit {
  clientes: Cliente[] = [];
  searchNome = '';
  searchCpf = '';

  constructor(private service: ClienteService, private router: Router) {}

  ngOnInit(): void {
    this.loadClientes();
  }

  loadClientes(): void {
    this.service
      .search(this.searchNome.trim(), this.searchCpf.trim())
      .subscribe({
        next: (data) => (this.clientes = data),
        error: (err) => console.error(err),
      });
  }

  cadastrar(): void {
    this.router.navigate(['/clientes/cadastro']);
  }

  editCliente(id: number): void {
    this.router.navigate(['/clientes/editar', id]);
  }

  deleteCliente(id: number): void {
    if (confirm('Deseja excluir este cliente?')) {
      this.service.delete(id).subscribe({
        next: () => {
          alert('Cliente excluído com sucesso!');
          this.loadClientes();
        },
        error: (err) => alert('Erro ao excluir: ' + err.error),
      });
    }
  }

  search(): void {
    this.loadClientes();
  }
}
