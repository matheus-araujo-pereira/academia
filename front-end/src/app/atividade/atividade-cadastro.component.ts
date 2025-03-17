import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { AtividadeService, Atividade } from './atividade.service';
import { Router, ActivatedRoute } from '@angular/router';

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

  constructor(
    private fb: FormBuilder,
    private service: AtividadeService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.atividadeForm = this.fb.group({
      nome: ['', Validators.required],
      descricao: [''],
      horaInicio: ['', Validators.required],
      horaFim: ['', Validators.required],
      diasSemana: ['', Validators.required],
      professor: this.fb.group({
        id: ['', Validators.required],
      }),
      // Para cadastro, o array de clientes inicia vazio
      clientes: [[]],
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const idParam = params.get('id');
      if (idParam) {
        this.isEdit = true;
        this.id = Number(idParam);
        // Para obter a atividade a ser editada, podemos buscar por id;
        // aqui, para simplificar, carregamos todas e filtramos
        this.service.getAll().subscribe({
          next: (activities) => {
            const atividade = activities.find((a) => a.id === this.id);
            if (atividade) {
              this.atividadeForm.patchValue(atividade);
            }
          },
          error: (err) => console.error(err),
        });
      }
    });
  }

  voltar(): void {
    this.router.navigate(['/atividades']);
  }

  salvar(): void {
    if (this.atividadeForm.valid) {
      if (this.isEdit && this.id) {
        this.service.update(this.id, this.atividadeForm.value).subscribe({
          next: () => this.router.navigate(['/atividades']),
          error: (err) => console.error(err),
        });
      } else {
        this.service.create(this.atividadeForm.value).subscribe({
          next: () => this.router.navigate(['/atividades']),
          error: (err) => console.error(err),
        });
      }
    }
  }
}
