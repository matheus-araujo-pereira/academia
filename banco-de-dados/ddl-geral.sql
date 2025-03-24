CREATE SCHEMA IF NOT EXISTS academia;

CREATE TABLE IF NOT EXISTS academia.administrador(
    id bigserial PRIMARY KEY,
    nome varchar(100) NOT NULL,
    login VARCHAR(50) NOT NULL,
    senha varchar(100) NOT NULL,
    cpf varchar(11),
    rg varchar(15),
    data_nascimento date,
    email varchar(100),
    telefone varchar(20)
);

CREATE TABLE IF NOT EXISTS academia.endereco(
    id bigserial PRIMARY KEY,
    estado varchar(100) NOT NULL,
    cidade varchar(100) NOT NULL,
    bairro varchar(100) NOT NULL,
    logradouro varchar(200) NOT NULL,
    numero varchar(10) NOT NULL,
    cep varchar(10) NOT NULL,
    administrador_id bigint,
    CONSTRAINT fk_administrador FOREIGN KEY (administrador_id) REFERENCES academia.administrador(id)
);

CREATE TABLE IF NOT EXISTS academia.atendente(
    id bigserial PRIMARY KEY,
    nome varchar(100) NOT NULL,
    login varchar(50) NOT NULL,
    senha varchar(100) NOT NULL,
    cpf varchar(11),
    rg varchar(15),
    data_nascimento date,
    email varchar(100),
    telefone varchar(20),
    endereco_id bigint,
    CONSTRAINT fk_endereco FOREIGN KEY (endereco_id) REFERENCES academia.endereco(id)
);

CREATE TABLE IF NOT EXISTS academia.professor(
    id bigserial PRIMARY KEY,
    nome varchar(100) NOT NULL,
    login varchar(50) NOT NULL,
    senha varchar(100) NOT NULL,
    cpf varchar(11),
    rg varchar(15),
    data_nascimento date,
    email varchar(100),
    telefone varchar(20),
    endereco_id bigint,
    CONSTRAINT fk_professor_endereco FOREIGN KEY (endereco_id) REFERENCES academia.endereco(id)
);

CREATE TABLE IF NOT EXISTS academia.plano(
    id bigserial PRIMARY KEY,
    nome varchar(100) NOT NULL,
    valor numeric(10, 2) NOT NULL,
    descricao text,
    administrador_id bigint,
    CONSTRAINT fk_plano_administrador FOREIGN KEY (administrador_id) REFERENCES academia.administrador(id)
);

CREATE TABLE IF NOT EXISTS academia.cliente(
    id bigserial PRIMARY KEY,
    nome varchar(100) NOT NULL,
    login varchar(50) NOT NULL,
    senha varchar(100) NOT NULL,
    cpf varchar(11),
    rg varchar(15),
    data_nascimento date,
    email varchar(100),
    telefone varchar(20),
    endereco_id bigint,
    plano_id bigint,
    CONSTRAINT fk_cliente_endereco FOREIGN KEY (endereco_id) REFERENCES academia.endereco(id),
    CONSTRAINT fk_cliente_plano FOREIGN KEY (plano_id) REFERENCES academia.plano(id)
);

CREATE TABLE IF NOT EXISTS academia.treino(
    id bigserial PRIMARY KEY,
    descricao text NOT NULL,
    data_criacao date NOT NULL,
    cliente_id bigint,
    professor_id bigint,
    CONSTRAINT fk_treino_cliente FOREIGN KEY (cliente_id) REFERENCES academia.cliente(id),
    CONSTRAINT fk_treino_professor FOREIGN KEY (professor_id) REFERENCES academia.professor(id)
);

CREATE TABLE IF NOT EXISTS academia.exercicio(
    id bigserial PRIMARY KEY,
    nome varchar(100) NOT NULL,
    descricao text,
    carga numeric(10, 2),
    repeticao integer,
    series integer,
    treino_id bigint,
    CONSTRAINT fk_exercicio_treino FOREIGN KEY (treino_id) REFERENCES academia.treino(id)
);

CREATE TABLE IF NOT EXISTS academia.atividade(
    id bigserial PRIMARY KEY,
    nome varchar(100) NOT NULL,
    descricao text,
    hora_inicio time NOT NULL,
    hora_fim time NOT NULL,
    dias_semana varchar(100),
    professor_id bigint,
    CONSTRAINT fk_atividade_professor FOREIGN KEY (professor_id) REFERENCES academia.professor(id)
);

