import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environments';

export interface Atividade {
  id: number;
  nome: string;
  descricao?: string;
  horaInicio: string;
  horaFim: string;
  diasSemana: string;
  professor?: any; // adapte conforme o modelo necessário
  clientes?: any[];
}

@Injectable({
  providedIn: 'root',
})
export class AtividadeService {
  private apiUrl = environment.apiUrl + '/atividades';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Atividade[]> {
    return this.http.get<Atividade[]>(this.apiUrl);
  }

  getById(id: number): Observable<Atividade> {
    return this.http.get<Atividade>(`${this.apiUrl}/${id}`);
  }

  create(atividade: Atividade): Observable<Atividade> {
    return this.http.post<Atividade>(this.apiUrl, atividade);
  }

  update(id: number, atividade: Atividade): Observable<Atividade> {
    return this.http.put<Atividade>(`${this.apiUrl}/${id}`, atividade);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
