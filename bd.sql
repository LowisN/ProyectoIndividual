-- Crear la base de datos "practica2" con codificación UTF-8
CREATE DATABASE IF NOT EXISTS practica2 CHARACTER SET utf8 COLLATE utf8_general_ci;

-- Usar la base de datos "practica2"
USE practica2;

-- Crear la tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(64) NOT NULL,
    email VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    imagen LONGBLOB
);

-- Crear la tabla de roles
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(64) NOT NULL UNIQUE
);

-- Crear la tabla intermedia para la relación muchos a muchos entre usuarios y roles
CREATE TABLE IF NOT EXISTS usuario_roles (
    usuario_id BIGINT,
    rol_id BIGINT,
    PRIMARY KEY (usuario_id, rol_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (rol_id) REFERENCES roles(id)
);

-- Insertar roles en la tabla roles
INSERT INTO roles (nombre) 
VALUES ('ROLE_ADMIN'), ('ROLE_USER')
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

-- Insertar un usuario administrador por defecto
-- password: admin123 (BCrypt encoded)
INSERT INTO usuarios (nombre, email, password) 
VALUES ('Administrador', 'admin@sistema.com', '$2a$10$kYp6/7OOF0g4L8sZGX5J8.iXjwjdP0H9H2RnZIgM9r1HYU1DGfQVe')
ON DUPLICATE KEY UPDATE email=email;

-- Asignar rol de administrador al usuario por defecto
INSERT INTO usuario_roles (usuario_id, rol_id)
SELECT u.id, r.id 
FROM usuarios u, roles r 
WHERE u.email = 'admin@sistema.com' 
AND r.nombre = 'ROLE_ADMIN'
ON DUPLICATE KEY UPDATE usuario_id=usuario_id;