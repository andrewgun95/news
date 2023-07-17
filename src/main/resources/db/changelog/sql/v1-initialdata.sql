INSERT INTO roles (id, name) VALUES
(
    1001, 'ROLE_ADMIN'
),
(
    1002, 'ROLE_USER'
);

INSERT INTO users (id, name, password, email, birth, active) VALUES
(
    1001, 'admin', '$2a$12$RvcL0lZw6Q.L1aRfTPTD4evqJlCzhjiUzHcTXLiamTlGUDbe2rvyK', 'admin@yopmail.com', '2023-01-01', true
);

INSERT INTO user_roles (user_id, role_id) VALUES
(1001, 1001),
(1001, 1002);