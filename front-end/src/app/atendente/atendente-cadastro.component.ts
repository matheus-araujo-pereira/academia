import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormGroup,
  FormBuilder,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { AtendenteService } from './atendente.service';
import { Router, ActivatedRoute } from '@angular/router';

/**
 * Componente de cadastro/edição de Atendente. Gerencia o formulário
 * e a interação com o serviço de Atendente.
 */
@Component({
  selector: 'app-atendente-cadastro',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './atendente-cadastro.component.html',
  styleUrls: ['./atendente-cadastro.component.css'],
})
export class AtendenteCadastroComponent implements OnInit {
  atendenteForm: FormGroup;
  isEdit = false;
  id: number | null = null;

  constructor(
    private fb: FormBuilder,
    private service: AtendenteService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.atendenteForm = this.fb.group({
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
   * Inicializa o formulário e verifica se é edição.
   */
  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const idParam = params.get('id');
      if (idParam) {
        this.isEdit = true;
        this.id = Number(idParam);
        this.service.getById(this.id).subscribe((data) => {
          // Certifique-se de que o objeto retornado possui a propriedade "endereco"
          this.atendenteForm.patchValue(data);
        });
      }
    });
  }

  /**
   * Volta para a lista de atendentes.
   */
  voltar(): void {
    this.router.navigate(['/atendentes']);
  }

  /**
   * Cria ou atualiza o atendente, exibindo alertas de sucesso ou erro.
   */
  salvar(): void {
    // Remover máscaras antes de validar
    const cpf = this.atendenteForm.get('cpf')?.value.replace(/\D/g, '');
    const rg = this.atendenteForm.get('rg')?.value.replace(/\D/g, '');
    const telefone = this.atendenteForm
      .get('telefone')
      ?.value.replace(/\D/g, '');
    const cep = this.atendenteForm
      .get('endereco.cep')
      ?.value.replace(/\D/g, '');

    this.atendenteForm.patchValue({
      cpf: cpf,
      rg: rg,
      telefone: telefone,
      endereco: {
        ...this.atendenteForm.get('endereco')?.value,
        cep: cep,
      },
    });

    if (this.atendenteForm.valid) {
      const atendente = this.atendenteForm.value;

      console.log('Atendente a ser salvo:', atendente);

      if (this.isEdit && this.id) {
        this.service.update(this.id, atendente).subscribe({
          next: () => {
            alert('Atendente atualizado com sucesso!');
            this.router.navigate(['/atendentes']);
          },
          error: (err) => {
            console.error('Erro ao atualizar:', err);
            alert('Erro ao atualizar: ' + err?.error?.message);
          },
        });
      } else {
        this.service.create(atendente).subscribe({
          next: () => {
            alert('Atendente cadastrado com sucesso!');
            this.router.navigate(['/atendentes']);
          },
          error: (err) => {
            console.error('Erro ao cadastrar:', err);
            alert('Erro ao cadastrar: ' + err?.error?.message);
          },
        });
      }
    } else {
      console.warn('Formulário inválido:', this.atendenteForm);
      alert('Por favor, preencha todos os campos obrigatórios corretamente.');
    }
  }

  validarCPF(): void {
    const cpf = this.atendenteForm.get('cpf')?.value.replace(/\D/g, '');
    if (cpf.length === 11) {
      this.atendenteForm.get('cpf')?.setErrors(null);
    }
  }

  validarRG(): void {
    const rg = this.atendenteForm.get('rg')?.value.replace(/\D/g, '');
    if (rg.length === 9) {
      this.atendenteForm.get('rg')?.setErrors(null);
    }
  }

  validarTelefone(): void {
    const telefone = this.atendenteForm
      .get('telefone')
      ?.value.replace(/\D/g, '');
    if (telefone.length === 11) {
      this.atendenteForm.get('telefone')?.setErrors(null);
    }
  }

  /**
   * Formata o CPF com máscara.
   */
  formatarCPF(): void {
    let cpf = this.atendenteForm.get('cpf')?.value;
    cpf = cpf.replace(/\D/g, '');
    cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
    cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
    cpf = cpf.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    this.atendenteForm.get('cpf')?.setValue(cpf, { emitEvent: false });
  }

  /**
   * Formata o RG com máscara.
   */
  formatarRG(): void {
    let rg = this.atendenteForm.get('rg')?.value;
    rg = rg.replace(/\D/g, '');
    rg = rg.replace(/(\d{2})(\d)/, '$1.$2');
    rg = rg.replace(/(\d{3})(\d)/, '$1.$2');
    rg = rg.replace(/(\d{3})(\d{1})$/, '$1-$2');
    this.atendenteForm.get('rg')?.setValue(rg, { emitEvent: false });
  }

  /**
   * Formata o telefone com máscara.
   */
  formatarTelefone(): void {
    let telefone = this.atendenteForm.get('telefone')?.value;
    telefone = telefone.replace(/\D/g, '');
    telefone = telefone.replace(/(\d{2})(\d)/, '($1) $2');
    telefone = telefone.replace(/(\d{5})(\d{4})$/, '$1-$2');
    this.atendenteForm
      .get('telefone')
      ?.setValue(telefone, { emitEvent: false });
  }

  /**
   * Formata o CEP com máscara.
   */
  formatarCEP(): void {
    let cep = this.atendenteForm.get('endereco.cep')?.value;
    cep = cep.replace(/\D/g, '');
    cep = cep.replace(/(\d{5})(\d{3})$/, '$1-$2');
    this.atendenteForm.get('endereco.cep')?.setValue(cep, { emitEvent: false });
  }
}
