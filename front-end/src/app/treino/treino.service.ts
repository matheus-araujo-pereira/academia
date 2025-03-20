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

export interface Treino {
  id?: number;
  descricao: string;
  dataCriacao: string;
  // Supondo que os objetos cliente e professor já estejam preenchidos em outras telas
  cliente?: any;
  professor?: any;
  exercicios: Exercicio[];
}

@Injectable({
  providedIn: 'root',
})
export class TreinoService {
  private apiUrl = environment.apiUrl + '/treinos';

  constructor(private http: HttpClient) {
    console.log('TreinoService URL:', this.apiUrl);
  }

  getAll(): Observable<Treino[]> {
    return this.http.get<Treino[]>(this.apiUrl);
  }

  search(descricao: string): Observable<Treino[]> {
    return this.http.get<Treino[]>(
      `${this.apiUrl}/search?descricao=${descricao}`
    );
  }

  getById(id: number): Observable<Treino> {
    return this.http.get<Treino>(`${this.apiUrl}/${id}`);
  }

  create(treino: Treino): Observable<Treino> {
    return this.http.post<Treino>(this.apiUrl, treino);
  }

  update(id: number, treino: Treino): Observable<Treino> {
    return this.http.put<Treino>(`${this.apiUrl}/${id}`, treino);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
