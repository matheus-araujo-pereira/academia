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
      professor: [''], // adaptar se necessário
      // Você pode adicionar também um controle para clientes, se necessário.
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const idParam = params.get('id');
      if (idParam) {
        this.isEdit = true;
        this.id = Number(idParam);
        this.service.getById(this.id).subscribe((data) => {
          // Extraímos o id do professor se existir
          if (data.professor && data.professor.id) {
            data.professor = data.professor.id;
          }
          this.atividadeForm.patchValue(data);
        });
      }
    });
  }

  voltar(): void {
    this.router.navigate(['/atividades']);
  }

  salvar(): void {
    if (this.atividadeForm.valid) {
      const formValue = this.atividadeForm.value;
      // Converter o valor do professor para um objeto com o id
      const atividadeToSend = {
        ...formValue,
        professor: { id: Number(formValue.professor) },
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
