-- Inserindo dados na tabela customers
INSERT INTO customers (id, name, email, document_number, phone_number, active, created_at, updated_at)
VALUES (1, 'João Silva', 'joao@example.com', '01263538088', '6122277193', true, now(), now());
INSERT INTO customers (id, name, email, document_number, phone_number, active, created_at, updated_at)
VALUES (2, 'Maria Oliveira', 'maria@example.com', '86776470073', '6172277198', true, now(), now());

-- Inserindo dados na tabela address
INSERT INTO address (id, customer_id, street, city, state, zip_code, district, number, complements, created_at,
                     updated_at)
VALUES (1, 1, 'Rua das Flores, 123', 'São Paulo', 'SP', '12345-678', 'CENTRO', 12902, 'SALA 3', NOW(), NOW());
INSERT INTO address (id, customer_id, street, city, state, zip_code, district, number, complements, created_at,
                     updated_at)
VALUES (2, 2, 'Rua dos Pinheiros, 200', 'Rio de Janeiro', 'RJ', '87654-321', 'CENTRO', 12902, 'SALA 3', NOW(), NOW());
