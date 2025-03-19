/**
 * Componente para cadastro e edição de professores.
 * Gerencia o formulário de cadastro e interage com o ProfessorService.
 */
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormGroup,
  FormBuilder,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ProfessorService } from './professor.service';

@Component({
  selector: 'app-professor-cadastro',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './professor-cadastro.component.html',
  styleUrls: ['./professor-cadastro.component.css'],
})
export class ProfessorCadastroComponent implements OnInit {
  professorForm: FormGroup;
  isEdit = false;
  id: number | null = null;

  constructor(
    private fb: FormBuilder,
    private service: ProfessorService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.professorForm = this.fb.group({
      nome: ['', Validators.required],
      login: ['', Validators.required],
      senha: ['', Validators.required],
      cpf: ['', Validators.required],
      rg: [''],
      dataNascimento: [''],
      email: [''],
      telefone: [''],
      endereco: this.fb.group({
        estado: ['', Validators.required],
        cidade: ['', Validators.required],
        bairro: ['', Validators.required],
        logradouro: ['', Validators.required],
        numero: ['', Validators.required],
        cep: [
          '',
          [Validators.required, Validators.pattern('^[0-9]{5}-?[0-9]{3}$')],
        ],
      }),
    });
  }

  /**
   * Inicializa o componente e carrega os dados para edição, se aplicável.
   */
  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const idParam = params.get('id');
      if (idParam) {
        this.isEdit = true;
        this.id = Number(idParam);
        this.service.getById(this.id).subscribe((data) => {
          this.professorForm.patchValue(data);
        });
      }
    });
  }

  /**
   * Navega de volta para a lista de professores.
   */
  voltar(): void {
    this.router.navigate(['/professores']);
  }

  /**
   * Salva ou atualiza um professor com base no estado do formulário.
   */
  salvar(): void {
    if (this.professorForm.valid) {
      if (this.isEdit && this.id) {
        this.service.update(this.id, this.professorForm.value).subscribe({
          next: () => {
            alert('Professor atualizado com sucesso!');
            this.router.navigate(['/professores']);
          },
          error: (err) => {
            alert('Erro ao atualizar: ' + err?.error?.message);
          },
        });
      } else {
        this.service.create(this.professorForm.value).subscribe({
          next: () => {
            alert('Professor cadastrado com sucesso!');
            this.router.navigate(['/professores']);
          },
          error: (err) => {
            alert('Erro ao cadastrar: ' + err?.error?.message);
          },
        });
      }
    }
  }
}
