
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(15) NOT NULL CHECK (char_length(name) >= 4),
    description VARCHAR(100) NOT NULL,
    price DECIMAL(19, 2) CHECK (price > 0),
    photo_url VARCHAR(255),
    category_id BIGINT REFERENCES category(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

create table category(
                         id BIGSERIAL PRIMARY KEY,
                         name TEXT NOT NULL
)