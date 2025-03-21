import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormGroup,
  FormBuilder,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { AtividadeService } from './atividade.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ProfessorService } from '../professor/professor.service';

@Component({
  selector: 'app-atividade-cadastro',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './atividade-cadastro.component.html',
  styleUrls: ['./atividade-cadastro.component.css'],
})
export class AtividadeCadastroComponent implements OnInit {
  atividadeForm: FormGroup;
  isEdit = false;
  id: number | null = null;
  professores: any[] = [];

  constructor(
    private fb: FormBuilder,
    private service: AtividadeService,
    private router: Router,
    private route: ActivatedRoute,
    private professorService: ProfessorService
  ) {
    this.atividadeForm = this.fb.group({
      nome: ['', [Validators.required, Validators.pattern(/^[A-Za-z\s]+$/)]],
      descricao: [''],
      horaInicio: ['', Validators.required],
      horaFim: ['', Validators.required],
      diasSemana: ['', Validators.required],
      professor: [null, Validators.required],
    });
  }

  ngOnInit(): void {
    this.fetchProfessores();
    this.route.paramMap.subscribe((params) => {
      const idParam = params.get('id');
      if (idParam) {
        this.isEdit = true;
        this.id = Number(idParam);
        this.service.getById(this.id).subscribe((data) => {
          if (data.professor && data.professor.id) {
            data.professor = data.professor.id;
          }
          this.atividadeForm.patchValue(data);
        });
      }
    });
  }

  fetchProfessores(): void {
    this.professorService.getAll().subscribe({
      next: (data) => (this.professores = data),
      error: (err) => console.error(err),
    });
  }

  voltar(): void {
    this.router.navigate(['/atividades']);
  }

  salvar(): void {
    if (this.atividadeForm.valid) {
      const formValue = this.atividadeForm.value;
      const atividadeToSend = {
        ...formValue,
        professor: { id: formValue.professor },
      };
      if (this.isEdit && this.id) {
        this.service.update(this.id, atividadeToSend).subscribe({
          next: () => {
            alert('Atividade atualizada com sucesso!');
            this.router.navigate(['/atividades']);
          },
          error: (err) => {
            alert('Erro ao atualizar: ' + err?.error?.message);
          },
        });
      } else {
        this.service.create(atividadeToSend).subscribe({
          next: () => {
            alert('Atividade cadastrada com sucesso!');
            this.router.navigate(['/atividades']);
          },
          error: (err) => {
            alert('Erro ao cadastrar: ' + err?.error?.message);
          },
        });
      }
    }
  }
}
