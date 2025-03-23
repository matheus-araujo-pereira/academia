import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DadosPessoaisService } from './dados-pessoais.service';
import { AtividadeService, Atividade } from '../atividade/atividade.service';

@Component({
  selector: 'app-dados-pessoais',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dados-pessoais.component.html',
  styleUrls: ['./dados-pessoais.component.css'],
})
export class DadosPessoaisComponent implements OnInit {
  usuario: any;
  atividades: Atividade[] = [];

  constructor(
    private dadosPessoaisService: DadosPessoaisService,
    private atividadeService: AtividadeService
  ) {}

  ngOnInit(): void {
    this.dadosPessoaisService.getDadosPessoais().subscribe({
      next: (data) => (this.usuario = data),
      error: (err) => console.error(err),
    });

    this.atividadeService.getAll().subscribe({
      next: (data) => (this.atividades = data),
      error: (err) => console.error(err),
    });
  }
}
