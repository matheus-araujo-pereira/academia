import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from './login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  loginForm: FormGroup;
  mensagem: string | null = null;

  constructor(private fb: FormBuilder, private loginService: LoginService) {
    this.loginForm = this.fb.group({
      login: ['', Validators.required],
      senha: ['', Validators.required],
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.loginService.login(this.loginForm.value).subscribe({
        next: (resp) => {
          this.mensagem = resp.mensagem + ' (' + resp.tipoUsuario + ')';
          // Redirecionar ou salvar token, conforme a lógica desejada.
        },
        error: (err) => {
          this.mensagem = 'Erro: ' + err.error.message;
        },
      });
    }
  }
}
