DROP TABLE IF EXISTS products;
CREATE TABLE products
(
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    price BIGINT NOT NULL
);


INSERT INTO products (title, description, price)
VALUES
('MacBook', 'Ultra low and Great Power', 5000),
('iPhone', 'The most expensive phone by credit', 4000),
('iPad', 'More size - more cost', 1500),
('iMac', 'More size - more cost', 4000),
('Galaxy Z', 'New form - new rules', 2500),
('Galaxy S', 'Latest model of galaxy', 2000),
('Galaxy Note', 'More size - more cost!', 1500),
('Wireless Charger', 'Free hands!', 500);