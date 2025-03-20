import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { TreinoService, Treino } from './treino.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-treino-cadastro',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './treino-cadastro.component.html',
  styleUrls: ['./treino-cadastro.component.css'],
})
export class TreinoCadastroComponent implements OnInit {
  form: FormGroup;
  isEdit = false;
  id?: number;
  login = localStorage.getItem('login') || '';

  constructor(
    private fb: FormBuilder,
    private treinoService: TreinoService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      descricao: ['', Validators.required],
      dataCriacao: ['', Validators.required],
      cliente: [null],
      professor: [null],
    });
  }

  ngOnInit(): void {
    if (!this.login) {
      alert('Usuário não autenticado');
      this.router.navigate(['/login']);
      return;
    }
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEdit = true;
      this.id = +idParam;
      this.treinoService.getAll(this.login).subscribe((lista) => {
        const treino = lista.find((t) => t.id === this.id);
        if (treino) this.form.patchValue(treino);
      });
    }
  }

  salvar(): void {
    if (this.form.invalid) return;
    const treino: Treino = this.form.value;
    if (this.isEdit && this.id) {
      this.treinoService.update(this.id, this.login, treino).subscribe({
        next: () => {
          alert('Treino atualizado com sucesso');
          this.router.navigate(['/treinos']);
        },
        error: (err) => alert('Erro ao atualizar: ' + err.error),
      });
    } else {
      this.treinoService.create(this.login, treino).subscribe({
        next: () => {
          alert('Treino criado com sucesso');
          this.router.navigate(['/treinos']);
        },
        error: (err) => alert('Erro ao criar: ' + err.error),
      });
    }
  }

  voltar(): void {
    this.router.navigate(['/treinos']);
  }
}
