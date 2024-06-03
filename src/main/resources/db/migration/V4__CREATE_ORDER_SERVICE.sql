
CREATE TABLE IF NOT EXISTS  order_service
(
    id              SERIAL  NOT NULL,
    description             TEXT,
    order_service_type      VARCHAR(50) NOT NULL,
    status                  VARCHAR(50) NOT NULL,
    created_at      TIMESTAMP with time zone,
    updated_at      TIMESTAMP with time zone,
    CONSTRAINT pk_order_service PRIMARY KEY (id)
);


CREATE  TABLE  IF NOT EXISTS order_service_detail
(
    id  SERIAL       NOT NULL,
    customer_id      INT NOT NULL,
    vehicle_id       INT NOT NULL,
    order_service_id INT NOT NULL,
    amount           DECIMAL,
    km_vehicle_at    INT NOT NULL,
    CONSTRAINT pk_order_service_detail PRIMARY KEY (id),
    FOREIGN KEY (order_service_id) REFERENCES order_service (id),
    FOREIGN KEY (customer_id) REFERENCES customers (id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicles (id)
);


CREATE INDEX IF NOT EXISTS ordern_servive_details_index_customer ON order_service_detail (id,customer_id,order_service_id);

CREATE TABLE IF NOT EXISTS itens(
  id SERIAL NOT NULL,
  code VARCHAR(100),
  description VARCHAR(255) NOT NULL,
  category    VARCHAR(50) NOT NULL,
  provider    VARCHAR(150),
  purchase_amount DECIMAL,
  sale_amount     DECIMAL,
  unit_measurement VARCHAR(50),
  unit_stock       DECIMAL,
  CONSTRAINT pk_itens PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS order_service_detal_itens(
  id SERIAL NOT NULL,
  item_id INT NOT NULL,
  order_service_detail_id INT NOT NULL,
  CONSTRAINT pk_order_service_detal_itens PRIMARY KEY (id),
  FOREIGN KEY (order_service_detail_id) REFERENCES order_service_detail (id),
  FOREIGN KEY (item_id) REFERENCES itens (id)
);

