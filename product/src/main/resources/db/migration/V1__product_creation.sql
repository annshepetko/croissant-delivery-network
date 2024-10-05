create table category(
                         id SERIAL PRIMARY KEY,
                         name TEXT NOT NULL
);


CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL CHECK (char_length(name) >= 4),
    description VARCHAR(100) NOT NULL,
    price DECIMAL(19, 2) CHECK (price > 0),
    photo_url VARCHAR(255),
    category_id INT REFERENCES category(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);


