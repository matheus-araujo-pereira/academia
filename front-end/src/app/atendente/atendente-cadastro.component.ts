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
      nome: ['', Validators.required],
      login: ['', Validators.required],
      senha: ['', Validators.required],
      cpf: ['', Validators.required],
      rg: [''],
      dataNascimento: [''],
      email: [''],
      telefone: [''],
      // Se houver dados de endereço, inclua-os aqui
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const idParam = params.get('id');
      if (idParam) {
        this.isEdit = true;
        this.id = Number(idParam);
        this.service.getById(this.id).subscribe((data) => {
          this.atendenteForm.patchValue(data);
        });
      }
    });
  }

  voltar(): void {
    this.router.navigate(['/atendentes']);
  }

  salvar(): void {
    if (this.atendenteForm.valid) {
      if (this.isEdit && this.id) {
        this.service.update(this.id, this.atendenteForm.value).subscribe({
          next: () => this.router.navigate(['/atendentes']),
          error: (err) => console.error(err),
        });
      } else {
        this.service.create(this.atendenteForm.value).subscribe({
          next: () => this.router.navigate(['/atendentes']),
          error: (err) => console.error(err),
        });
      }
    }
  }
}
