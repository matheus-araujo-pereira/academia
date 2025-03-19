import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environments';

export interface Endereco {
  id?: number;
  estado: string;
  cidade: string;
  bairro: string;
  logradouro: string;
  numero: string;
  cep: string;
}

export interface Plano {
  id: number;
  nome: string;
  valor: number;
  descricao?: string;
}

export interface Cliente {
  id: number;
  nome: string;
  login: string;
  senha: string;
  cpf: string;
  rg?: string;
  dataNascimento?: string;
  email?: string;
  telefone?: string;
  endereco: Endereco;
  plano: Plano;
}

@Injectable({
  providedIn: 'root',
})
export class ClienteService {
  private apiUrl = environment.apiUrl + '/clientes';
  constructor(private http: HttpClient) {}

  getAll(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(this.apiUrl);
  }

  search(nome: string, cpf: string): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(
      `${this.apiUrl}/search?nome=${nome}&cpf=${cpf}`
    );
  }

  getById(id: number): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.apiUrl}/${id}`);
  }

  create(cliente: Cliente): Observable<any> {
    return this.http.post(this.apiUrl, cliente, { responseType: 'text' });
  }

  update(id: number, cliente: Cliente): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, cliente, {
      responseType: 'text',
    });
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { responseType: 'text' });
  }
}
