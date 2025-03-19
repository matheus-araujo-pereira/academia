import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environments';

/**
 * Interface que representa o Endereco do atendente.
 */
export interface Endereco {
  id?: number;
  estado: string;
  cidade: string;
  bairro: string;
  logradouro: string;
  numero: string;
  cep: string;
}

/**
 * Interface que representa o modelo do Atendente.
 */
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
  endereco: Endereco;
}

/**
 * Serviço responsável por operações de CRUD de Atendente
 * e busca por nome/CPF.
 */
@Injectable({
  providedIn: 'root',
})
export class AtendenteService {
  private apiUrl = environment.apiUrl + '/atendentes';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Atendente[]> {
    return this.http.get<Atendente[]>(this.apiUrl);
  }

  /**
   * Retorna todos os atendentes ou filtrados por nome/cpf, caso especificado.
   */
  search(nome: string, cpf: string): Observable<Atendente[]> {
    return this.http.get<Atendente[]>(
      `${this.apiUrl}/search?nome=${nome}&cpf=${cpf}`
    );
  }

  /**
   * Busca um atendente específico pelo ID.
   */
  getById(id: number): Observable<Atendente> {
    return this.http.get<Atendente>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo atendente.
   */
  create(atendente: Atendente): Observable<Atendente> {
    return this.http.post<Atendente>(this.apiUrl, atendente);
  }

  /**
   * Atualiza um atendente existente.
   */
  update(id: number, atendente: Atendente): Observable<Atendente> {
    return this.http.put<Atendente>(`${this.apiUrl}/${id}`, atendente);
  }

  /**
   * Exclui um atendente pelo ID.
   */
  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
