CREATE SCHEMA IF NOT EXISTS products;
DROP TABLE IF EXISTS products.list;

CREATE TABLE products.list (
    id INT IDENTITY PRIMARY KEY,
    name VARCHAR(256),
    price INT
);