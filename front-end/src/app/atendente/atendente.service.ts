import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environments';

export interface Atendente {
  id: number;
  nome: string;
  login: string;
  senha: string;
  cpf: string;
  rg?: string;
  dataNascimento?: string;
  email?: string;
  telefone?: string;
  // endereco: any  // Adapte se houver estrutura para endereço
}

@Injectable({
  providedIn: 'root',
})
export class AtendenteService {
  private apiUrl = environment.apiUrl + '/atendentes';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Atendente[]> {
    return this.http.get<Atendente[]>(this.apiUrl);
  }

  search(nome: string, cpf: string): Observable<Atendente[]> {
    return this.http.get<Atendente[]>(
      `${this.apiUrl}/search?nome=${nome}&cpf=${cpf}`
    );
  }

  getById(id: number): Observable<Atendente> {
    return this.http.get<Atendente>(`${this.apiUrl}/${id}`);
  }

  create(atendente: Atendente): Observable<Atendente> {
    return this.http.post<Atendente>(this.apiUrl, atendente);
  }

  update(id: number, atendente: Atendente): Observable<Atendente> {
    return this.http.put<Atendente>(`${this.apiUrl}/${id}`, atendente);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
