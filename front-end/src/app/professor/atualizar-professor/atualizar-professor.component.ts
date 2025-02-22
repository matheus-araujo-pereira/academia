import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-atualizar-professor',
  templateUrl: './atualizar-professor.component.html',
  styleUrls: ['./atualizar-professor.component.css'],
})
export class AtualizarProfessorComponent {
  constructor(private router: Router) {}

  atualizarProfessor() {
    // Lógica para atualizar professor
    this.router.navigate(['/professor']);
  }
}
