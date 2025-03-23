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
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
  ],
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
          sessionStorage.setItem('userLogin', this.loginForm.value.login); // Armazena o login no sessionStorage
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
