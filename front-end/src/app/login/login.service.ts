import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

export interface LoginRequest {
  login: string;
  senha: string;
}

export interface LoginResponse {
  mensagem: string;
  tipoUsuario: string;
}

@Injectable()
export class LoginService {
  // URL do seu backend:
  private loginUrl = "http://localhost:8080/api/login";

  constructor(private http: HttpClient) {}

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.loginUrl, request);
  }
}
