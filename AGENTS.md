# AGENTS.md · ETEREA.multitenant-core-service

## Rol del servicio

Core multi-tenant de la plataforma ETEREA. Expone dos mundos bien separados:

```text
/api/brain/core/**     datos globales de la plataforma (base maestra eterea-brain)
/api/tenant/core/**    datos de un negocio, con routing a su base por X-TENANT-ID
```

Implementa arquitectura hexagonal y modular (Spring Modulith) con migración
progresiva del código legacy. La base maestra guarda empresas, negocios,
usuarios y asignaciones; cada negocio tiene su propia base de datos operativa.

### Relación con el sistema legacy

El sistema VB6/legacy es **solo una referencia funcional**. No comparte
infraestructura, base de datos ni contratos HTTP con este servicio y no va a
consumir esta API. Por lo tanto:

- No hay que preservar endpoints, nombres de campos, JSON ni IDs del legacy.
- Las decisiones de diseño se toman por la arquitectura de este proyecto, no
  por compatibilidad con el legacy.
- Los únicos consumidores a cuidar son el gateway y las apps Angular de este
  workspace (`frontend/`).

## Stack

- Java 25
- Spring Boot 4.1.2
- Spring Cloud 2025.1.4
- Spring Data JPA + Hibernate
- Spring Security 7: JWT resource server, Argon2, roles y lockout de login
- Spring Web + WebFlux, Validation, HATEOAS
- MySQL (`mysql-connector-j` 26.10.0), H2 para tests
- Consul Discovery + OpenFeign
- Lombok, ModelMapper, Caffeine, OpenPDF, ZXing
- Maven (no hay `mvnw` en el repo)

## Comandos

```bash
# Compilar y testear
mvn -B verify

# Empaquetar sin tests
mvn -DskipTests package

# Ejecutar en local (revisar variables; los defaults apuntan a una DB concreta)
APP_PORT=8080 \
APP_SERVER=localhost:3306 \
APP_DATABASE=eterea-brain \
APP_USER=root \
APP_PASSWORD=... \
APP_CONSUL_HOST=localhost \
APP_CONSUL_PORT=8500 \
APP_JWT_SECRET='cambiar-por-un-secreto-de-32-bytes-minimo' \
APP_JWT_ISSUER=eterea.multitenant \
mvn spring-boot:run

# Un test puntual
mvn -Dtest=NombreDelTest test

# Docker
docker build -t eterea/multitenant-core-service .
```

> Los tests de contexto (`ApplicationTests`) levantan Spring. Los tests de casos
> de uso y adaptadores son unitarios. Si el entorno no tiene MySQL/Consul,
> verificar al menos `mvn -DskipTests package` y ejecutar los tests unitarios
> puntuales.

## Configuración

`src/main/resources/bootstrap.yml`:

- `APP_PORT`: puerto (default `8080`).
- `APP_SERVER`, `APP_DATABASE`, `APP_USER`, `APP_PASSWORD`: conexión a la base
  maestra (en compose, `eterea-brain`).
- `APP_CONSUL_HOST`, `APP_CONSUL_PORT`: Consul.
- `APP_LOGGING`: nivel de logs.
- `APP_JWT_SECRET` / `APP_JWT_ISSUER`: firma y emisor JWT; deben coincidir con el
  gateway.
- `APP_ACCESS_TOKEN_TTL` (15m), `APP_REFRESH_TOKEN_TTL` (7d),
  `APP_SECURITY_COOKIE_SECURE`, `APP_SECURITY_MAX_FAILED_ATTEMPTS`,
  `APP_SECURITY_LOCK_DURATION` y `APP_SECURITY_TEMPORARY_PASSWORD_TTL` (24h por
  defecto).
- `APP_DB_INIT_MODE` (default `always`): ejecuta
  `db/refresh-token-schema.sql`, que crea `usuario_refresh_token` de forma
  idempotente. En producción se puede usar `never` si la tabla ya fue migrada.
- `APP_TENANT_DB_*`: tamaño y ciclo de vida de los pools por tenant. Ver la
  sección Multi-tenancy.
- `spring.jpa.hibernate.ddl-auto: none`: el esquema se administra fuera de la app.
- `spring.jpa.open-in-view: false`.

