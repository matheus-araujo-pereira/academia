INSERT INTO academia.administrador(nome, login, senha, cpf, rg, data_nascimento, email, telefone)
    VALUES ('Matheus Araujo', 'matheus.araujo', 'senhaSegura123', '12345678909', '12.345.678-9', '1985-06-15', 'matheus.araujo@email.com', '(11) 98765-4321'),
('Gabriel Teixeira', 'gabriel.teixeira', 'senhaSegura456', '23456789012', '23.456.789-0', '1990-02-20', 'gabriel.teixeira@email.com', '(21) 97654-3210'),
('Breno Murilo', 'breno.murilo', 'senhaSegura789', '34567890123', '34.567.890-1', '1988-10-30', 'breno.murilo@email.com', '(31) 96543-2109');

INSERT INTO academia.plano(nome, valor, descricao, administrador_id)
    VALUES ('Basico', 99.00, 'Acesso somente a academia e treinos personalizados', 1),
('Premium', 199.00, 'Acesso a atividades extras além dos treinos personalizados', 1);

INSERT INTO academia.endereco(estado, cidade, bairro, logradouro, numero, cep, administrador_id)
    VALUES ('SP', 'São Paulo', 'Mooca', 'Rua A', '10', '01001-000', 1),
('RJ', 'Rio de Janeiro', 'Copacabana', 'Avenida Atlântica', '1500', '22021-001', 1);

INSERT INTO academia.atendente(nome, login, senha, cpf, rg, data_nascimento, email, telefone, endereco_id)
    VALUES ('Tahiane Araujo Barreto', 'tahiane', 'tahiane2104', '12345678901', '1234567', '1982-04-21', 'tahiane@example.com', '+5511912345678', 1),
('Carlos Silva', 'carlos', 'carlos1234', '23456789012', '2345678', '1990-05-12', 'carlos.silva@example.com', '+5511987654321', 2);

INSERT INTO academia.professor(nome, login, senha, cpf, rg, data_nascimento, email, telefone, endereco_id)
    VALUES ('João da Silva', 'joaosilva', 'senhaSegura123', '98765432101', '7654321', '1980-05-15', 'joao.silva@example.com', '+5511999998888', 1),
('Ana Pereira', 'anapereira', 'senhaSegura456', '12345678902', '8765432', '1985-03-25', 'ana.pereira@example.com', '+5511999888777', 2);

-- Inserir clientes
INSERT INTO academia.cliente(nome, login, senha, cpf, rg, data_nascimento, email, telefone, endereco_id, plano_id)
    VALUES ('Alice Costa', 'alicec', 'senhaAlice2025', '11122233344', '123456789', '1990-01-01', 'alice.costa@example.com', '+5511987654321', 1, 1),
('Bruna Souza', 'brunasouza', 'senhaBruna2025', '22334455667', '987654321', '1992-07-11', 'bruna.souza@example.com', '+5511998776655', 2, 1),
('Carlos Mendes', 'carlosmendes', 'senhaCarlos123', '33445566778', '112233445', '1995-12-05', 'carlos.mendes@example.com', '+5511987766554', 1, 2),
('Daniela Rocha', 'danielarocha', 'senhaDani456', '44556677889', '223344556', '1988-02-17', 'daniela.rocha@example.com', '+5511996655443', 2, 2),
('Emanuel Costa', 'emanuelcosta', 'senhaEmanuel789', '55667788990', '334455667', '1993-06-22', 'emanuel.costa@example.com', '+5511985544332', 1, 1),
('Fernanda Lima', 'fernandalima', 'senhaFernanda101', '66778899001', '445566778', '1990-11-13', 'fernanda.lima@example.com', '+5511994433221', 2, 1),
('Gustavo Silva', 'gustavosilva', 'senhaGustavo2020', '77889900112', '556677889', '1989-04-08', 'gustavo.silva@example.com', '+5511983322110', 1, 2),
('Helena Barbosa', 'helenabarbosa', 'senhaHelena999', '88990011223', '667788990', '1991-08-20', 'helena.barbosa@example.com', '+5511977211009', 2, 2),
('Igor Martins', 'igormartins', 'senhaIgor999', '99001122334', '778899001', '1994-01-09', 'igor.martins@example.com', '+5511966100998', 1, 1),
('Jéssica Alves', 'jessicaalves', 'senhaJessica333', '10012223345', '889900112', '1986-09-30', 'jessica.alves@example.com', '+5511955099887', 2, 1),
('Kleber Souza', 'klebersouza', 'senhaKleber678', '21123334456', '990011223', '1992-03-17', 'kleber.souza@example.com', '+5511944998776', 1, 2),
('Lúcia Nunes', 'lucianunes', 'senhaLucia321', '32234445567', '101122334', '1996-12-26', 'lucia.nunes@example.com', '+5511933887665', 2, 1),
('Marco Silva', 'marcosilva', 'senhaMarco2021', '43345556678', '202233445', '1987-11-03', 'marco.silva@example.com', '+5511922776554', 1, 2),
('Natália Costa', 'nataliacosta', 'senhaNatalia456', '54456667789', '313344556', '1990-04-17', 'natalia.costa@example.com', '+5511911665443', 2, 2),
('Otávio Pereira', 'otaviopereira', 'senhaOtavio789', '65567778890', '424455667', '1993-10-15', 'otavio.pereira@example.com', '+5511900554332', 1, 1),
('Paula Fernandes', 'paulaf', 'senhaPaula101', '76678889901', '535566778', '1992-02-28', 'paula.fernandes@example.com', '+5511899443221', 2, 1);

