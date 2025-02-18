import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { routes } from './app-routing.module';

@NgModule({
  declarations: [
    // ...outras declarações (não os componentes standalone)...
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes)
  ],
  providers: []
  // Removido bootstrap: os componentes standalone serão inicializados via main.ts
})
export class AppModule { }
