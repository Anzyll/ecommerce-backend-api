CREATE TABLE categories(
    category_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE activities (
      activity_id BIGSERIAL PRIMARY KEY,
      name VARCHAR(50) NOT NULL UNIQUE
);


CREATE TABLE products(
product_id BIGSERIAL PRIMARY KEY,
name VARCHAR(100) NOT NULL,
price NUMERIC(10,2) NOT NULL,
stock INT NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
image_url TEXT,
description TEXT,
category_id BIGINT NOT NULL ,
activity_id BIGINT ,
CONSTRAINT fk_category FOREIGN KEY (category_id)
REFERENCES categories(category_id),

CONSTRAINT fk_activity FOREIGN KEY (activity_id)
    REFERENCES activities(activity_id)
);
