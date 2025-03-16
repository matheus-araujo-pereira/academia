// Exemplo em AppComponent
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true, // componente standalone
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
})
export class AppComponent {}
