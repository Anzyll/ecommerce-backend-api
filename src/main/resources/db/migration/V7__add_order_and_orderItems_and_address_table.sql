CREATE TABLE orders
(
    order_id     BIGSERIAL PRIMARY KEY,
    user_id      BIGINT         NOT NULL,
    status       VARCHAR(20)    NOT NULL,
    total_amount NUMERIC(10, 2) NOT NULL,
    created_at   TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_orders_user FOREIGN KEY (user_id)
    REFERENCES users(user_id)
    ON DELETE RESTRICT

);

CREATE INDEX idx_orders_userId ON orders(user_id);
CREATE INDEX idx_orders_status ON orders(status);

CREATE TABLE order_items (
        order_item_id BIGSERIAL PRIMARY KEY,
        order_id BIGINT NOT NULL,
        product_id BIGINT NOT NULL,
        quantity INT NOT NULL CHECK (quantity > 0),
        price NUMERIC(12, 2) NOT NULL,
        created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        CONSTRAINT fk_order_items_order
        FOREIGN KEY (order_id)
        REFERENCES orders (order_id)
        ON DELETE CASCADE,

        CONSTRAINT fk_order_items_product
        FOREIGN KEY (product_id)
        REFERENCES products (product_id)
        ON DELETE RESTRICT,

        CONSTRAINT uq_order_product
        UNIQUE (order_id, product_id)
);

CREATE INDEX idx_order_items_order_id ON order_items (order_id);
CREATE INDEX idx_order_items_product_id ON order_items (product_id);

CREATE TABLE addresses (
     address_id  BIGSERIAL PRIMARY KEY,
     user_id   BIGINT NOT NULL,
     full_name       VARCHAR(100) NOT NULL,
     phone_number    VARCHAR(20) NOT NULL,
     address   VARCHAR(255) NOT NULL,
     city VARCHAR(100) NOT NULL,
     state   VARCHAR(100) NOT NULL,
     postal_code VARCHAR(20) NOT NULL,
     country VARCHAR(100) NOT NULL,
     is_default BOOLEAN NOT NULL DEFAULT FALSE,
     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP,

    CONSTRAINT fk_addresses_user
    FOREIGN KEY (user_id)
    REFERENCES users (user_id)
     ON DELETE CASCADE
);

CREATE INDEX idx_addresses_user_id ON addresses (user_id);
CREATE INDEX idx_addresses_default ON addresses (user_id, is_default);
