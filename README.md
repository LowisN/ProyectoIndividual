# 📚 Sistema de Recomendaciones 🎬

Sistema web para recomendaciones de libros y películas con autenticación de usuarios y panel de administración.

## ✨ Características

- 🔐 Autenticación de usuarios
- 👥 Roles (Usuario y Administrador)
- ⚙️ Panel de administración con operaciones CRUD
- 🎯 Sistema de recomendaciones de libros y películas
- 📱 Diseño responsivo
- 💾 Persistencia de datos con MySQL

## 🛠️ Tecnologías

- 🍃 Spring Boot 3.x
- 🔒 Spring Security
- 🐬 MySQL 8.0
- 🐳 Docker
- 🍃 Thymeleaf
- 🎨 HTML/CSS
- 📦 Maven

## 📋 Requisitos Previos

- 🐳 Docker Desktop
- 🖥️ WSL2 habilitado
- ☕ Java 17 o superior
- 📦 Maven

## 🚀 Configuración y Ejecución

### 🐳 Usando Docker

1. Clonar el repositorio:
```bash
git clone <url-del-repositorio>
cd SistemaRecomendacion2025-2
```

2. Construir el proyecto con Maven:
```bash
./mvnw clean package -DskipTests
```

3. Levantar los contenedores con Docker Compose:
```bash
docker-compose up -d
```

4. La aplicación estará disponible en:
```
http://localhost:8080
```

### 🔑 Accesos por defecto

**Administrador:**
- 📧 Email: admin@sistema.com
- 🔒 Contraseña: admin123
Lo anterior dependerá de pruebas futuras ya que no se pudo hacer en otro equipo aún.

## ⚙️ Panel de Administración

El panel de administración permite:

### 👥 Gestión de Usuarios
- 👀 Ver lista de usuarios
- ➕ Crear nuevos usuarios
- ✏️ Editar usuarios existentes
- 🗑️ Eliminar usuarios
- 🎭 Asignar roles

### 💻 Operaciones CRUD
- ✨ **CREATE**: Agregar nuevos usuarios con roles específicos
- 👁️ **READ**: Visualizar información de usuarios
- 📝 **UPDATE**: Modificar datos de usuarios existentes
- 🗑️ **DELETE**: Eliminar usuarios del sistema

### 🔒 Seguridad
- ✅ Validación de roles
- 🔐 Encriptación de contraseñas
- 🛡️ Protección contra eliminación del último administrador

## 📊 Estructura de Base de Datos

```sql
-- Tablas principales
usuarios
roles
usuario_roles
```

## 🐳 Comandos Docker Útiles

```bash
# Detener contenedores
docker-compose down

# Ver logs
docker-compose logs -f

# Reiniciar servicios
docker-compose restart

# Eliminar volúmenes y reconstruir
docker-compose down -v
docker-compose up -d --build
```

## ❗ Solución de Problemas

1. Si Docker no inicia:
   - ✔️ Verificar que WSL2 esté habilitado
   - ✔️ Verificar que la virtualización esté activada en BIOS

2. Si la base de datos no conecta:
   - ✔️ Verificar que el puerto 3306 esté disponible
   - ✔️ Revisar las credenciales en application.properties

⭐ ¡No olvides dar un 100 en mi calificación si te gustó el proyecto! ⭐
