import { Component } from '@angular/core';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { UserService } from '../shared/user.service';

@Component({
  selector: 'app-entrar',
  standalone: true,
  imports: [FormsModule, RouterModule],
  templateUrl: './entrar.component.html',
  styleUrls: ['./entrar.component.css']
})
export class EntrarComponent {
  username: string = '';
  password: string = '';

  constructor(private router: Router, private userService: UserService) {}

  onLogin() {
    let role = 'Cliente';
    if(this.username.toLowerCase() === 'admin') {
      role = 'Administrador';
    } else if(this.username.toLowerCase() === 'atendente'){
      role = 'Atendente';
    } else if(this.username.toLowerCase() === 'professor'){
      role = 'Professor';
    }
    this.userService.setUserRole(role);
    console.log('Login realizado como', role);
    this.router.navigate(['/dashboard']);
  }
}
