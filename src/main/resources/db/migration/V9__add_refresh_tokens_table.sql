CREATE TABLE refresh_tokens (
        id BIGSERIAL PRIMARY KEY,
        token VARCHAR(255) NOT NULL UNIQUE,
        expiry_date TIMESTAMP NOT NULL,
        user_id BIGINT NOT NULL,

       CONSTRAINT fk_refresh_token_user
       FOREIGN KEY (user_id)
       REFERENCES users(user_id)
       ON DELETE CASCADE
);
