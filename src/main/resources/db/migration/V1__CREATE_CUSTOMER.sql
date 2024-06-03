CREATE TABLE IF NOT EXISTS  customers
(
    id              SERIAL       NOT NULL,
    name            VARCHAR(255) NOT NULL,
    document_number VARCHAR(14)  NOT NULL,
    email           VARCHAR(255) NOT NULL,
    phone_number    VARCHAR(11)  NOT NULL,
    active          BOOLEAN DEFAULT TRUE,
    created_at      TIMESTAMP with time zone,
    updated_at      TIMESTAMP with time zone,
    CONSTRAINT pk_customers PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS customers_index_document_number ON customers (document_number);
CREATE INDEX IF NOT EXISTS customers_index_name_email ON customers (name, email);

CREATE TABLE IF NOT EXISTS address
(
    id          SERIAL     NOT NULL,
    customer_id INT,
    street_name VARCHAR(255),
    number      INT        NOT NULL,
    district    VARCHAR(255),
    zip_code    VARCHAR(9) NOT NULL,
    complements TEXT,
    city        VARCHAR(100),
    state       VARCHAR(50),
    created_at  TIMESTAMP with time zone,
    updated_at  TIMESTAMP with time zone,
    CONSTRAINT pk_address PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES customers (id)
);