`src/test/resources/bootstrap.yml` deshabilita Consul y define un secreto JWT de
test.

## Arquitectura y paquetes

```text
src/main/java/eterea/tenant/service/
├── brain/                  # CRUD global sobre eterea-brain
│   ├── auth/               # login JWT, refresh tokens y cookies
│   ├── empresa/
│   ├── negocio/
│   └── usuarios/
│       ├── usuario/
│       ├── usuario_empresa/
│       └── usuario_negocio/
├── modules/                # módulos de negocio (Spring Modulith)
│   ├── platform/           # negocio actual, clientes, usuarios y contratos
│   ├── compras/            # proveedores
│   ├── contable/           # cuenta, asientos, ejercicios y reportes contables
│   ├── reporte/            # esqueleto transversal de reportes (modelo + PDF)
│   ├── stock/              # artículos, productos, inventario y saldos
│   ├── ventas/             # comprobantes, vouchers y facturación
│   ├── reserva/            # reservas y hotelería
│   ├── tesoreria/          # caja, cierre y transferencias
│   └── personal/           # legajo
├── configuration/          # datasource routing, interceptor, seguridad
├── tool/                   # utilidades transversales
└── Application.java
```

### Reportes

- `modules/reporte` es transversal: `ReporteTabla` (cabecera, columnas y filas)
  es la única fuente de verdad para el JSON de vista previa y el PDF.
- El PDF se renderiza con OpenPDF (A4, cabecera repetida, `Página X de Y`,
  wrapping de celdas). El renderer se genera en memoria y nunca escribe un PDF
  parcial ante un error.
- Cada reporte vive en su módulo de negocio: un caso de uso arma el
  `ReporteTabla`, un controller expone `POST /api/tenant/core/reporte/<slug>/datos`
  y `/pdf`, y reutiliza `ReporteDtoMapper`/`ReportePdfService`.
- Fechas visibles en reportes y PDF: `dd-MM-yyyy`.
- Referencia implementada: `contable/cuenta/reporte` (listado del plan de
  cuentas).

Cada slice hexagonal sigue:

```text
domain/model          modelos de dominio puros
domain/ports/in       casos de uso de entrada
domain/ports/out      puertos de salida (repositorios, hashing, etc.)
application/service   orquestación
application/usecases  implementaciones de casos de uso
infrastructure/web    controllers, DTOs y mappers
infrastructure/persistence  entidades JPA, repositorios y adaptadores
```

### Regla de dependencia

El dominio **no** depende de Spring, JPA, DTOs ni HTTP. Los adaptadores de
infraestructura dependen del dominio y de sus puertos. No importar clases de
`infrastructure` desde `domain` ni desde `application`.

## Módulos

El core es un solo deployable. La separación de módulos es de código, no de
infraestructura: no se crean servicios ni repositorios de GitHub por módulo.

- Los módulos viven en `eterea.tenant.service.modules.<nombre>`.
- Un módulo **no** importa paquetes internos de otro; solo sus `api` anotados
  con `@NamedInterface("api")`.
- Contratos existentes: `modules.platform.api.NegocioActual`,
  `modules.contable.api.CuentaQuery` / `CuentaResumen` y los paquetes `api`
  compartidos de cada módulo.
- No duplicar entidades JPA entre módulos: guardar la FK y resolver el dato por
  el `api` del módulo dueño.
- El kernel (`configuration`, `tool`) no depende de módulos.
- `Auditable` vive en `tool` (kernel).
- Los módulos están cerrados: `ModularityTests` verifica ciclos y accesos a
  internals en cada build.
- Los controladores van bajo `/api/tenant/**`; el `TenantInterceptor` resuelve
  el tenant por path.
- Correr `mvn -Dtest=ModularityTests test` al mover clases entre módulos.
- Guía completa: [`docs/modules.md`](docs/modules.md).

## Multi-tenancy

- Header `X-TENANT-ID` obligatorio para las rutas bajo `/api/tenant/**`.
- El valor es `negocio.tenantId`, **no** `empresaId`.
- `TenantInterceptor` valida el header y llama a `TenantDataSourceService`.
- `TenantDataSourceService` busca el negocio por `tenantId` en la base maestra y
  crea un `HikariDataSource` por tenant, con pool chico y bajo demanda.
