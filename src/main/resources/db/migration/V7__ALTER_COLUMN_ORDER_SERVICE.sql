ALTER TABLE order_service
    ADD COLUMN quantity_itens INT NOT NULL;


ALTER TABLE order_service
    ADD COLUMN date_start DATE;


ALTER TABLE order_service
    ADD COLUMN date_end DATE;


ALTER TABLE order_service_detail_itens
    ADD COLUMN quantity_item INT NOT NULL;


ALTER TABLE order_service_detail_itens
    ADD COLUMN sale_amount_at DECIMAL NOT NULL;