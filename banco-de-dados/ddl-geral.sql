-- Cria o schema "academia" se ainda não existir
CREATE SCHEMA IF NOT EXISTS academia;

-- DROP das tabelas de baixo para cima (respeitando as dependências):
DROP TABLE IF EXISTS academia.atividade CASCADE;

DROP TABLE IF EXISTS academia.exercicio CASCADE;

DROP TABLE IF EXISTS academia.treino CASCADE;

DROP TABLE IF EXISTS academia.cliente CASCADE;

DROP TABLE IF EXISTS academia.plano CASCADE;

DROP TABLE IF EXISTS academia.professor CASCADE;

DROP TABLE IF EXISTS academia.atendente CASCADE;

DROP TABLE IF EXISTS academia.endereco CASCADE;

DROP TABLE IF EXISTS academia.administrador CASCADE;

-- Tabela que armazena os dados do administrador da academia
CREATE TABLE IF NOT EXISTS academia.administrador(
    id bigserial PRIMARY KEY, -- Identificador único
    nome varchar(100) NOT NULL, -- Nome do administrador
    login VARCHAR(50) NOT NULL, -- Login de acesso
    senha varchar(100) NOT NULL, -- Senha de acesso
    cpf varchar(11), -- CPF do administrador
    rg varchar(15), -- RG do administrador
    data_nascimento date, -- Data de nascimento
    email varchar(100), -- E‑mail de contato
    telefone varchar(20) -- Telefone de contato
);

-- Tabela para armazenar endereços relacionados ao administrador
CREATE TABLE IF NOT EXISTS academia.endereco(
    id bigserial PRIMARY KEY, -- Identificador único
    estado varchar(100) NOT NULL,
    cidade varchar(100) NOT NULL,
    bairro varchar(100) NOT NULL,
    logradouro varchar(200) NOT NULL,
    numero varchar(10) NOT NULL,
    cep varchar(10) NOT NULL,
    administrador_id bigint, -- Chave estrangeira para administrador
    CONSTRAINT fk_administrador FOREIGN KEY (administrador_id) REFERENCES academia.administrador(id)
);

-- Tabela que armazena os dados de atendentes
CREATE TABLE IF NOT EXISTS academia.atendente(
    id bigserial PRIMARY KEY, -- Identificador único
    nome varchar(100) NOT NULL,
    login varchar(50) NOT NULL,
    senha varchar(100) NOT NULL,
    cpf varchar(11),
    rg varchar(15),
    data_nascimento date,
    email varchar(100),
    telefone varchar(20),
    endereco_id bigint, -- Chave estrangeira para endereço
    CONSTRAINT fk_endereco FOREIGN KEY (endereco_id) REFERENCES academia.endereco(id)
);

-- Tabela para armazenar os dados dos professores
CREATE TABLE IF NOT EXISTS academia.professor(
    id bigserial PRIMARY KEY, -- Identificador único
    nome varchar(100) NOT NULL,
    login varchar(50) NOT NULL,
    senha varchar(100) NOT NULL,
    cpf varchar(11),
    rg varchar(15),
    data_nascimento date,
    email varchar(100),
    telefone varchar(20),
    endereco_id bigint, -- Chave estrangeira para endereço
    CONSTRAINT fk_professor_endereco FOREIGN KEY (endereco_id) REFERENCES academia.endereco(id)
);

-- Tabela que armazena os planos oferecidos pela academia
CREATE TABLE IF NOT EXISTS academia.plano(
    id bigserial PRIMARY KEY, -- Identificador único
    nome varchar(100) NOT NULL, -- Nome do plano
    valor numeric(10, 2) NOT NULL, -- Valor do plano
    descricao text, -- Descrição do plano
    administrador_id bigint, -- Chave estrangeira para administrador
    CONSTRAINT fk_plano_administrador FOREIGN KEY (administrador_id) REFERENCES academia.administrador(id)
);

-- Tabela que armazena os dados dos clientes
CREATE TABLE IF NOT EXISTS academia.cliente(
    id bigserial PRIMARY KEY, -- Identificador único
    nome varchar(100) NOT NULL,
    login varchar(50) NOT NULL,
    senha varchar(100) NOT NULL,
    cpf varchar(11),
    rg varchar(15),
    data_nascimento date,
    email varchar(100),
    telefone varchar(20),
    endereco_id bigint, -- Chave estrangeira para endereço
    plano_id bigint, -- Chave estrangeira para plano
    CONSTRAINT fk_cliente_endereco FOREIGN KEY (endereco_id) REFERENCES academia.endereco(id),
    CONSTRAINT fk_cliente_plano FOREIGN KEY (plano_id) REFERENCES academia.plano(id)
);

-- Tabela que armazena os treinos dos clientes
CREATE TABLE IF NOT EXISTS academia.treino(
    id bigserial PRIMARY KEY, -- Identificador único
    descricao text NOT NULL, -- Descrição do treino
    data_criacao date NOT NULL, -- Data em que o treino foi criado
    cliente_id bigint, -- Chave estrangeira para cliente
    professor_id bigint, -- Chave estrangeira para professor
    CONSTRAINT fk_treino_cliente FOREIGN KEY (cliente_id) REFERENCES academia.cliente(id),
    CONSTRAINT fk_treino_professor FOREIGN KEY (professor_id) REFERENCES academia.professor(id)
);

-- Tabela que armazena os exercícios de cada treino
CREATE TABLE IF NOT EXISTS academia.exercicio(
    id bigserial PRIMARY KEY, -- Identificador único
    nome varchar(100) NOT NULL, -- Nome do exercício
    descricao text, -- Descrição detalhada
    carga numeric(10, 2), -- Carga utilizada (ex: kg)
    repeticao integer, -- Quantidade de repetições
    series integer, -- Quantidade de séries
    treino_id bigint, -- Chave estrangeira para treino
    CONSTRAINT fk_exercicio_treino FOREIGN KEY (treino_id) REFERENCES academia.treino(id)
);

-- Tabela para armazenar atividades gerais ministradas por professores
CREATE TABLE IF NOT EXISTS academia.atividade(
    id bigserial PRIMARY KEY, -- Identificador único
    nome varchar(100) NOT NULL, -- Nome da atividade
    descricao text, -- Descrição da atividade
    hora_inicio time NOT NULL, -- Horário de início
    hora_fim time NOT NULL, -- Horário de término
    dias_semana varchar(100), -- Dias da semana em que ocorre
    professor_id bigint, -- Chave estrangeira para professor
    CONSTRAINT fk_atividade_professor FOREIGN KEY (professor_id) REFERENCES academia.professor(id)
);

