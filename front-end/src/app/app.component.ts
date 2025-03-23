// Exemplo em AppComponent
import { Component } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { filter } from 'rxjs/operators';
import { MenuComponent } from './menu/menu.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true, // componente standalone
  imports: [RouterOutlet, CommonModule, MenuComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  showMenu = false;

  constructor(private router: Router) {
    // Exibe o menu se a rota não for "/login"
    this.router.events.subscribe((event) => {
      // Implemente sua lógica para definir showMenu conforme a rota
      // Por exemplo, se a rota não contem "login", exiba o menu:
      this.showMenu = !this.router.url.includes('/login');
    });
  }
}
