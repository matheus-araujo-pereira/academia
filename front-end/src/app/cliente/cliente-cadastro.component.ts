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
        cep: ['', Validators.required],
      }),
      plano: this.fb.group({
        id: [null, Validators.required],
      }),
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
          this.clienteForm.patchValue(cliente);
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
    if (this.clienteForm.valid) {
      if (this.isEdit && this.id) {
        this.service.update(this.id, this.clienteForm.value).subscribe({
          next: (msg) => {
            alert(msg);
            this.router.navigate(['/clientes']);
          },
          error: (err) => {
            alert('Erro ao atualizar: ' + err.error);
          },
        });
      } else {
        this.service.create(this.clienteForm.value).subscribe({
          next: (msg) => {
            alert(msg);
            this.router.navigate(['/clientes']);
          },
          error: (err) => {
            alert('Erro ao cadastrar: ' + err.error);
          },
        });
      }
    }
  }
}
