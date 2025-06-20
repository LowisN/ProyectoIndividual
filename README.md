# 📚 Sistema de Recomendaciones de Libros 📚

Este proyecto es una aplicación web desarrollada con Spring Boot que permite a los usuarios buscar y guardar sus libros favoritos, así como recibir recomendaciones basadas en sus preferencias.

## ✨ Características

- **🔐 Sistema de autenticación y autorización**
  - Registro de usuarios
  - Inicio de sesión
  - Roles de usuario y administrador
  - Perfiles de usuario personalizables

- **🔍 Catálogo y búsqueda de libros**
  - Búsqueda por título, autor y categoría
  - Visualización detallada de información de libros

- **❤️ Gestión de favoritos**
  - Guardar libros favoritos
  - Eliminar libros de favoritos
  - Ver lista de libros favoritos

- **⚙️ Panel de administración**
  - Gestión de usuarios
  - Asignación de roles
  - Visualización de datos del sistema

## 🛠️ Tecnologías utilizadas

- **⚙️ Backend**
  - Java 11+
  - Spring Boot
  - Spring Security
  - Spring Data JPA
  - MySQL

- **💻 Frontend**
  - Thymeleaf
  - HTML5
  - CSS3
  - JavaScript

## 📋 Requisitos previos

Para ejecutar esta aplicación, necesitas:

- ☕ Java JDK 11 o superior
- 🔧 Maven
- 🗃️ MySQL 8.0 o superior
- 🐳 Docker (opcional, para contenedorización)

## 🚀 Configuración y ejecución

### 💻 Ejecución local

1. **Clonar el repositorio**

```bash
git clone https://github.com/LowisN/ProyectoIndividual.git
cd ProyectoIndividual
```

2. **Configurar la base de datos**

Por defecto, la aplicación está configurada para conectarse a una base de datos MySQL:

```
spring.datasource.url=jdbc:mysql://localhost:3306/practica2?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=admin
spring.datasource.password=admin
```

Puedes modificar estos valores en el archivo `src/main/resources/application.properties`.

3. **Compilar y ejecutar la aplicación**

```bash
./mvnw clean package
./mvnw spring-boot:run
```

O en Windows:

```bash
mvnw.cmd clean package
mvnw.cmd spring-boot:run
```

4. **Acceder a la aplicación**

Una vez iniciada, la aplicación estará disponible en [http://localhost:5000](http://localhost:5000)

### 🐳 Ejecución con Docker

El proyecto incluye un `Dockerfile` y un archivo `docker-compose.yml` para facilitar la contenerización.

1. **Construir y levantar los contenedores**

```bash
docker-compose up -d
```

Este comando:
- Crea un contenedor MySQL para la base de datos
- Construye y lanza la aplicación Spring Boot

2. **Acceder a la aplicación**

La aplicación estará disponible en [http://localhost:5000](http://localhost:5000)

3. **Detener los contenedores**

```bash
docker-compose down
```

## 📁 Estructura del proyecto

```
ProyectoIndividual/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── mx/ipn/escom/Recomendaciones/
│   │   │       ├── RecomendacionesApplication.java
│   │   │       └── auth/
│   │   │           ├── config/
│   │   │           ├── controller/
│   │   │           ├── dto/
│   │   │           ├── entity/
│   │   │           ├── repository/
│   │   │           └── service/
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       ├── templates/
│   │       └── schema.sql
│   └── test/
└── pom.xml
```

## 📝 Uso del sistema

### 👤 Usuarios predeterminados

El sistema crea automáticamente un usuario administrador:

- **Usuario**: admin
- **Contraseña**: admin
- **Roles**: ROLE_ADMIN, ROLE_USER

### 🌟 Funcionalidades principales

1. **🔑 Registro e inicio de sesión**
   - Los nuevos usuarios pueden registrarse desde la página de registro
   - El acceso al sistema requiere autenticación

2. **👤 Perfil de usuario**
   - Los usuarios pueden cambiar su contraseña
   - Añadir/cambiar su imagen de perfil

3. **📚 Exploración de libros**
   - Búsqueda por diferentes criterios
   - Visualización de detalles de libros

4. **🛠️ Administración del sistema**
   - El panel de administración permite gestionar usuarios
   - Asignar roles específicos

## 👨‍💻 Contribuir

Si deseas contribuir a este proyecto:

1. 🍴 Haz un fork del repositorio
2. 🌿 Crea una rama para tu funcionalidad (`git checkout -b feature/nueva-funcionalidad`)
3. 💾 Haz commit de tus cambios (`git commit -m 'Añadir nueva funcionalidad'`)
4. 📤 Haz push a la rama (`git push origin feature/nueva-funcionalidad`)
5. 🔄 Abre un Pull Request

## 📄 Licencia

[MIT](https://choosealicense.com/licenses/mit/)
