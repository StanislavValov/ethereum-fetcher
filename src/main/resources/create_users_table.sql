CREATE TABLE users
(
    id                 UUID PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
    username           VARCHAR(255),
    password           VARCHAR(255),
);
