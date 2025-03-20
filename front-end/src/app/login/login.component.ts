/**
 * Componente Angular que gerencia o formulário de login e exibe mensagens de sucesso ou erro.
 */
import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { LoginService } from './login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  loginForm: FormGroup;
  mensagem: string | null = null;

  constructor(
    private fb: FormBuilder,
    private loginService: LoginService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      login: ['', Validators.required],
      senha: ['', Validators.required],
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.loginService.login(this.loginForm.value).subscribe({
        next: (resp) => {
          this.mensagem = `Login realizado com sucesso! Você é: ${resp.tipoUsuario}`;
          alert(this.mensagem);
          // Alterado: redireciona para /dados-pessoais em vez de /menu
          this.router.navigate(['/dados-pessoais']);
        },
        error: (err) => {
          this.mensagem =
            'Erro ao realizar login: ' +
            (err?.error?.message || 'Usuário ou senha inválidos.');
        },
      });
    }
  }
}
