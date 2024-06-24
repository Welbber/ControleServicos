-- ADD Papéis
INSERT INTO roles (name)
VALUES ('ADMIN');
INSERT INTO roles (name)
VALUES ('USER');
INSERT INTO roles (name)
VALUES ('SELLER');

-- ADD PERMISSIONS
INSERT INTO permissions (name)
VALUES ('CREATE');
INSERT INTO permissions (name)
VALUES ('READ');
INSERT INTO permissions (name)
VALUES ('UPDATE');
INSERT INTO permissions (name)
VALUES ('DELETE');


-- Associar Permissões ao Papel ADMIN
INSERT INTO role_permissions (role_id, permission_id)
VALUES ((SELECT id FROM roles WHERE name = 'ADMIN'), (SELECT id FROM permissions WHERE name = 'CREATE')),
       ((SELECT id FROM roles WHERE name = 'ADMIN'), (SELECT id FROM permissions WHERE name = 'READ')),
       ((SELECT id FROM roles WHERE name = 'ADMIN'), (SELECT id FROM permissions WHERE name = 'UPDATE')),
       ((SELECT id FROM roles WHERE name = 'ADMIN'), (SELECT id FROM permissions WHERE name = 'DELETE'));

-- Associar Permissões ao Papel USER
INSERT INTO role_permissions (role_id, permission_id)
VALUES ((SELECT id FROM roles WHERE name = 'USER'), (SELECT id FROM permissions WHERE name = 'READ'));

-- Associar Permissões ao Papel SELLER
INSERT INTO role_permissions (role_id, permission_id)
VALUES ((SELECT id FROM roles WHERE name = 'SELLER'), (SELECT id FROM permissions WHERE name = 'CREATE')),
       ((SELECT id FROM roles WHERE name = 'SELLER'), (SELECT id FROM permissions WHERE name = 'READ')),
       ((SELECT id FROM roles WHERE name = 'SELLER'), (SELECT id FROM permissions WHERE name = 'UPDATE'));

