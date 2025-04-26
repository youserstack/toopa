CREATE TABLE IF NOT EXISTS account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255), -- ✅ 추가
    role VARCHAR(255)
);

INSERT INTO account (name, email, password, role) VALUES ('mike', 'mike@gmail.com', '$2a$10$5lK/DG19HkUgFEuM2IUp9OGqMTGRHlGlyZzHtVhKgi1a7.ixIEkY2', 'tester');
