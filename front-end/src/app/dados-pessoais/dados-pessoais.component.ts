import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DadosPessoaisService } from './dados-pessoais.service';

@Component({
  selector: 'app-dados-pessoais',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dados-pessoais.component.html',
  styleUrls: ['./dados-pessoais.component.css'],
})
export class DadosPessoaisComponent implements OnInit {
  usuario: any;

  constructor(private dadosPessoaisService: DadosPessoaisService) {}

  ngOnInit(): void {
    this.dadosPessoaisService.getDadosPessoais().subscribe({
      next: (data) => (this.usuario = data),
      error: (err) => console.error(err),
    });
  }
}
