
CREATE TABLE IF NOT EXISTS  order_service
(
    id              UUID  NOT NULL DEFAULT gen_random_uuid(),
    description             TEXT,
    order_service_type      VARCHAR(50) NOT NULL,
    status                  VARCHAR(50) NOT NULL,
    customer_id      UUID NOT NULL,
    vehicle_id       UUID NOT NULL,
    amount           DECIMAL,
    km_vehicle_at    INT NOT NULL,
    created_at       TIMESTAMP with time zone,
    updated_at       TIMESTAMP with time zone,
    CONSTRAINT pk_order_service PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES customers (id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicles (id)
);

CREATE INDEX IF NOT EXISTS ordern_servive_index_descriptin_and_status ON order_service (id, description, status);
CREATE INDEX IF NOT EXISTS ordern_servive_index_customer ON order_service (id, customer_id);

CREATE TABLE IF NOT EXISTS order_service_detail_itens(
  id UUID NOT NULL DEFAULT gen_random_uuid(),
  item_id UUID NOT NULL,
  order_service_id UUID NOT NULL,
  CONSTRAINT pk_order_service_detal_itens PRIMARY KEY (id),
  FOREIGN KEY (order_service_id) REFERENCES order_service(id),
  FOREIGN KEY (item_id) REFERENCES itens (id)
);

