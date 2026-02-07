CREATE TABLE roles(
    role_id SERIAL PRIMARY KEY ,
    title VARCHAR(100)
);

CREATE TABLE users(
    user_id SERIAL PRIMARY KEY ,
    email VARCHAR(255) UNIQUE  NOT NULL ,
    hashed_password VARCHAR(100),
    created_at TIMESTAMP NOT NULL ,
    role_id INT,
    CONSTRAINT fk_role FOREIGN KEY (role_id)
                  REFERENCES roles(role_id) ON DELETE SET NULL
);


CREATE TABLE profiles(
    profile_id SERIAL PRIMARY KEY ,
    full_name VARCHAR(100) NOT NULL ,
    phone VARCHAR(100),
    user_id INT UNIQUE ,
    CONSTRAINT fk_user FOREIGN KEY (user_id)
                     REFERENCES users(user_id) ON DELETE CASCADE
);
