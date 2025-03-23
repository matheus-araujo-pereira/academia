import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ExercicioService } from './exercicio.service';

@Component({
  selector: 'app-exercicio-cadastro',
  templateUrl: './exercicio-cadastro.component.html',
  styleUrls: ['./exercicio-cadastro.component.css'],
  imports: [FormsModule, ReactiveFormsModule], // Adicione esta linha
})
export class ExercicioCadastroComponent implements OnInit {
  exercicioForm: FormGroup;
  isEdit = false;
  id: number | null = null;

  constructor(
    private fb: FormBuilder,
    private service: ExercicioService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.exercicioForm = this.fb.group({
      nome: ['', Validators.required],
      descricao: [''],
      carga: [null],
      repeticao: [null],
      series: [null],
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const idParam = params.get('id');
      if (idParam) {
        this.isEdit = true;
        this.id = Number(idParam);
        this.service.getById(this.id).subscribe((data) => {
          this.exercicioForm.patchValue(data);
        });
      }
    });
  }

  salvar(): void {
    if (this.exercicioForm.valid) {
      if (this.isEdit && this.id) {
        this.service.update(this.id, this.exercicioForm.value).subscribe({
          next: () => {
            alert('Exercício atualizado com sucesso!');
            this.router.navigate(['/exercicios']);
          },
          error: (err) => alert('Erro ao atualizar: ' + err?.error?.message),
        });
      } else {
        this.service.create(this.exercicioForm.value).subscribe({
          next: () => {
            alert('Exercício cadastrado com sucesso!');
            this.router.navigate(['/exercicios']);
          },
          error: (err) => alert('Erro ao cadastrar: ' + err?.error?.message),
        });
      }
    }
  }

  voltar(): void {
    this.router.navigate(['/exercicios']);
  }
}
