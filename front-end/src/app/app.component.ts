// Exemplo em AppComponent
import { Component } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { filter } from 'rxjs/operators';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router'; // Certifique-se de importar o RouterModule

@Component({
  selector: 'app-root',
  standalone: true, // componente standalone
  imports: [RouterOutlet, CommonModule, RouterModule], // Adicione RouterModule aqui
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  showMenu = false;
  isMobileMenuOpen = false;

  constructor(private router: Router) {
    // Exibe o menu se a rota não for "/login"
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.showMenu = !this.router.url.includes('/login');
        this.isMobileMenuOpen = false; // Fecha o menu móvel ao navegar
      }
    });
  }

  toggleMobileMenu(): void {
    this.isMobileMenuOpen = !this.isMobileMenuOpen;
  }

  deslogar(): void {
    this.router.navigate(['/login']);
  }
}
