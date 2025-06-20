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

-- Crear la tabla de libros favoritos
CREATE TABLE IF NOT EXISTS libros_favoritos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    libro_id VARCHAR(255) NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255),
    imagen_url VARCHAR(512),
    fecha_agregado TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    UNIQUE KEY unique_usuario_libro (usuario_id, libro_id)
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
VALUES ('Administrador', 'admin@sistema.com', '$2a$10$WKfv4GaTzIODpaNHoS1.EO81pQ9bDkPR.BYG3EnF6fFBAAcrWlhXO')
ON DUPLICATE KEY UPDATE email=email;

-- Asignar rol de administrador al usuario por defecto
INSERT INTO usuario_roles (usuario_id, rol_id)
SELECT u.id, r.id 
FROM usuarios u, roles r 
WHERE u.email = 'admin@sistema.com' 
AND r.nombre = 'ROLE_ADMIN'
ON DUPLICATE KEY UPDATE usuario_id=usuario_id;

-- Asignar rol de panel admin a los administradores existentes
INSERT INTO usuario_roles (usuario_id, rol_id)
SELECT u.id, r.id 
FROM usuarios u
JOIN usuario_roles ur ON u.id = ur.usuario_id
JOIN roles r_admin ON r_admin.id = ur.rol_id
JOIN roles r ON r.nombre = 'ROLE_PANEL_ADMIN'
WHERE r_admin.nombre = 'ROLE_ADMIN'
ON DUPLICATE KEY UPDATE usuario_id=usuario_id;

-- Asignar automáticamente el rol de usuario (ROLE_USER) a cualquier nuevo usuario registrado
DELIMITER //
CREATE TRIGGER IF NOT EXISTS asignar_rol_usuario_after_insert 
AFTER INSERT ON usuarios
FOR EACH ROW
BEGIN
    INSERT INTO usuario_roles (usuario_id, rol_id)
    SELECT NEW.id, r.id
    FROM roles r
    WHERE r.nombre = 'ROLE_USER';
END;
//
DELIMITER ;