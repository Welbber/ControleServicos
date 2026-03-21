CREATE TABLE IF NOT EXISTS tenants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    trade_name VARCHAR(255) NOT NULL,
    document_number VARCHAR(20) NOT NULL UNIQUE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now()
);

INSERT INTO tenants (id, trade_name, document_number)
VALUES (gen_random_uuid(), 'Vital Autopeças', '00000000000000');

ALTER TABLE users ADD COLUMN tenant_id UUID;

UPDATE users SET tenant_id = (SELECT id FROM tenants LIMIT 1);

ALTER TABLE users ALTER COLUMN tenant_id SET NOT NULL;

ALTER TABLE users 
    ADD CONSTRAINT fk_users_tenants 
    FOREIGN KEY (tenant_id) 
    REFERENCES tenants (id);
