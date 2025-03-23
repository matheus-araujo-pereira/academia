import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormGroup,
  FormBuilder,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { ClienteService, Cliente, Plano } from './cliente.service';
import { Router, ActivatedRoute } from '@angular/router';
import { PlanoService } from '../plano/plano.service';

@Component({
  selector: 'app-cliente-cadastro',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './cliente-cadastro.component.html',
  styleUrls: ['./cliente-cadastro.component.css'],
})
export class ClienteCadastroComponent implements OnInit {
  clienteForm: FormGroup;
  isEdit = false;
  id: number | null = null;
  planos: Plano[] = [];

  constructor(
    private fb: FormBuilder,
    private service: ClienteService,
    private planoService: PlanoService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.clienteForm = this.fb.group({
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
      // Alterado: usa FormControl simples para "plano"
      plano: [null, Validators.required],
    });
  }

  ngOnInit(): void {
    this.fetchPlanos();
    this.route.paramMap.subscribe((params) => {
      const idParam = params.get('id');
      if (idParam) {
        this.isEdit = true;
        this.id = Number(idParam);
        this.service.getById(this.id).subscribe((cliente) => {
          this.clienteForm.patchValue({
            // ...existing code...
            ...cliente,
            // Ajuste: atribui o id do plano diretamente
            plano: cliente.plano?.id || null,
          });
        });
      }
    });
  }

  fetchPlanos(): void {
    this.planoService.getAll().subscribe({
      next: (data) => (this.planos = data),
      error: (err) => console.error(err),
    });
  }

  voltar(): void {
    this.router.navigate(['/clientes']);
  }

  salvar(): void {
    // Remover máscaras antes de validar
    const cpf = this.clienteForm.get('cpf')?.value.replace(/\D/g, '');
    const rg = this.clienteForm.get('rg')?.value.replace(/\D/g, '');
    const telefone = this.clienteForm.get('telefone')?.value.replace(/\D/g, '');
    const cep = this.clienteForm.get('endereco.cep')?.value.replace(/\D/g, '');

    this.clienteForm.patchValue({
      cpf: cpf,
      rg: rg,
      telefone: telefone,
      endereco: {
        ...this.clienteForm.get('endereco')?.value,
        cep: cep,
      },
    });

    if (this.clienteForm.valid) {
      const cliente = this.clienteForm.value;
      // Converte o valor do plano em objeto para enviar ao back-end
      cliente.plano = { id: cliente.plano };

      if (this.isEdit && this.id) {
        this.service.update(this.id, cliente).subscribe({
          next: () => {
            alert('Cliente atualizado com sucesso!');
            this.router.navigate(['/clientes']);
          },
          error: (err) => {
            console.error('Erro ao atualizar:', err);
            alert('Erro ao atualizar: ' + err?.error?.message);
          },
        });
      } else {
        this.service.create(cliente).subscribe({
          next: () => {
            alert('Cliente cadastrado com sucesso!');
            this.router.navigate(['/clientes']);
          },
          error: (err) => {
            console.error('Erro ao cadastrar:', err);
            alert('Erro ao cadastrar: ' + err?.error?.message);
          },
        });
      }
    } else {
      console.warn('Formulário inválido:', this.clienteForm);
      alert('Por favor, preencha todos os campos obrigatórios corretamente.');
    }
  }

  validarCPF(): void {
    const cpf = this.clienteForm.get('cpf')?.value.replace(/\D/g, '');
    if (cpf.length === 11) {
      this.clienteForm.get('cpf')?.setErrors(null);
    }
  }

  validarRG(): void {
    const rg = this.clienteForm.get('rg')?.value.replace(/\D/g, '');
    if (rg.length === 9) {
      this.clienteForm.get('rg')?.setErrors(null);
    }
  }

  validarTelefone(): void {
    const telefone = this.clienteForm.get('telefone')?.value.replace(/\D/g, '');
    if (telefone.length === 11) {
      this.clienteForm.get('telefone')?.setErrors(null);
    }
  }

  formatarCPF(): void {
    let cpf = this.clienteForm.get('cpf')?.value;
    cpf = cpf.replace(/\D/g, '');
    cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
    cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
    cpf = cpf.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    this.clienteForm.get('cpf')?.setValue(cpf, { emitEvent: false });
  }

  formatarRG(): void {
    let rg = this.clienteForm.get('rg')?.value;
    rg = rg.replace(/\D/g, '');
    rg = rg.replace(/(\d{2})(\d)/, '$1.$2');
    rg = rg.replace(/(\d{3})(\d)/, '$1.$2');
    rg = rg.replace(/(\d{3})(\d{1})$/, '$1-$2');
    this.clienteForm.get('rg')?.setValue(rg, { emitEvent: false });
  }

  formatarTelefone(): void {
    let telefone = this.clienteForm.get('telefone')?.value;
    telefone = telefone.replace(/\D/g, '');
    telefone = telefone.replace(/(\d{2})(\d)/, '($1) $2');
    telefone = telefone.replace(/(\d{5})(\d{4})$/, '$1-$2');
    this.clienteForm.get('telefone')?.setValue(telefone, { emitEvent: false });
  }

  formatarCEP(): void {
    let cep = this.clienteForm.get('endereco.cep')?.value;
    cep = cep.replace(/\D/g, '');
    cep = cep.replace(/(\d{5})(\d{3})$/, '$1-$2');
    this.clienteForm.get('endereco.cep')?.setValue(cep, { emitEvent: false });
  }
}
