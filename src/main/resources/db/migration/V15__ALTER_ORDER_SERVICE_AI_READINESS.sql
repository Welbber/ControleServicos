ALTER TABLE order_service
ADD COLUMN parts_cost DECIMAL,
ADD COLUMN labor_cost DECIMAL,
ADD COLUMN discount_amount DECIMAL,
ADD COLUMN customer_complaint TEXT,
ADD COLUMN inspection_notes TEXT,
ADD COLUMN ai_damage_report TEXT;

ALTER TABLE order_service_detail_itens
ADD COLUMN tenant_id UUID;

-- Backfill exiting records using the primary tenant (useful for early MVP environments)
UPDATE order_service_detail_itens
SET tenant_id = (SELECT id FROM tenants LIMIT 1)
WHERE tenant_id IS NULL;

-- Enforce the constraint
ALTER TABLE order_service_detail_itens
ALTER COLUMN tenant_id SET NOT NULL;