- `TenantDataSourceRegistry` es el dueño del ciclo de vida: cuenta los requests
  en curso, cierra el pool cuando el tenant lleva `APP_TENANT_DB_EVICTION_AFTER`
  sin uso y mantiene como máximo `APP_TENANT_DB_MAX_LIVE_POOLS` pools vivos.
- Los pools se configuran con `APP_TENANT_DB_POOL_MAX` (8), `APP_TENANT_DB_POOL_MIN`
  (1), `APP_TENANT_DB_IDLE_TIMEOUT` (60s), `APP_TENANT_DB_MAX_LIFETIME` (20m),
  `APP_TENANT_DB_CONNECTION_TIMEOUT` (5s) y `APP_TENANT_DB_VALIDATION_TIMEOUT` (3s).
- Métricas Micrometer: `eterea.tenant.datasource.pools`, `.requests`,
  `.connections`, `.pools.created` y `.pools.evicted`.
- `TenantInterceptor` adquiere el pool al entrar y lo libera en
  `afterCompletion`. También reconoce `/api/tenant/**` por path, además del
  paquete `eterea.tenant.service.tenant`.
- `TenantRoutingDataSource` y `TenantContext` resuelven la base en cada request.
- Los controladores de `brain` **no** pasan por el interceptor de tenant y usan
  la base maestra.
- No consultar bases de tenant desde un controller de `brain` ni al revés.
- No hardcodear `tenantId` en código de producción; siempre viene del header o
  de una asignación.

## Brain: usuarios y login

Endpoints relevantes:

| Método | Ruta | Uso |
|---|---|---|
| `POST` | `/api/brain/auth/login` | Login común; emite access JWT + refresh cookie |
| `POST` | `/api/brain/auth/refresh` | Valida/extiende el refresh y emite un JWT nuevo |
| `POST` | `/api/brain/auth/logout` | Revoca el refresh token y limpia la cookie |
| `GET` | `/api/brain/auth/me` | Usuario de la sesión (requiere JWT) |
| `GET` | `/api/brain/core/usuario` | Listado de usuarios |
| `GET` | `/api/brain/core/usuario/{usuarioId}` | Usuario por id |
| `POST` | `/api/brain/core/usuario` | Alta (password obligatoria) |
| `PUT` | `/api/brain/core/usuario/{usuarioId}` | Edición |
| `PUT` | `/api/brain/core/usuario/{usuarioId}/password` | Cambio de password |
| `POST` | `/api/brain/core/usuario/{usuarioId}/password-reset` | Blanqueo: genera temporal (ADMIN) |
| `GET` | `/api/brain/core/usuario-empresa/usuario/{usuarioId}` | Empresas asignadas |
| `GET` | `/api/brain/core/usuario-negocio/usuario/{usuarioId}` | Todos los negocios asignados, con su empresa |
| `GET` | `/api/brain/core/usuario-negocio/usuario/{usuarioId}/empresa/{empresaId}` | Negocios asignados de una empresa |
| `GET` | `/api/brain/core/empresa` | Empresas |
| `GET` | `/api/brain/core/empresa/{empresaId}` | Empresa |
| `POST` | `/api/brain/core/empresa` | Alta de empresa (ADMIN) |
| `PUT` | `/api/brain/core/empresa/{empresaId}` | Edición de empresa (ADMIN) |
| `GET` | `/api/brain/core/negocio` | Negocios |
| `GET` | `/api/brain/core/negocio/empresa/{empresaId}` | Negocios de una empresa (resumen seguro) |
| `GET` | `/api/brain/core/negocio/{negocioId}` | Negocio |
| `GET` | `/api/brain/core/negocio/tenant/{tenantId}` | Negocio por tenant |
| `POST` | `/api/brain/core/negocio` | Alta de negocio (ADMIN); `negocioId` autogenerado y `tenantId` único |
| `PUT` | `/api/brain/core/negocio/{negocioId}` | Edición (ADMIN); conserva `tenantId` y `databasePassword` si no se envían |

El `tenantId` de un negocio se genera en el cliente administrador con el formato
`<PREFIJO>_tenant_<YYYYMMDD>_<36 hex>`; el backend sólo valida que no se repita.
La respuesta de negocio no incluye `databasePassword` (`WRITE_ONLY`).

