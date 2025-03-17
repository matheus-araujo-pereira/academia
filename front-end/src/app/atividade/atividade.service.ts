import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environments';

export interface Atividade {
  id: number;
  nome: string;
  descricao: string;
  horaInicio: string;
  horaFim: string;
  diasSemana: string;
  professor: { id: number };
  clientes: Array<{ id: number }>;
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

  create(atividade: Atividade): Observable<Atividade> {
    return this.http.post<Atividade>(this.apiUrl, atividade);
  }

  update(id: number, atividade: Atividade): Observable<Atividade> {
    return this.http.put<Atividade>(`${this.apiUrl}/${id}`, atividade);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  // Para inscrição dos clientes: chama o endpoint /api/clientes/{clienteId}/atividades/{atividadeId}/inscrever
  inscrever(clienteId: number, atividadeId: number): Observable<Atividade> {
    const url =
      environment.apiUrl +
      `/clientes/${clienteId}/atividades/${atividadeId}/inscrever`;
    return this.http.post<Atividade>(url, {});
  }
}
