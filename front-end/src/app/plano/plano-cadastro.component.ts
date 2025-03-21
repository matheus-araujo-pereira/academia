import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormGroup,
  FormBuilder,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { PlanoService, Plano } from './plano.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-plano-cadastro',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './plano-cadastro.component.html',
  styleUrls: ['./plano-cadastro.component.css'],
})
export class PlanoCadastroComponent implements OnInit {
  planoForm: FormGroup;
  isEdit = false;
  id: number | null = null;

  constructor(
    private fb: FormBuilder,
    private service: PlanoService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.planoForm = this.fb.group({
      nome: [
        '',
        [
          Validators.required,
          Validators.pattern('^[a-zA-Z\\s]+$'),
          Validators.maxLength(50),
        ],
      ],
      valor: ['', [Validators.required, Validators.min(0.01)]],
      descricao: ['', Validators.maxLength(255)],
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const idParam = params.get('id');
      if (idParam) {
        this.isEdit = true;
        this.id = Number(idParam);
        this.service.getById(this.id).subscribe((data: Plano) => {
          this.planoForm.patchValue(data);
        });
      }
    });
  }

  voltar(): void {
    this.router.navigate(['/planos']);
  }

  salvar(): void {
    if (this.planoForm.valid) {
      if (this.isEdit && this.id) {
        this.service.update(this.id, this.planoForm.value).subscribe({
          next: (msg) => {
            alert(msg);
            this.router.navigate(['/planos']);
          },
          error: (err) => {
            alert('Erro ao atualizar: ' + err.error);
          },
        });
      } else {
        this.service.create(this.planoForm.value).subscribe({
          next: (msg) => {
            alert(msg);
            this.router.navigate(['/planos']);
          },
          error: (err) => {
            alert('Erro ao cadastrar: ' + err.error);
          },
        });
      }
    }
  }
}
