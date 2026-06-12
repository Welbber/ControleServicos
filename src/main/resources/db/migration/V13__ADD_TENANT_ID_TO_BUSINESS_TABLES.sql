ALTER TABLE customers ADD COLUMN tenant_id UUID;
ALTER TABLE vehicles ADD COLUMN tenant_id UUID;
ALTER TABLE order_service ADD COLUMN tenant_id UUID;
ALTER TABLE itens ADD COLUMN tenant_id UUID;

UPDATE customers SET tenant_id = (SELECT id FROM tenants LIMIT 1);
UPDATE vehicles SET tenant_id = (SELECT id FROM tenants LIMIT 1);
UPDATE order_service SET tenant_id = (SELECT id FROM tenants LIMIT 1);
UPDATE itens SET tenant_id = (SELECT id FROM tenants LIMIT 1);

ALTER TABLE customers ALTER COLUMN tenant_id SET NOT NULL;
ALTER TABLE vehicles ALTER COLUMN tenant_id SET NOT NULL;
ALTER TABLE order_service ALTER COLUMN tenant_id SET NOT NULL;
ALTER TABLE itens ALTER COLUMN tenant_id SET NOT NULL;

ALTER TABLE customers ADD CONSTRAINT fk_customers_tenants FOREIGN KEY (tenant_id) REFERENCES tenants (id);
ALTER TABLE vehicles ADD CONSTRAINT fk_vehicles_tenants FOREIGN KEY (tenant_id) REFERENCES tenants (id);
ALTER TABLE order_service ADD CONSTRAINT fk_orders_tenants FOREIGN KEY (tenant_id) REFERENCES tenants (id);
ALTER TABLE itens ADD CONSTRAINT fk_itens_tenants FOREIGN KEY (tenant_id) REFERENCES tenants (id);
