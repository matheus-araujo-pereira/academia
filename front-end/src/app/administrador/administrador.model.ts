export interface Administrador {
  id?: number;
  nome: string;
  login: string;
  senha: string;
  cpf?: string;
  rg?: string;
  dataNascimento?: string;
  email?: string;
  telefone?: string;
}
