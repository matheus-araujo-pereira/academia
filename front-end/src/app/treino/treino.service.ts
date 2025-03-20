import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environments';

export interface Treino {
  id?: number;
  descricao: string;
  dataCriacao: string;
  cliente?: any;
  professor?: any;
  exercicios?: any[];
}

@Injectable({
  providedIn: 'root',
})
export class TreinoService {
  private apiUrl = environment.apiUrl + '/treinos';

  constructor(private http: HttpClient) {}

  getAll(login: string): Observable<Treino[]> {
    return this.http.get<Treino[]>(`${this.apiUrl}?login=${login}`);
  }

  create(login: string, treino: Treino): Observable<Treino> {
    return this.http.post<Treino>(`${this.apiUrl}?login=${login}`, treino);
  }

  update(id: number, login: string, treino: Treino): Observable<Treino> {
    return this.http.put<Treino>(`${this.apiUrl}/${id}?login=${login}`, treino);
  }

  delete(id: number, login: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}?login=${login}`);
  }
}
