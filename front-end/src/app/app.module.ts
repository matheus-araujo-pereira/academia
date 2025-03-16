import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

@NgModule({
  // Remova o array "declarations" se todos os seus componentes forem standalone
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule,
    AppComponent, // como componente standalone, ele é importado
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
