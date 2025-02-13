-- Drop tables existentes (na ordem inversa das dependências) se necessário
DROP TABLE IF EXISTS cliente_atividade;
DROP TABLE IF EXISTS exercicio;
DROP TABLE IF EXISTS treino;
DROP TABLE IF EXISTS atividade;
DROP TABLE IF EXISTS cliente;
DROP TABLE IF EXISTS plano;
DROP TABLE IF EXISTS professor;
DROP TABLE IF EXISTS atendente;
DROP TABLE IF EXISTS administrador;
DROP TABLE IF EXISTS endereco;
---------------------------
-- Tabela: endereco
---------------------------
CREATE TABLE endereco (
    id SERIAL PRIMARY KEY,
    estado VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    bairro VARCHAR(100),
    logradouro VARCHAR(255),
    numero VARCHAR(50),
    cep VARCHAR(20)
);
---------------------------
-- Tabela: administrador
---------------------------
CREATE TABLE administrador (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    login VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    cpf VARCHAR(20) UNIQUE NOT NULL,
    rg VARCHAR(20),
    data_nascimento DATE,
    email VARCHAR(255),
    telefone VARCHAR(50)
);
---------------------------
-- Tabela: atendente
---------------------------
CREATE TABLE atendente (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    login VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    cpf VARCHAR(20) UNIQUE NOT NULL,
    rg VARCHAR(20),
    data_nascimento DATE,
    email VARCHAR(255),
    telefone VARCHAR(50),
    endereco_id INTEGER REFERENCES endereco(id)
);
---------------------------
-- Tabela: professor
---------------------------
CREATE TABLE professor (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    login VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    cpf VARCHAR(20) UNIQUE NOT NULL,
    rg VARCHAR(20),
    data_nascimento DATE,
    email VARCHAR(255),
    telefone VARCHAR(50),
    endereco_id INTEGER REFERENCES endereco(id)
);
---------------------------
-- Tabela: plano
---------------------------
CREATE TABLE plano (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255),
    valor MONEY,
    descricao TEXT
);
---------------------------
-- Tabela: cliente
---------------------------
CREATE TABLE cliente (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    login VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    cpf VARCHAR(20) UNIQUE NOT NULL,
    rg VARCHAR(20),
    data_nascimento DATE,
    email VARCHAR(255),
    telefone VARCHAR(50),
    plano_id INTEGER REFERENCES plano(id),
    endereco_id INTEGER REFERENCES endereco(id)
);
---------------------------
-- Tabela: atividade
---------------------------
CREATE TABLE atividade (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    data_inicio TIMESTAMP,
    data_fim TIMESTAMP
);
---------------------------
-- Tabela: treino
---------------------------
CREATE TABLE treino (
    id SERIAL PRIMARY KEY,
    descricao TEXT,
    data_criacao TIMESTAMP,
    cliente_id INTEGER REFERENCES cliente(id),
    professor_id INTEGER REFERENCES professor(id)
);
---------------------------
-- Tabela: exercicio
---------------------------
CREATE TABLE exercicio (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    carga REAL,
    repeticoes INTEGER,
    series INTEGER,
    treino_id INTEGER REFERENCES treino(id)
);
---------------------------
-- Tabela para relacionamento M:N entre Cliente e Atividade
---------------------------
CREATE TABLE cliente_atividade (
    cliente_id INTEGER REFERENCES cliente(id),
    atividade_id INTEGER REFERENCES atividade(id),
    PRIMARY KEY (cliente_id, atividade_id)
);