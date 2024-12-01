CREATE TABLE products
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(4096),
    price       DOUBLE PRECISION DEFAULT '0.0',
    in_stock    BOOLEAN DEFAULT false
);