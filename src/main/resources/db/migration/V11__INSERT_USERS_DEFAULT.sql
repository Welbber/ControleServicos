INSERT INTO users (username, password_hash, email)
VALUES ('admin', '$2a$10$KL0CgKce7NqnsBPAPkoKdueVSRmK8KZZ9DrvIPANF11jMguWGz/2y', 'admin@example.com');

INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE username = 'admin'), (SELECT id FROM roles WHERE name = 'ADMIN'));

-- vendedor
INSERT INTO Users (username, password_hash, email)
VALUES ('vendedor', '$2a$10$.yfm7DgYL7sIxPkxoR/GLeYhdKvvTVCEh7ibWzDr3N0/BMiMgXQ7O', 'vendedor@example.com');

INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE username = 'vendedor'), (SELECT id FROM roles WHERE name = 'SELLER'));

-- Usuário básico
INSERT INTO users (username, password_hash, email)
VALUES ('usuario', '$2a$10$ObCtPsoYY5o4tI3w1VvezuFn9A7Jy1xiynSv8z0SkJBbEiyh1j5GC', 'usuario@example.com');

INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE username = 'usuario'), (SELECT id FROM roles WHERE name = 'USER'));
