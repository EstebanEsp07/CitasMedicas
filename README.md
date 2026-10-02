# Citas Médicas (Codespaces)

## Levantar stack completo en Codespaces

Desde la raíz del repositorio:

```bash
docker compose up --build -d
```

Servicios expuestos:

- Frontend: `http://localhost:3000`
- Backend API: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- RabbitMQ Management: `http://localhost:15672` (usuario `admin`, clave `admin_secure_pass`)
- PostgreSQL: `localhost:5432` (`citas_medicas_db`, usuario `postgres`, clave `postgres_secure_pass`)

## Notas de inicialización

- El esquema de base de datos se gestiona con Flyway (`backend/src/main/resources/db/migration`).
- `docker/postgres/init.sql` no se monta en Compose para evitar conflictos de doble creación de esquema.
- RabbitMQ sí carga `docker/rabbitmq/definitions.json` al iniciar para crear exchange/colas/bindings de desarrollo.