El login:

1. Busca por `login` exacto.
2. Verifica `activo`.
3. Verifica que no esté bloqueado (`lockedUntil`).
4. Compara con Argon2 (`Argon2PasswordHashAdapter`).
5. En password incorrecta incrementa `failedLoginAttempts` y bloquea la cuenta al
   llegar a `app.security.max-failed-attempts`.
6. En éxito resetea intentos, actualiza `lastLoginAt`/`lastLoginIp`.
7. `POST /api/brain/auth/login` emite:
   - access token JWT HS256 (15 min por defecto), con `sub` = `usuarioId`,
     `login`, `nombre`, `administrador` y `authorities`;
   - refresh token opaco de 32 bytes, guardado como SHA-256 en
     `usuario_refresh_token` y enviado en cookie `HttpOnly`/`SameSite=Strict`;
   - `401` genérico para credenciales inválidas/inactivo/bloqueado;
   - `403` si `app=administrador` y el usuario no es administrador.
8. `POST /api/brain/auth/refresh` valida y extiende el refresh token (no lo rota
   en cada request, para que los dos clientes compartan la sesión sin pisarse);
   `logout` lo revoca.

### Blanqueo con contraseña temporal

- `POST /api/brain/core/usuario/{usuarioId}/password-reset` (sólo `ADMIN`)
  genera una clave temporal de 10 caracteres sin caracteres ambiguos, la guarda
  hasheada, desbloquea la cuenta y revoca los refresh tokens del usuario.
- La respuesta trae `temporaryPassword` y `expiresAt` una única vez.
- Mientras `mustChangePassword` esté activo, el access token incluye el claim
  `pwd_change_required` y `PasswordChangeRequiredFilter` sólo permite
  `PUT /api/brain/core/usuario/{id}/password`, `POST /api/brain/auth/logout` y
  `GET /api/brain/auth/me`; el resto responde `403` con código
  `PASSWORD_CHANGE_REQUIRED`.
- La temporal vence a las 24 h (`APP_SECURITY_TEMPORARY_PASSWORD_TTL`). Si vence
  antes de usarse, el login responde `428` y el refresh rechaza la sesión.
- Al cambiar la contraseña se limpian `mustChangePassword` y la expiración.
- El esquema se migró con `docs/sql/2026-10-30-usuario-password-reset.sql`
  (`must_change_password`, `temporary_password_expires_at`); `ddl-auto` sigue en
  `none`.

> `/api/brain/auth/**` es el único flujo de autenticación. El login legacy de
> tenant (`/api/v1/auth/**`) y el login viejo de Brain
> (`/api/brain/core/usuario/login`) fueron eliminados: no hay código de auth
> compartido con otros proyectos.

### Autorización

- `/api/brain/auth/login`, `refresh` y `logout`: públicos.
- `/api/brain/auth/me`: autenticado.
- `/api/brain/core/**`: autenticado; las mutaciones requieren `ADMIN`.
- `/api/brain/core/usuario`, `empresa` (listado) y `negocio/**`: `ADMIN`.
- `/api/brain/core/usuario/{id}`, asignaciones por usuario y cambio de password:
  el propio usuario o `ADMIN` (`@PreAuthorize`).
- Las mutaciones de asignaciones validan usuario, empresa y negocio. Si la
  asignación ya existe activa responden `409`; si existe inactiva la reactivan.
- Un negocio sólo puede asignarse si el usuario tiene la empresa activa. Al
  desactivar una empresa se desactivan en cascada sus negocios para ese usuario.
- `GET /api/brain/core/negocio/empresa/{empresaId}` devuelve un resumen sin
  datos de conexión (incluida `databasePassword`).
- `/api/tenant/core/**`: autenticado y con `usuario-empresa` + `usuario-negocio`
  activos para el `negocio.tenantId` del header (los `ADMIN` están exentos).
- `/actuator/env`: `ADMIN`; `health`/`info` públicos.

## Reglas de código

### Spring Data JPA

- **No usar `@Query`** en código nuevo, ni JPQL ni native, ni con `@Modifying`.
- Usar métodos derivados (`findBy...`, `findAllBy...`, `existsBy...`,
  `countBy...`, `findTopBy...OrderBy...`).
