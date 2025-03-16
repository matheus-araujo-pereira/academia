import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Administrador } from './administrador.model';

@Injectable({ providedIn: 'root' })
export class AdministradorService {
  private baseUrl = '/api/administradores';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Administrador[]> {
    return this.http.get<Administrador[]>(this.baseUrl);
  }

  create(admin: Administrador): Observable<Administrador> {
    return this.http.post<Administrador>(this.baseUrl, admin);
  }
}
