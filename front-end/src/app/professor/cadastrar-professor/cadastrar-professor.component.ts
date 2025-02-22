import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cadastrar-professor',
  templateUrl: './cadastrar-professor.component.html',
  styleUrls: ['./cadastrar-professor.component.css'],
})
export class CadastrarProfessorComponent {
  constructor(private router: Router) {}

  cadastrarProfessor() {
    // Lógica para cadastrar professor
    this.router.navigate(['/professor']);
  }
}