- Si no alcanza: Criteria API / `EntityManager` en un adaptador
  `*RepositoryCustomImpl`, o `JpaSpecificationExecutor` con `Specification`.
- No migrar `@Query` legacy salvo pedido explícito. Las consultas legacy ya
  fueron migradas a `*RepositoryCustomImpl` (Criteria API o SQL nativo vía
  `EntityManager`); no reintroducir la anotación.

### Endpoints

- **No usar `@RequestParam`** en endpoints nuevos.
- Identificadores y filtros simples: `@PathVariable`.
- Filtros compuestos, listas o payloads: `@RequestBody` (aunque sea una consulta,
  usar `POST` si corresponde).
- No usar `@RequestBody` en `GET`.
- Los `@RequestParam` existentes son legacy: no agregar nuevos ni cambiarlos sin
  autorización.

### Web

- Validar requests con `@Valid` y Jakarta Validation.
- No exponer entidades JPA directamente: usar DTOs (`*Request`, `*Response`) y
  mappers.
- No devolver `passwordHash` ni datos de conexión sensibles.
- Manejar errores de dominio en el controller con `ResponseStatusException`
  (patrón actual) y no filtrar stacktraces al cliente.
- Usar `ResponseEntity` y códigos HTTP coherentes.

### Persistencia

- Mantener `ddl-auto: none`; no agregar migraciones automáticas ni `create-drop`
  en producción.
- No agregar columnas o tablas sin coordinar con el esquema real.
- Respetar `UUID` para ids de Brain y `Integer` para ids de negocio donde ya está
  definido.
- Los adaptadores JPA traducen entidad ↔ dominio; no filtrar entidades al
  dominio.

### Seguridad

- `SecurityConfig` es stateless y usa JWT resource server. No volver a `permitAll`.
- `APP_JWT_SECRET` es obligatorio (mínimo 32 caracteres) y debe coincidir con el
  gateway; `APP_JWT_ISSUER` también debe coincidir.
- `APP_SECURITY_COOKIE_SECURE=true` en producción (HTTPS).
- El refresh token se guarda hasheado en `usuario_refresh_token`; nunca loguear el
  valor crudo, `Authorization` ni `Set-Cookie`.
- Los endpoints legacy `/api/tenant/core/**` ahora exigen JWT: avisar si un
  cliente viejo deja de funcionar.
- No hardcodear credenciales, IPs de DB ni passwords.
- No loguear passwords, hashes ni `Authorization`.
- No relajar las reglas de `@PreAuthorize` ni el chequeo de tenant sin pedido
  explícito.

## Tests

```bash
mvn -B verify
mvn -Dtest=ApplicationTests test
```

- `ApplicationTests`: contexto Spring, excluye registro automático en Consul.
- Tests de casos de uso y adaptadores: usar mocks/dobles, sin DB real.
- No afirmar cobertura inexistente.
- Al agregar un caso de uso, cubrir camino feliz y errores de dominio.

## Grafo y documentación

- `graphify-out/`: 4.164 nodos; usarlo antes de greps grandes.

```bash
graphify query "¿dónde se registra un comprobante?" --budget 1200
graphify path "Articulo" "Comprobante"
graphify explain "TenantInterceptor"
graphify affected "TenantDataSourceService"
```

- `README.md`: descripción funcional y módulos.
- `docs/diagrams/mermaid/`: diagramas de arquitectura, clases y secuencias.
- `CHANGELOG.md`: historial de versiones.

## Git

- Rama actual: `testing`.
- No crear ramas, commitear ni pushear sin autorización explícita.
- El repo tiene CI en `.github/workflows/maven.yml` (build + SonarCloud + imagen).

## Checklist antes de terminar

- [ ] El cambio respeta la dirección hexagonal.
- [ ] No se agregó `@Query` ni `@RequestParam`.
- [ ] Los endpoints de tenant usan `X-TENANT-ID` y `negocio.tenantId`.
- [ ] No se expusieron datos sensibles.
- [ ] `mvn -B verify` o la verificación mínima acordada pasa.
- [ ] Se actualizó la documentación si cambió un contrato público.
