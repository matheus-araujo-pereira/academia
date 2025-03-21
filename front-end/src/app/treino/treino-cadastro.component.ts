import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { TreinoService, Treino } from './treino.service';
import { ClienteService, Cliente } from '../cliente/cliente.service';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-treino-cadastro',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './treino-cadastro.component.html',
  styleUrls: ['./treino-cadastro.component.css'],
})
export class TreinoCadastroComponent implements OnInit {
  treinoForm: FormGroup;
  isEdit = false;
  id: number | null = null;
  clientes: Cliente[] = [];

  constructor(
    private fb: FormBuilder,
    private service: TreinoService,
    private clienteService: ClienteService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.treinoForm = this.fb.group({
      descricao: ['', Validators.required],
      dataCriacao: ['', Validators.required],
      cliente: ['', Validators.required],
      exercicios: this.fb.array([]),
    });
  }

  ngOnInit(): void {
    this.clienteService.getAll().subscribe((data) => {
      this.clientes = data;
    });

    this.route.paramMap.subscribe((params) => {
      const idParam = params.get('id');
      if (idParam) {
        this.isEdit = true;
        this.id = Number(idParam);
        this.service.getById(this.id).subscribe((data) => {
          this.treinoForm.patchValue({
            descricao: data.descricao,
            dataCriacao: data.dataCriacao,
            cliente: data.cliente?.id,
          });
          const exerciciosFG = data.exercicios.map((ex) =>
            this.fb.group({
              id: [ex.id],
              nome: [ex.nome, Validators.required],
              descricao: [ex.descricao],
              carga: [ex.carga],
              repeticao: [ex.repeticao],
              series: [ex.series],
            })
          );
          this.treinoForm.setControl('exercicios', this.fb.array(exerciciosFG));
        });
      }
    });
  }

  get exercicios(): FormArray {
    return this.treinoForm.get('exercicios') as FormArray;
  }

  addExercicio(): void {
    this.exercicios.push(
      this.fb.group({
        nome: ['', Validators.required],
        descricao: [''],
        carga: [null],
        repeticao: [null],
        series: [null],
      })
    );
  }

  removeExercicio(index: number): void {
    this.exercicios.removeAt(index);
  }

  salvar(): void {
    if (this.treinoForm.valid) {
      const treino: Treino = this.treinoForm.value;
      if (this.isEdit && this.id) {
        this.service.update(this.id, treino).subscribe({
          next: () => {
            alert('Treino atualizado com sucesso!');
            this.router.navigate(['/treinos']);
          },
          error: (err) => alert('Erro ao atualizar: ' + err?.error?.message),
        });
      } else {
        this.service.create(treino).subscribe({
          next: () => {
            alert('Treino cadastrado com sucesso!');
            this.router.navigate(['/treinos']);
          },
          error: (err) => alert('Erro ao cadastrar: ' + err?.error?.message),
        });
      }
    }
  }

  voltar(): void {
    this.router.navigate(['/treinos']);
  }
}
