import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';

@NgModule({
  // Remova o array "declarations" se todos os seus componentes forem standalone
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule,
    // Remova AppComponent daqui
  ],
  providers: [],
  // Remova o bootstrap: [AppComponent]
})
export class AppModule {}
