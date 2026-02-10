CREATE TABLE roles(
    role_id BIGSERIAL PRIMARY KEY ,
    title VARCHAR(100) NOT NULL
);

CREATE TABLE users(
    user_id BIGSERIAL PRIMARY KEY ,
    email VARCHAR(255) UNIQUE  NOT NULL ,
    password VARCHAR(100) NOT NULL ,
    role_id BIGINT,
    CONSTRAINT fk_role FOREIGN KEY (role_id)
                  REFERENCES roles(role_id) ON DELETE SET NULL
);

CREATE TABLE profiles(
    profile_id BIGSERIAL PRIMARY KEY ,
    full_name VARCHAR(100) NOT NULL ,
    user_id BIGINT UNIQUE NOT NULL ,
    created_at TIMESTAMP NOT NULL ,
    CONSTRAINT fk_user FOREIGN KEY (user_id)
                     REFERENCES users(user_id) ON DELETE CASCADE
);
