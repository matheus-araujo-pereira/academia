import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environments';

export interface Exercicio {
  id?: number;
  nome: string;
  descricao?: string;
  carga?: number;
  repeticao?: number;
  series?: number;
}

@Injectable({
  providedIn: 'root',
})
export class ExercicioService {
  private apiUrl = environment.apiUrl + '/exercicios';

  constructor(private http: HttpClient) {
    console.log('ExercicioService URL:', this.apiUrl);
  }

  getAll(): Observable<Exercicio[]> {
    return this.http.get<Exercicio[]>(this.apiUrl);
  }

  search(nome: string): Observable<Exercicio[]> {
    return this.http.get<Exercicio[]>(`${this.apiUrl}/search?nome=${nome}`);
  }

  getById(id: number): Observable<Exercicio> {
    return this.http.get<Exercicio>(`${this.apiUrl}/${id}`);
  }

  create(exercicio: Exercicio): Observable<Exercicio> {
    return this.http.post<Exercicio>(this.apiUrl, exercicio);
  }

  update(id: number, exercicio: Exercicio): Observable<Exercicio> {
    return this.http.put<Exercicio>(`${this.apiUrl}/${id}`, exercicio);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
