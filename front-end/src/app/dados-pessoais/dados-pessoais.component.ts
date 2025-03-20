import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-dados-pessoais',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dados-pessoais.component.html',
  styleUrls: ['./dados-pessoais.component.css'],
})
export class DadosPessoaisComponent implements OnInit {
  usuario: any;
  constructor(private http: HttpClient) {}
  ngOnInit(): void {
    this.http.get('/api/dados-pessoais').subscribe({
      next: (data) => (this.usuario = data),
      error: (err) => console.error(err),
    });
  }
}
