DROP TABLE IF EXISTS roles;
CREATE TABLE roles
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);


INSERT INTO roles (name) VALUES ('ROLE_ADMIN'), ('ROLE_USER'), ('ROLE_GUEST');


DROP TABLE IF EXISTS users_roles;
CREATE TABLE users_roles
(
    id SERIAL PRIMARY KEY,
    user_id BIGINT,
    role_id BIGINT
);
