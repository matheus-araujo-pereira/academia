/**
 * Serviço que fornece métodos para comunicação com a API de professores.
 * Inclui operações de listagem, busca, criação, atualização e exclusão.
 */
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

export interface Professor {
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

@Injectable({
  providedIn: 'root',
})
export class ProfessorService {
  // Certifique-se de que environment.apiUrl contenha '/api'
  private apiUrl = environment.apiUrl + '/professores';

  constructor(private http: HttpClient) {
    console.log('ProfessorService URL:', this.apiUrl);
  }

  /**
   * Retorna todos os professores.
   */
  getAll(): Observable<Professor[]> {
    console.log('Buscando professores na URL:', this.apiUrl);
    return this.http.get<Professor[]>(this.apiUrl);
  }

  /**
   * Busca professores pelo nome e CPF.
   */
  search(nome: string, cpf: string): Observable<Professor[]> {
    return this.http.get<Professor[]>(
      `${this.apiUrl}/search?nome=${nome}&cpf=${cpf}`
    );
  }

  /**
   * Retorna um professor pelo ID.
   */
  getById(id: number): Observable<Professor> {
    return this.http.get<Professor>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo professor.
   */
  create(professor: Professor): Observable<Professor> {
    return this.http.post<Professor>(this.apiUrl, professor);
  }

  /**
   * Atualiza os dados de um professor.
   */
  update(id: number, professor: Professor): Observable<Professor> {
    return this.http.put<Professor>(`${this.apiUrl}/${id}`, professor);
  }

  /**
   * Exclui um professor pelo ID.
   */
  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
