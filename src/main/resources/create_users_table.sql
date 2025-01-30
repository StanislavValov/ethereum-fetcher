CREATE TABLE users
(
    id       UUID PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255)
);

INSERT INTO users (username, password)
VALUES ('alice', '$2a$10$z33RVM3PS2qQrUFv6AW1PeRzo7C24CgF4tBeF27bnipB0ghMjyLr2'),
       ('carol', '$2a$10$iDqZ2N0tMSCi2vOVP2yDzOk/cyWKo/idsuZbbLnj.48p3Rcihwo0e'),
       ('dave', '$2a$10$R/0YZlSFKED/wU6Q4XLwLe7uF3OvjftGvNK4Bv.Z/BpWznGNJzPw2'),
       ('bob', '$2a$10$D9mrC1MAx/5QZjVzsaq4iudl.fuGjPwBoWs1dIsW3c1d91QwRjUXi');