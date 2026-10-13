CREATE DATABASE IF NOT EXISTS mercado_local CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE mercado_local;
CREATE TABLE IF NOT EXISTS products (
 id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 name VARCHAR(120) NOT NULL,
 description VARCHAR(500) NOT NULL DEFAULT '',
 price DECIMAL(12,2) NOT NULL,
 stock INT NOT NULL DEFAULT 0,
 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 CONSTRAINT chk_products_price CHECK(price >= 0),
 CONSTRAINT chk_products_stock CHECK(stock >= 0)
);
-- Reemplace la clave antes de ejecutar como administrador y otorgue permisos minimos:
-- CREATE USER 'mercado_app'@'localhost' IDENTIFIED BY 'CAMBIAR_POR_CLAVE_SEGURA';
-- GRANT SELECT, INSERT, UPDATE, DELETE ON mercado_local.products TO 'mercado_app'@'localhost';
-- FLUSH PRIVILEGES;
