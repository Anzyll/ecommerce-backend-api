CREATE  TABLE cart(
    cart_id BIGSERIAL PRIMARY KEY ,
    user_id BIGINT NOT NULL ,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(15) NOT NULL DEFAULT 'ACTIVE',
    CONSTRAINT fk_cart_user FOREIGN KEY (user_id)
                  REFERENCES users(user_id)
                  ON DELETE CASCADE
);

CREATE TABLE cart_items(
    id BIGSERIAL PRIMARY KEY ,
    cart_id BIGINT NOT NULL ,
    product_id BIGINT NOT NULL ,
    quantity INT NOT NULL CHECK ( quantity>0 ),
    CONSTRAINT fk_cart_item_cart FOREIGN KEY (cart_id)
                       REFERENCES cart(cart_id)
                       ON DELETE  CASCADE ,
    CONSTRAINT fk_cart_item_product FOREIGN KEY (product_id)
                       REFERENCES products(product_id)
                       ON DELETE RESTRICT ,
    CONSTRAINT uq_cart_item_product
                       UNIQUE (cart_id,product_id)

);

CREATE INDEX idx_cart_user_active ON
cart(user_id,status);

CREATE INDEX idx_cart_item_cart ON
cart_items(cart_id);