-- Inserindo treino
INSERT INTO academia.treino(descricao, data_criacao, cliente_id, professor_id)
    VALUES ('Treino de Força - Sessão 1 para Cliente 1', '2025-03-16', 1, 1);

-- Inserindo exercícios para o treino do cliente 1
INSERT INTO academia.exercicio(nome, descricao, carga, repeticao, series, treino_id)
    VALUES ('Agachamento', 'Agachamento com barra', 80.0, 12, 3, 1),
('Supino', 'Supino reto com barra', 60.0, 10, 4, 1);

-- Inserindo treino
INSERT INTO academia.treino(descricao, data_criacao, cliente_id, professor_id)
    VALUES ('Treino de Força - Sessão 1 para Cliente 2', '2025-03-16', 2, 2);

-- Inserindo exercícios para o treino do cliente 2
INSERT INTO academia.exercicio(nome, descricao, carga, repeticao, series, treino_id)
    VALUES ('Leg Press', 'Leg Press 45 graus', 120.0, 15, 3, 2),
('Remada', 'Remada unilateral', 40.0, 12, 4, 2);

-- Inserindo treino
INSERT INTO academia.treino(descricao, data_criacao, cliente_id, professor_id)
    VALUES ('Treino de Força - Sessão 1 para Cliente 3', '2025-03-16', 3, 3);

-- Inserindo exercícios para o treino do cliente 3
INSERT INTO academia.exercicio(nome, descricao, carga, repeticao, series, treino_id)
    VALUES ('Puxada Frente', 'Puxada frontal com barra', 70.0, 12, 3, 3),
('Cadeira Abdutora', 'Cadeira abdutora para glúteos', 30.0, 15, 4, 3);

-- Inserindo treino
INSERT INTO academia.treino(descricao, data_criacao, cliente_id, professor_id)
    VALUES ('Treino de Força - Sessão 1 para Cliente 4', '2025-03-16', 4, 4);

-- Inserindo exercícios para o treino do cliente 4
INSERT INTO academia.exercicio(nome, descricao, carga, repeticao, series, treino_id)
    VALUES ('Cadeira Extensora', 'Cadeira extensora para quadríceps', 45.0, 10, 3, 4),
('Barra Fixa', 'Barra fixa para costas', 0.0, 8, 4, 4);

-- Inserindo treino
INSERT INTO academia.treino(descricao, data_criacao, cliente_id, professor_id)
    VALUES ('Treino de Força - Sessão 1 para Cliente 5', '2025-03-16', 5, 1);

-- Inserindo exercícios para o treino do cliente 5
INSERT INTO academia.exercicio(nome, descricao, carga, repeticao, series, treino_id)
    VALUES ('Stiff', 'Stiff com barra', 100.0, 12, 3, 5),
('Flexão', 'Flexão de braço tradicional', 0.0, 20, 4, 5);

