# API de Gestión de Franquicias - Prueba Técnica Backend

Solución para la prueba técnica de desarrollo backend. La API permite gestionar franquicias, sus sucursales asociadas y los productos ofertados en cada una, incluyendo la funcionalidad principal de consultar el producto con mayor stock por sucursal.

## Tecnologías
- Java 25
- Spring Boot 4
- Spring Data JPA
- PostgreSQL / H2 (en memoria para pruebas locales rápidas)
- Docker & Docker Compose
- SpringDoc OpenAPI (Swagger UI)
- Gradle

## Decisiones de Arquitectura y Diseño
Para esta solución implementé una **Arquitectura Hexagonal (Puertos y Adaptadores)**:

- **Dominio Aislado**: Las entidades (`Franchise`, `Branch`, `Product`), los casos de uso (`ports.in`) y los puertos hacia persistencia (`ports.out`) están completamente desacoplados del framework y librerías externas.
- **Transacciones desacopladas con Patrón Decorator**: Para mantener el servicio de dominio puro (sin `@Transactional`), la transaccionalidad se maneja en la capa de infraestructura mediante decoradores (`TransactionalFranchiseCommandDecorator` para escritura y `ReadOnlyFranchiseQueryDecorator` con `readOnly = true` para optimizar lecturas).
- **Mapeo explícito**: Las entidades de persistencia JPA (`*JpaEntity`) se mantienen separadas de los modelos de dominio mediante mappers dedicados.
- **Manejo global de excepciones**: Se implementó `GlobalExceptionHandler` con `ProblemDetail` (RFC 7807) para estandarizar las respuestas de error ante recursos no encontrados (404) o validaciones de negocio (400).

---

## Cómo ejecutar el proyecto

### 1. Con Docker Compose (Recomendado - API + PostgreSQL)
Para levantar la solución completa con la base de datos PostgreSQL:

```bash
docker compose up --build
```

Esto iniciará:
- PostgreSQL 16 en el puerto `5432` (base de datos: `franchises_db`).
- La API de Spring Boot en el puerto `8080`.

Para detener los servicios:
```bash
docker compose down
```

### 2. Con Gradle en local (H2 en memoria con datos de prueba)
Si prefieres correr la aplicación directamente sin levantar Docker:

```bash
./gradlew bootRun
```
La aplicación iniciará en `http://localhost:8080` utilizando H2 en memoria con compatibilidad PostgreSQL.
- **Datos iniciales automáticos**: Al iniciar en local, el archivo `data.sql` precarga automáticamente 3 franquicias (Juan Valdez, El Corral y Frisby) con sus respectivas sucursales y productos con diferentes niveles de stock, para que puedas probar las consultas de inmediato sin necesidad de crear registros manualmente.
- Consola H2: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:franchises;MODE=PostgreSQL`
  - Usuario: `sa`
  - Contraseña: *(vacía)*

---

## Cómo probar la API

Tienes 3 alternativas listas para usar:

1. **Swagger UI (Navegador)**:
   - [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) (con botón *Try it out* para cada endpoint).
2. **Archivo `requests.http` (IntelliJ IDEA / VS Code)**:
   - Ubicado en la raíz del proyecto. Puedes ejecutar cada petición directamente con el botón de reproducción del IDE.
3. **Colección de Postman**:
   - Archivo `franchises.postman_collection.json` en la raíz. Solo impórtalo en Postman y todas las peticiones estarán organizadas y listas para disparar con la variable `{{baseUrl}}`.

---

## Endpoints de la API

### Criterios de Aceptación y Puntos Extra
| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/v1/franchises` | Crear una nueva franquicia |
| POST | `/api/v1/franchises/{franchiseId}/branches` | Agregar una sucursal a la franquicia |
| POST | `/api/v1/branches/{branchId}/products` | Agregar un producto a la sucursal |
| DELETE | `/api/v1/branches/{branchId}/products/{productId}` | Eliminar un producto de una sucursal |
| PATCH | `/api/v1/products/{productId}/stock` | Modificar el stock de un producto |
| GET | `/api/v1/franchises/{franchiseId}/max-stock-products` | Consultar el producto con más stock por sucursal |
| PATCH | `/api/v1/franchises/{franchiseId}/name` | Actualizar el nombre de una franquicia (Plus) |
| PATCH | `/api/v1/branches/{branchId}/name` | Actualizar el nombre de una sucursal (Plus) |
| PATCH | `/api/v1/products/{productId}/name` | Actualizar el nombre de un producto (Plus) |

### Endpoints de Consulta (Soporte para Pruebas)
Para poder verificar visualmente las entidades creadas y su estado:
| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/v1/franchises` | Listar todas las franquicias |
| GET | `/api/v1/franchises/{franchiseId}` | Ver detalle de una franquicia |
| GET | `/api/v1/franchises/{franchiseId}/branches` | Listar sucursales de una franquicia |
| GET | `/api/v1/branches/{branchId}/products` | Listar productos de una sucursal |
