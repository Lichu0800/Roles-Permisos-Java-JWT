# Proyecto de Autenticación y Autorización con Spring Security y JWT

Este proyecto implementa un sistema de autenticación y autorización basado en **Spring Boot**, **Spring Security**, **JWT (JSON Web Tokens)** y un modelo de **roles y permisos**.

---

## 🚀 Tecnologías Utilizadas
- **Java 17**
- **Maven 3.6+**
- **MySQL**

---

## ⚙️ Instalación y Configuración
### 1️⃣ Clonar el repositorio
```bash
git clone https://github.com/tu-usuario/tu-repositorio.git
cd tu-repositorio
```

### 2️⃣ Configurar la base de datos
Modifica `application.yml` o `application.properties` con los datos de tu base de datos.

```yml
# DATOS DE LA DB
spring.datasource.url=jdbc:mysql://localhost:3306/nombre_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=

# NOMBRE DE LA APP
spring.application.name=autenticacion
spring.security.user.name=${DB_USER}
spring.security.user.password=${DB_PASSWORD}

# Configuración de JWT
security.jwt.private.key=${PRIVATE_KEY}
security.jwt.user.generator=${USER_GENERATOR}
```

### 3️⃣ Ejecutar la aplicación
```bash
mvn spring-boot:run
```

---

## 📌 Endpoints Principales
### 🔐 Autenticación
- `POST /auth/login` → Iniciar sesión con credenciales de usuario.

### 👥 Usuarios
- `GET /api/users` → Obtener todos los usuarios.
- `GET /api/users/{id}` → Obtener un usuario por ID.
- `POST /api/users` → Crear un nuevo usuario.

### 🎭 Roles
- `GET /api/roles` → Obtener todos los roles.
- `GET /api/roles/{id}` → Obtener un rol por ID.
- `POST /api/roles` → Crear un nuevo rol.

### 🔑 Permisos
- `GET /api/permissions` → Obtener todos los permisos.
- `GET /api/permissions/{id}` → Obtener un permiso por ID.
- `POST /api/permissions` → Crear un nuevo permiso.

---

## 📂 Estructura del Proyecto
```
/src/main/java/com/lisandro/autenticacion/  # Código fuente principal.
/src/main/resources/                       # Archivos de configuración y recursos.
/src/test/java/com/lisandro/autenticacion/ # Pruebas unitarias.
```

---

## 🔒 Seguridad y Autorización
El sistema maneja **roles y permisos**, permitiendo restringir rutas según la jerarquía de usuarios.

---

## 🤝 Contribuir
Si deseas contribuir a este proyecto, puedes hacer un **fork**, crear una rama con tus cambios y enviar un pull request. 🙌

---

## 👤 Autor
💡 **Lichu** - [GitHub](https://github.com/Lichu0800?tab=repositories)

---

## 📜 Licencia
Este proyecto está bajo la licencia **MIT**.

