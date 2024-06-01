ALTER TABLE address
RENAME COLUMN street_name to street;

CREATE TABLE IF NOT EXISTS  vehicles
(
    id              SERIAL       NOT NULL,
    customer_id     INT NOT NULL,
    brand           VARCHAR(255),
    license_plate   VARCHAR(8)  NOT NULL UNIQUE,
    model           VARCHAR(255),
    color           VARCHAR(30),
    year             INT,
    created_at      TIMESTAMP with time zone,
    updated_at      TIMESTAMP with time zone,
    CONSTRAINT pk_vehicles PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES customers (id)
);

CREATE INDEX IF NOT EXISTS vehicles_index_plate ON vehicles (license_plate);
