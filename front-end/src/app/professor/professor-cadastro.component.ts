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
import { ProfessorService, Professor } from './professor.service';

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
      nome: ['', [Validators.required, Validators.pattern(/^[A-Za-z\s]+$/)]],
      login: [
        '',
        [Validators.required, Validators.pattern(/^[a-z]+\.[a-z]+$/)],
      ],
      senha: ['', [Validators.required, Validators.minLength(4)]],
      cpf: ['', [Validators.required, Validators.pattern(/^\d{11}$/)]],
      rg: ['', [Validators.required, Validators.pattern(/^\d{9}$/)]],
      dataNascimento: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telefone: ['', [Validators.required, Validators.pattern(/^\d{11}$/)]],
      endereco: this.fb.group({
        estado: ['', Validators.required],
        cidade: ['', Validators.required],
        bairro: ['', Validators.required],
        logradouro: ['', Validators.required],
        numero: ['', Validators.required],
        cep: ['', [Validators.required, Validators.pattern(/^\d{5}-?\d{3}$/)]],
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
    // remove possible masks
    const clean = (val: any) => (val ? val.replace(/\D/g, '') : '');
    this.professorForm.patchValue({
      cpf: clean(this.professorForm.get('cpf')?.value),
      rg: clean(this.professorForm.get('rg')?.value),
      telefone: clean(this.professorForm.get('telefone')?.value),
      endereco: {
        ...this.professorForm.get('endereco')?.value,
        cep: clean(this.professorForm.get('endereco.cep')?.value),
      },
    });

    if (this.professorForm.valid) {
      const professor = this.professorForm.value as Professor;
      if (this.isEdit && this.id) {
        this.service.update(this.id, professor).subscribe({
          next: () => {
            alert('Professor atualizado com sucesso!');
            this.router.navigate(['/professores']);
          },
          error: (err) => {
            alert('Erro ao atualizar: ' + err?.error?.message);
          },
        });
      } else {
        this.service.create(professor).subscribe({
          next: () => {
            alert('Professor cadastrado com sucesso!');
            this.router.navigate(['/professores']);
          },
          error: (err) => {
            alert('Erro ao cadastrar: ' + err?.error?.message);
          },
        });
      }
    } else {
      alert('Por favor, preencha todos os campos obrigatórios corretamente.');
    }
  }

  validarCPF(): void {
    const cpf = this.professorForm.get('cpf')?.value.replace(/\D/g, '');
    if (cpf.length === 11) {
      this.professorForm.get('cpf')?.setErrors(null);
    }
  }

  validarRG(): void {
    const rg = this.professorForm.get('rg')?.value.replace(/\D/g, '');
    if (rg.length === 9) {
      this.professorForm.get('rg')?.setErrors(null);
    }
  }

  validarTelefone(): void {
    const telefone = this.professorForm
      .get('telefone')
      ?.value.replace(/\D/g, '');
    if (telefone.length === 11) {
      this.professorForm.get('telefone')?.setErrors(null);
    }
  }

  formatarCPF(): void {
    let cpf = this.professorForm.get('cpf')?.value || '';
    cpf = cpf
      .replace(/\D/g, '')
      .replace(/(\d{3})(\d)/, '$1.$2')
      .replace(/(\d{3})(\d)/, '$1.$2')
      .replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    this.professorForm.get('cpf')?.setValue(cpf, { emitEvent: false });
  }

  formatarRG(): void {
    let rg = this.professorForm.get('rg')?.value || '';
    rg = rg
      .replace(/\D/g, '')
      .replace(/(\d{2})(\d)/, '$1.$2')
      .replace(/(\d{3})(\d)/, '$1.$2')
      .replace(/(\d{3})(\d{1})$/, '$1-$2');
    this.professorForm.get('rg')?.setValue(rg, { emitEvent: false });
  }

  formatarTelefone(): void {
    let tel = this.professorForm.get('telefone')?.value || '';
    tel = tel
      .replace(/\D/g, '')
      .replace(/(\d{2})(\d)/, '($1) $2')
      .replace(/(\d{5})(\d{4})$/, '$1-$2');
    this.professorForm.get('telefone')?.setValue(tel, { emitEvent: false });
  }

  formatarCEP(): void {
    let cep = this.professorForm.get('endereco.cep')?.value || '';
    cep = cep.replace(/\D/g, '').replace(/(\d{5})(\d{3})$/, '$1-$2');
    this.professorForm.get('endereco.cep')?.setValue(cep, { emitEvent: false });
  }
}
