CREATE TABLE IF NOT EXISTS itens(
                                    id SERIAL NOT NULL,
                                    code VARCHAR(100),
    description VARCHAR(255) NOT NULL,
    type    VARCHAR(50) NOT NULL,
    provider    VARCHAR(150),
    purchase_amount DECIMAL,
    sale_amount     DECIMAL,
    unit_measurement VARCHAR(50),
    unit_stock      INT,
    created_at      TIMESTAMP with time zone,
    updated_at      TIMESTAMP with time zone,
                                  CONSTRAINT pk_itens PRIMARY KEY (id)
    );

CREATE INDEX IF NOT EXISTS itens_index_code_and_description ON itens(code, description);
