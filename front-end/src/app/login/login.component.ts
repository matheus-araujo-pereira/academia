import { Component } from '@angular/core';
import { LoginService, LoginRequest, LoginResponse } from './login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  login: string = '';
  senha: string = '';
  mensagem: string = '';
  tipoUsuario: string = '';

  constructor(private loginService: LoginService) {}

  onSubmit(): void {
    const request: LoginRequest = { login: this.login, senha: this.senha };
    this.loginService.login(request).subscribe({
      next: (resp: LoginResponse) => {
        this.mensagem = resp.mensagem;
        this.tipoUsuario = resp.tipoUsuario;
      },
      error: (err) => {
        this.mensagem = 'Erro no login: ' + err.message;
        this.tipoUsuario = '';
      },
    });
  }
}
