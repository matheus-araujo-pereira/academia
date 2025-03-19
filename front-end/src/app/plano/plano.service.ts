import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environments';

export interface Plano {
  id: number;
  nome: string;
  valor: number;
  descricao?: string;
  // administrador pode ser incluído se necessário
}

@Injectable({
  providedIn: 'root',
})
export class PlanoService {
  private apiUrl = environment.apiUrl + '/planos';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Plano[]> {
    return this.http.get<Plano[]>(this.apiUrl);
  }

  getById(id: number): Observable<Plano> {
    return this.http.get<Plano>(`${this.apiUrl}/${id}`);
  }

  create(plano: Plano): Observable<any> {
    return this.http.post(this.apiUrl, plano, { responseType: 'text' });
  }

  update(id: number, plano: Plano): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, plano, {
      responseType: 'text',
    });
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { responseType: 'text' });
  }

  search(nome: string): Observable<Plano[]> {
    return this.http.get<Plano[]>(`${this.apiUrl}/search?nome=${nome}`);
  }
}
