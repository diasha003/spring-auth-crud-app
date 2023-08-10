DROP TABLE IF EXISTS users_products;
CREATE TABLE users_products
(
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL
);

INSERT INTO users_products (user_id, product_id)
VALUES (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (3, 5);