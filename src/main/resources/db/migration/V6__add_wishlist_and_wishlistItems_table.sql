CREATE TABLE wishlist(
        wishlist_id BIGSERIAL PRIMARY KEY ,
        user_id BIGINT NOT NULL ,
        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        CONSTRAINT fk_cart_user FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE
);

CREATE TABLE wishlist_items(
         id BIGSERIAL PRIMARY KEY ,
         wishlist_id BIGINT NOT NULL ,
         product_id BIGINT NOT NULL ,
         CONSTRAINT fk_wishlist_item_wishlist FOREIGN KEY (wishlist_id)
         REFERENCES wishlist(wishlist_id)
         ON DELETE  CASCADE ,
         CONSTRAINT fk_wishlist_item_product FOREIGN KEY (product_id)
         REFERENCES products(product_id)
         ON DELETE RESTRICT ,
         CONSTRAINT uq_wishlist_item_product
         UNIQUE (wishlist_id,product_id)
);

CREATE INDEX idx_wishlist_user_id ON
wishlist(user_id);

CREATE INDEX idx_wishlist_item_wishlist_id ON
wishlist_items(wishlist_id);
