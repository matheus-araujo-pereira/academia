import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environments';

@Injectable({
  providedIn: 'root',
})
export class DadosPessoaisService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getDadosPessoais(): Observable<any> {
    const login = sessionStorage.getItem('userLogin');
    return this.http.get<any>(`${this.apiUrl}/dados-pessoais?login=${login}`);
  }
}
