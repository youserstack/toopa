





CREATE TABLE IF NOT EXISTS user_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);

INSERT INTO user_entity (email, name, password, role)
VALUES ('mike@gmail.com', 'mike', '$2a$10$VvrDaLREEwBgkoxE8lRciuTjnTkawKnfGYGVTnVkds7QtHEbLhp3i', 'USER');

