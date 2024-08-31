CREATE SEQUENCE ordered_products_seq START 1 INCREMENT 1;
CREATE SEQUENCE orders_seq START 1 INCREMENT 1;
CREATE SEQUENCE address_seq START 1 INCREMENT 1;


create table address
(
    id       BIGINT PRIMARY KEY DEFAULT nextval('address_seq'),
    city     TEXT NOT NULL,
    district TEXT NOT NULL,
    street   TEXT NOT NULL,
    house    TEXT NOT NULL,
    entrance TEXT NOT NULL,
    floor    INT  NOT NULL,
    flat     INT  NOT NULL

);
create table orders
(
    id                BIGINT PRIMARY KEY DEFAULT nextval('orders_seq'),
    created_at        TIMESTAMP          default now(),

    user_firstname    TEXT,
    user_lastname     TEXT,
    user_phone_number VARCHAR(10),
    total_price       DECIMAL,
    email             TEXT,
    address_id        BIGINT,
    CONSTRAINT address_fk FOREIGN KEY (address_id) REFERENCES address (id)
);

create table ordered_products
(
    id          BIGINT PRIMARY KEY DEFAULT nextval('ordered_products_seq'),
    name        TEXT,
    price       DECIMAL,
    category_id INT,
    amount      INT,
    product_id  BIGINT,
    order_id    BIGINT,
    CONSTRAINT order_fk FOREIGN KEY (order_id) REFERENCES orders (id)
);

