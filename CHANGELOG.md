## [4.1.0] - 2026-11-01

### Added
- **feat(contable-balance)**: Nuevo slice hexagonal `modules.contable.balance` que genera el balance a partir del plan de cuentas y de los agregados de `movcon` y `movconapertura`. `BalanceCalculator` acumula los saldos propios, sube los totales al padre en orden descendente de código, deriva jerarquía y nivel con `CodigoCuenta`, redondea a 2 decimales y agrega advertencias cuando el libro no cuadra.
- **feat(contable-balance)**: Dos modos de presentación en `BalanceModo`: `ESTRUCTURA` (Balance General jerárquico) y `POR_CUENTA` (Saldos Contables planos), con `incluyeApertura` e `incluyeInflacion` como filtros del cálculo.
- **feat(reporte-balance)**: `BalanceReporteController` expone `POST /api/tenant/core/reporte/balance/datos`, `/pdf`, `/xlsx` y `GET /api/tenant/core/reporte/balance/monedas`. La vista previa, el PDF y el XLSX se arman desde el mismo `ReporteTabla`, con subtítulo de período y monedas, y nombre de archivo derivado del negocio y del rango.
- **feat(balance-cotizaciones)**: Conversión de moneda por par `monedaRegistroId`/`monedaExpresionId` con cotizaciones del BCRA bajo demanda. `BalanceMovimientoPort.findFechasSinCotizacion` detecta los días sin cobertura, `CotizacionOnDemandService` pide solo esa ventana, aplica la última tasa disponible y hace upsert idempotente en `moneda_cotizacion`. Si el BCRA no devuelve tasas responde `BALANCE_COTIZACION_FALTANTE` y no inventa una cotización 1,0.
- **feat(balance-cache)**: Caché Caffeine del balance (500 entradas, 60 s) con clave compuesta por tenant, filtros y watermark de movimientos, apertura y cotizaciones.
- **feat(reporte-xlsx)**: Exportación XLSX transversal del módulo `reporte` con Apache POI: caso de uso `GenerarReporteXlsxUseCase`, puerto `ReporteXlsxPort` y adaptador `OpenPoiXlsxAdapter` con título, metadatos, subtítulo, cabecera congelada, números nativos con formato `#,##0.00`, sangría por nivel y nombre de hoja saneado.
- **feat(balance-errores)**: `BalanceExceptionHandler` traduce los códigos `BALANCE_*` a `ProblemDetail`: `400` para rango o moneda inválidos, `404` sin negocio activo y `422` cuando faltan cotizaciones.
- **test(balance-xlsx)**: Tests unitarios de `BalanceCalculator`, `GenerarBalanceUseCaseImpl`, `GenerarBalanceReporteUseCaseImpl`, `BalanceReporteController` y `OpenPoiXlsxAdapter`.

### Changed
- **feat(reporte-contrato)**: `ReporteTabla` incorpora `advertencias`, `ReporteCabecera` incorpora `subtitulo` y `ReporteColumna` incorpora `tipo` (`ReporteTipoColumna.TEXTO|NUMERO`). Los constructores anteriores se conservan, por lo que los reportes existentes no requieren cambios. La vista previa JSON agrega `subtitulo`, `tipo` y `advertencias`, y el PDF imprime el subtítulo cuando está presente.
- **chore(deps)**: Se agrega `org.apache.poi:poi-ooxml` 5.5.1.
- **docs**: README, CHANGELOG, `docs/modules.md` y diagramas Mermaid actualizados con el balance, la exportación XLSX y el nuevo contrato de reportes.

## [4.0.0] - 2026-10-31

### Breaking Changes
- **refactor!(auth)**: La autenticación se centraliza en `/api/brain/auth` (`login`, `refresh`, `logout` y `me`) con JWT HS256 y refresh token opaco en cookie `HttpOnly`. Se eliminan el login legacy de tenant (`/api/v1/auth/**`) y el login anterior de Brain (`POST /api/brain/core/usuario/login`).
- **refactor!(tenant-api)**: Todos los endpoints `/api/tenant/core/**` exigen JWT válido y asignaciones `usuario-empresa` y `usuario-negocio` activas para el `tenantId` del header `X-TENANT-ID`; los usuarios con `ADMIN` quedan exentos. Los clientes que llamaban a la API de tenant sin token dejan de funcionar.
- **refactor!(config)**: `APP_JWT_SECRET` pasa a ser obligatorio (mínimo 32 caracteres) y `APP_JWT_ISSUER` debe coincidir con el gateway.
- **refactor!(runtime)**: Se elimina Kotlin del proyecto: ya no se compila ni se ejecuta código Kotlin y todo el código quedó en Java 25.

### Added
- **feat(auth)**: Módulo `brain/auth` con login JWT HS256 (15 minutos por defecto), refresh token opaco de 32 bytes guardado como SHA-256 en `usuario_refresh_token`, cookie `HttpOnly`/`SameSite=Strict` y endpoints `/api/brain/auth/login|refresh|logout|me`.
- **feat(auth-login)**: Lockout por intentos fallidos (`APP_SECURITY_MAX_FAILED_ATTEMPTS`, `APP_SECURITY_LOCK_DURATION`), actualización de `lastLoginAt`/`lastLoginIp`, hashing Argon2 y claims `administrador`, `authorities` y `pwd_change_required`.
- **feat(auth-password-reset)**: Blanqueo con contraseña temporal de 10 caracteres (solo `ADMIN`) en `POST /api/brain/core/usuario/{usuarioId}/password-reset`; la temporal vence según `APP_SECURITY_TEMPORARY_PASSWORD_TTL` (24 h), revoca los refresh tokens y `PasswordChangeRequiredFilter` limita la sesión hasta el cambio.
- **feat(auth-schema)**: `db/refresh-token-schema.sql` crea `usuario_refresh_token` de forma idempotente; se ejecuta al arrancar según `APP_DB_INIT_MODE` (default `always`).
- **feat(reportes)**: Módulo transversal `modules.reporte` con `ReporteTabla` como única fuente del JSON de vista previa y del PDF OpenPDF (A4, cabecera repetida, `Página X de Y`, wrapping), `ReporteDtoMapper`, `ReportePdfService` y manejo de errores propio.
- **feat(contable-cuenta)**: CRUD de cuentas contables con árbol jerárquico (`GET /api/tenant/core/cuenta/arbol`), detalle, alta, edición y baja.
- **feat(contable-reporte)**: Reporte del plan de cuentas en `POST /api/tenant/core/reporte/plan-cuentas/datos` y `/pdf`.
- **feat(contable-ejercicio)**: CRUD de ejercicios, sugerencia del siguiente ejercicio y bloqueo/desbloqueo en `/api/tenant/core/ejercicio`.
- **feat(contable-inflacion)**: Coeficientes de inflación por año y mes con validación de dominio en `/api/tenant/core/coeficiente-inflacion`.
- **feat(brain-usuario-negocio)**: Consulta de todos los negocios asignados a un usuario (`GET /api/brain/core/usuario-negocio/usuario/{usuarioId}`) y resumen de negocios por empresa (`GET /api/brain/core/negocio/empresa/{empresaId}`).
- **feat(modulos)**: Fronteras de módulo con Spring Modulith y verificación en `ModularityTests`. Módulos: `platform`, `compras`, `contable`, `reporte`, `stock`, `ventas`, `reserva`, `tesoreria` y `personal`.
- **feat(modulos-api)**: Contratos entre módulos: `platform.api.NegocioActual` y `contable.api.CuentaQuery` / `CuentaResumen`.
- **feat(tenant-datasource)**: Ciclo de vida de pools por tenant con creación bajo demanda, evicción por inactividad, tope de pools vivos y métricas Micrometer (`eterea.tenant.datasource.*`).
- **docs**: `AGENTS.md`, `docs/modules.md` y scripts de migración en `docs/sql` para `negocio.tenant_id` único, blanqueo de contraseña y unicidad de coeficientes de inflación.

### Changed
- **chore(deps)**: Spring Boot 4.1.1 → 4.1.2, Spring Cloud 2025.1.3 → 2025.1.4, MySQL Connector/J 26.7.0 → 26.10.0, SpringDoc OpenAPI 3.1.1 → 3.2.0, ModelMapper 3.2.6 → 3.2.8, Commons Lang3 3.20.0 → 3.21.0 y JaCoCo 0.8.13 → 0.8.16. Se agrega Spring Modulith 2.1.2 y `spring-boot-starter-oauth2-resource-server`.
- **refactor(modules)**: Reubicación de `tenant.hexagonal.cuenta|legajo|cierrecaja|negocio|articulo|comprobante` a `modules.contable.cuenta`, `modules.personal.legajo`, `modules.tesoreria.cierrecaja`, `modules.platform.negocio`, `modules.stock.articulo` y `modules.ventas.comprobante`. Sin cambios en rutas HTTP ni contratos.
- **refactor(stock-ventas)**: Se quitaron las asociaciones JPA `@OneToOne` a `CuentaEntity` en `ArticuloEntity` y `ComprobanteEntity`; las FK se conservan y la cuenta se resuelve con `CuentaQuery`.
- **refactor(tenant-datasource)**: Los pools por tenant pasan de 30 conexiones fijas a un pool bajo demanda (8 máximo, 1 mínimo) con cierre a los 15 minutos de inactividad.
- **refactor(persistence)**: Eliminadas todas las anotaciones `@Query`. Las consultas no derivadas se movieron a `*RepositoryCustomImpl` con `EntityManager` (Criteria API o SQL nativo), sin cambiar la semántica.

### Fixed
- **fix(brain-negocio)**: El alta de negocio ya no exige `negocioId`; el identificador se genera con la secuencia `AUTO_INCREMENT` de la tabla.
- **fix(brain-negocio)**: La edición conserva `tenantId` y `databasePassword` cuando el request no los envía.
- **fix(brain-negocio)**: El `tenantId` es obligatorio y único al crear (responde `409 Conflict` si ya existe).
- **fix(brain-negocio)**: `databasePassword` deja de exponerse en las respuestas (`WRITE_ONLY`).

### Removed
- **refactor(runtime)**: Eliminación total del runtime alternativo de JVM. Todo el código quedó en Java 25 y se retiraron del `pom.xml` el plugin de compilación, las dependencias de stdlib/testing y el módulo de Jackson asociado.
- **refactor(paquetes)**: El paquete `eterea.tenant.service.tenant` desapareció. Sus clases se distribuyeron entre `platform`, `compras`, `contable`, `reporte`, `stock`, `ventas`, `reserva`, `tesoreria` y `personal`.
- **refactor(kernel)**: `Auditable` se movió al kernel (`tool`).
- **refactor(fronteras)**: Se eliminaron las relaciones JPA que cruzaban módulos (`Voucher`, `ReservaArticulo`, `ArticuloMovimientoPrevio`, `ValorMovimiento`, `CuentaMovimiento`, `TransferenciaEntity`, `ProveedorMovimientoEntity`) y las dependencias compartidas se expusieron por paquetes `api`.
- **chore(cleanup)**: Se eliminaron DTOs, excepciones y utilidades sin uso (`CodigoQR`, `OrderNoteException`, `OrderWeb`, `OffsetDateTimeDeserializer`).

## [3.1.0] - 2026-09-12

### Added
- **feat(brain-usuario-empresa)**: Nuevo módulo hexagonal `brain/usuarios/usuario_empresa` para la vinculación usuario-empresa con arquitectura Brain:
  - Modelo de dominio `UsuarioEmpresaBrain`, entidad JPA `UsuarioEmpresaBrainEntity` (tabla `usuario_empresa`, restricción `uq_usuario_empresa` e índice `idx_empresa_id`) y puertos de entrada/salida.
  - Casos de uso: `CreateUsuarioEmpresaBrainUseCaseImpl`, `GetUsuarioEmpresaBrainsByUsuarioIdUseCaseImpl`, `ActivateUsuarioEmpresaBrainUseCaseImpl` y `DeactivateUsuarioEmpresaBrainUseCaseImpl`.
  - Servicio de aplicación `UsuarioEmpresaBrainService`, adaptador `JpaUsuarioEmpresaBrainRepositoryAdapter`, mappers (`UsuarioEmpresaBrainMapper`, `UsuarioEmpresaBrainDtoMapper`) y excepción `UsuarioEmpresaBrainException`.
  - Nuevos endpoints REST bajo `/api/brain/core/usuario-empresa`: creación (`POST`), consulta por usuario (`GET /usuario/{usuarioId}`), activación (`PUT /{usuarioEmpresaId}/activate`) y desactivación (`PUT /{usuarioEmpresaId}/deactivate`).
- **feat(brain-usuario-negocio)**: Nuevo módulo hexagonal `brain/usuarios/usuario_negocio` para la vinculación usuario-negocio con datos del negocio:
  - Modelos de dominio `UsuarioNegocioBrain`, `NegocioInfo` y `UsuarioNegocioConNegocioBrain`; entidad JPA `UsuarioNegocioBrainEntity` (tabla `usuario_negocio`, restricción `uq_usuario_negocio` e índice `idx_negocio_id`).
  - Casos de uso: `CreateUsuarioNegocioBrainUseCaseImpl`, `GetUsuarioNegocioBrainsByUsuarioIdAndEmpresaIdUseCaseImpl`, `ActivateUsuarioNegocioBrainUseCaseImpl` y `DeactivateUsuarioNegocioBrainUseCaseImpl`.
  - Puerto de salida `NegocioInfoRepository` con adaptador `NegocioInfoRepositoryAdapter`, servicio `UsuarioNegocioBrainService`, mappers (`UsuarioNegocioBrainMapper`, `UsuarioNegocioBrainDtoMapper`) y excepción `UsuarioNegocioBrainException`.
  - Nuevos endpoints REST bajo `/api/brain/core/usuario-negocio`: creación (`POST`), consulta con datos del negocio (`GET /usuario/{usuarioId}/empresa/{empresaId}`), activación (`PUT /{usuarioNegocioId}/activate`) y desactivación (`PUT /{usuarioNegocioId}/deactivate`).

### Changed
- **refactor(brain-usuario)**: Reubicación del módulo de usuarios Brain del paquete `brain.usuario` a `brain.usuarios.usuario`, agrupándolo con los nuevos módulos de vinculación. Sin cambios en rutas HTTP (`/api/brain/core/usuario`) ni en las firmas de los puertos de entrada.
- **refactor(deps)**: Actualización de Spring Boot 4.1.0 → 4.1.1 (`pom.xml`).
- **refactor(deps)**: Actualización de Spring Cloud 2025.1.2 → 2025.1.3 (`pom.xml`).
- **refactor(deps)**: Actualización de SpringDoc OpenAPI 3.1.0 → 3.1.1 (`pom.xml`).
- **docs**: Actualización de los diagramas `usuario-class-diagram.mmd`, `component-diagram.mmd` y `hexagonal-architecture-diagram.mmd` para reflejar los módulos Brain de usuarios (`usuario`, `usuario_empresa`, `usuario_negocio`) en lugar de los eliminados módulos unificados.

## [3.0.0] - 2026-08-17

### Breaking Changes
- **refactor!(brain-api)**: Sustituidos los módulos y rutas públicas `unificado` de negocio y usuario por los módulos Brain bajo `/api/brain/core/empresa`, `/api/brain/core/negocio` y `/api/brain/core/usuario`.
- **refactor!(tenant-api)**: La API de movimientos de proveedores queda disponible en `/api/tenant/core/proveedormovimiento`; se eliminan las rutas anteriores.

### Added
- **feat(brain-empresa)**: CRUD de empresas Brain con validación de requests y respuestas `201 Created`.
- **feat(brain-negocio)**: CRUD de negocios Brain y consulta por `tenantId`.
- **feat(brain-usuario)**: CRUD de usuarios Brain, login con resolución de IP del cliente y cambio de contraseña.
- **feat(security)**: Hashing de contraseñas mediante el adaptador Argon2.

### Changed
- **refactor(multitenancy)**: La resolución dinámica de DataSources consulta `NegocioBrainRepository` y expone el bean principal como `TenantRoutingDataSource`.
- **fix(mysql)**: Añadido `allowPublicKeyRetrieval=true` a la URL JDBC de MySQL.
- **chore(deps)**: Actualizado SpringDoc OpenAPI de 3.0.3 a 3.1.0 y MySQL Connector/J de 9.7.0 a 26.7.0.

## [2.8.0] - 2026-07-09

### Added
- **feat(unificado-usuarios)**: Nuevos módulos de arquitectura hexagonal para la gestión de usuarios unificados y vinculación con negocios:
  - **Módulo `usuarioUnificado`**:
    - Modelo de dominio `UsuarioUnificado` (id, login, password).
    - Entidad JPA `UsuarioUnificadoEntity` y su correspondiente repositorio Spring Data `JpaUsuarioUnificadoRepository`.
    - Puerto de salida `UsuarioUnificadoRepository` con adaptador JPA `JpaUsuarioUnificadoRepositoryAdapter`.
    - Casos de uso de entrada: `FindAllUsuarioUnificadoUseCaseImpl`, `FindUsuarioUnificadoByLoginUseCaseImpl`, y `FindUsuarioUnificadoByUsuarioIdUseCaseImpl` para consultas de usuarios unificados.
    - Servicio de aplicación `UsuarioUnificadoService` como orquestador.
    - Controlador REST `UsuarioUnificadoController` bajo la ruta `/api/unificado/core/usuarioUnificado`.
    - DTOs y mappers bidireccionales (`UsuarioUnificadoMapper`, `UsuarioUnificadoDtoMapper`).
  - **Módulo `usuarioNegocioUnificado`**:
    - Modelo de dominio `UsuarioNegocioUnificado` (usuarioNegocioId, usuarioId, negocioId, objeto NegocioUnificado, objeto UsuarioUnificado).
    - Entidad JPA `UsuarioNegocioUnificadoEntity` y su repositorio Spring Data `JpaUsuarioNegocioUnificadoRepository`.
    - Puerto de salida `UsuarioNegocioUnificadoRepository` con adaptador JPA `JpaUsuarioNegocioUnificadoRepositoryAdapter`.
    - Casos de uso de entrada: `FindAllUsuarioNegocioUnificadoUseCaseImpl`, `FindUsuarioNegocioUnificadoByUsuarioIdUseCaseImpl`, y `FindUsuarioNegocioUnificadoByUsuarioNegocioIdUseCaseImpl` para consultas de vinculación usuario-negocio.
    - Servicio de aplicación `UsuarioNegocioUnificadoService` como orquestador.
    - Controlador REST `UsuarioNegocioUnificadoController` bajo la ruta `/api/unificado/core/usuarioNegocioUnificado`.
    - DTOs y mappers bidireccionales (`UsuarioNegocioUnificadoMapper`, `UsuarioNegocioUnificadoDtoMapper`).
- **docs**:
  - Nuevo diagrama de clases de Mermaid `usuario-class-diagram.mmd` que detalla los nuevos modelos, mappers, DTOs y entidades de usuarios.
  - Integración del diagrama en el pipeline automatizado de GitHub Actions de generación de documentación (`generate-docs.yml`).
  - Actualización del diagrama de componentes (`component-diagram.mmd`) y arquitectura hexagonal (`hexagonal-architecture-diagram.mmd`) para reflejar los nuevos módulos unificados.

## [2.7.1] - 2026-07-09

### Refactored
- **refactor(json-serialization)**: Implementación de interfaz `Jsonifyable` unificada para serialización JSON en modelos de dominio, entidades JPA, DTOs y modelos legacy:
  - Nuevos `Jsonifyable` interface y refactor de `Jsonifier` utilidad centralizada
  - Modelos de dominio Java: `Articulo`, `ArticuloMovimiento`, `Comprobante`, `Cuenta`, `InvoiceData`, `Transferencia` implementan `Jsonifyable`
  - Entidades JPA: `ArticuloEntity`, `ArticuloMovimientoEntity`, `ComprobanteEntity`, `CuentaEntity`, `TransferenciaEntity` implementan `Jsonifyable`
  - DTOs: `FacturacionDto`, `ClienteMovimientoResponse`, `InvoiceDataResponse`, `RegistroCaeResponse`, `ArticuloDto`, `ImpresionFiscalDto`, `ParametroDto`, `TransferenciaDto`, `TransferenciaWrapperDto` implementan `Jsonifyable`
  - Modelos legacy: `OrderNote`, `ArticuloBarra`, `ClienteMovimientoPrevio`, `Reserva`, `ReservaArticulo`, `StockMovimiento`, `Valor`, `ValorMovimiento`, `Voucher`, `VoucherProducto` implementan `Jsonifyable`
  - Modelos Java adicionales: `ClienteMovimiento`, `CuentaMovimiento`, `RegistroCae`, `ReservaContext`, `Track`, `FacturaResponseDto`, `FacturaResponseDto.FacturaDto` implementan `Jsonifyable`
  - `ConsolidadoService.ComprobanteRango` record ahora implementa `Jsonifyable` y usa `@RequiredArgsConstructor`
  - Eliminación de métodos `jsonify()` duplicados en cada clase, delegando en `Jsonifier.builder(this).build()`

## [2.7.0] - 2026-07-09

### Changed
- **refactor(negocio)**: Refactorización completa del módulo Negocio a arquitectura hexagonal con use cases dedicados:
  - Nuevos `GetNegocioByIdUseCaseImpl`, `GetAllNegociosByCopyArticuloUseCaseImpl`, `GetAllNegociosWithIpAddressUseCaseImpl` como implementaciones de puertos de entrada
  - `NegocioService` ahora delega en los use cases en lugar de implementarlos directamente
  - `GetNegocioByIdUseCase.getNegocioById()` retorna `Negocio` directo (lanza `NegocioException`) en lugar de `Optional<Negocio>`
  - Añadido `@Builder` a `Negocio` y `NegocioEntity`; nuevo `NegocioMapper.toEntity()`
  - `NegocioController`: cambio de ruta de `/api/core/negocio` y `/negocio` a `/api/tenant/core/negocio`, constructores explícitos, manejo de `NegocioException` con 404
- **refactor(adapter-packages)**: Estandarización de paquetes de adaptadores JPA — 5 adaptadores movidos de `persistence/repository/` a `persistence/adapter/`: `JpaCuentaRepositoryAdapter`, `JpaEmpresaRepositoryAdapter`, `JpaLegajoRepositoryAdapter`, `JpaProveedorMovimientoRepositoryAdapter`, `JpaTransferenciaRepositoryAdapter`
- **refactor(controller-constructors)**: Sustitución de `@RequiredArgsConstructor` por constructores explícitos en `InvoiceDataController`, `NegocioController`, `AnticipoHaberesController`; adición de `@RequiredArgsConstructor` en `JpaComprobanteRepositoryAdapter`, `JpaCuentaRepositoryAdapter`, `JpaNegocioUnificadoRepositoryAdapter`, `JpaNegocioUnificadoController`
- **refactor(service)**: `CierreCajaAnticipoHaberesService` refactorizado para delegar en nuevo `SaveAllAnticipoHaberesUseCaseImpl`
- **refactor(mapper)**: `ResumenIvaComprasMensualDtoMapper` actualizado para usar `negocioService.getNegocioById()` sin `Optional`; `AnticipoHaberesMapper` cambiado de `@Service` a `@Component`
- **refactor(imports)**: Limpieza de imports no utilizados y eliminación de `@Component` de `LegajoRepository` (interface, no clase concreta)

### Fixed
- **fix(http-status)**: Corrección de códigos HTTP en endpoints de negocio y legajo — `BAD_REQUEST` (400) cambiado a `NOT_FOUND` (404) cuando no se encuentra un recurso
- **fix(error-handling)**: `InvoiceDataController` ahora captura `InvoiceDataException` y retorna 404 con mensaje descriptivo en lugar de 500

### Removed
- **chore(cleanup)**: Eliminación de 5 clases de excepción legacy/Java antiguas reemplazadas por versiones hexagonales Java:
  - `tenant/exception/LegajoException.java`, `tenant/exception/NegocioException.java`, `tenant/exception/ProductoArticuloException.java`
  - `legacy/exception/ArticuloMovimientoException.java`, `legacy/exception/TransferenciaException.java`

### New
- **feat(anticipohaberes)**: Nuevo caso de uso `SaveAllAnticipoHaberesUseCaseImpl` para el módulo de anticipo de haberes
- **feat(exceptions)**: Nuevas excepciones hexagonales Java: `InvoiceDataException`, `LegajoException`, `NegocioException`, `TransferenciaException`, `ProductoArticuloException`

## [2.6.0] - 2026-07-09

### Features
- **feat(multitenant)**: Nuevo sistema de multi-tenancy con ruteo dinámico de DataSources
  - `TenantRoutingDataSource`: Implementación de `AbstractRoutingDataSource` para ruteo por tenant
  - `TenantDataSourceService`: Servicio de creación dinámica de DataSources por tenant (pool HikariCP de 30 conexiones)
  - `TenantContext`: Contexto thread-local para mantener el tenant activo durante la request
  - `TenantInterceptor`: Interceptor HTTP que extrae el header `X-TENANT-ID` y resuelve el DataSource
  - `WebMvcConfig`: Registro del interceptor en el pipeline MVC
  - `OpenApiConfig`: Documentación Swagger automática del header `X-TENANT-ID` en endpoints tenant
  - `DataSourceConfiguration`: Configuración del `DataSource` maestro y enrutador

### Changed
- **refactor(negociounificado)**: Modificación del modelo `NegocioUnificado` para soporte multi-tenant:
  - Nuevo campo `databasePort` añadido al modelo de dominio, entidad JPA, mapper, DTO de respuesta y DTO mapper
  - Eliminación de los campos `backendServer` y `backendPort` del modelo de dominio, entidad JPA y mapper
  - Nuevo método `findByTenantId` en `NegocioUnificadoRepository` y su adaptador JPA para lookup de conexiones

### Docs
- **docs**: Nuevo diagrama de secuencia `tenant-resolution-sequence-diagram.mmd` para documentar el flujo de resolución de tenants
- **docs**: Actualización del diagrama de clases `negociounificado-class-diagram.mmd` para reflejar los cambios en el modelo
- **docs**: Actualización del diagrama de arquitectura hexagonal para incluir componentes de multi-tenancy

### Tests
- **test(tenant)**: Nuevos tests unitarios para `TenantInterceptor` con validación de header `X-TENANT-ID`
  - Test: request sin header → 400 Bad Request
  - Test: request con header → 200 OK

## [2.5.0] - 2026-07-08

### Features
- **feat(negociounificado)**: Nuevo módulo hexagonal `NegocioUnificado` para gestión unificada de negocios
  - Nuevo endpoint `GET /negocioUnificado/` para listar todos los negocios unificados
  - Nuevo endpoint `GET /negocioUnificado/{negocioId}` para buscar negocio unificado por ID
  - Modelo de dominio `NegocioUnificado`, entidad JPA, mappers, DTOs y controlador REST
  - Casos de uso: `FindAllNegocioUnificadoUseCaseImpl` y `FindNegocioUnificadoByNegocioIdUseCaseImpl`
  - Puerto de salida `NegocioUnificadoRepository` con adaptador JPA
- **docs**: Nuevo diagrama de clases y actualización de diagramas de arquitectura para el módulo `NegocioUnificado`

## [2.4.5] - 2026-06-29

### Fixed
- **fix(reserva)**: Corregida la duplicación del artículo compartido en `ReservaService.generarReservaArticulo`. La colección `collectionAgregar` se construía con la clave `Articulo::toString`, que al no existir un `@ToString` en `Articulo` devuelve el hash de identidad del objeto (único por instancia)

## [2.4.4] - 2026-06-28

### Fixed
- **fix(json-serialization)**: Corregido patrón `@JsonFormat` de `yyyy-MM-dd'T'HH:mm:ssZ` a `yyyy-MM-dd'T'HH:mm:ssXX` en todas las entidades y modelos con `OffsetDateTime` para serialización ISO 8601 correcta con colon en el offset horario (afecta ~40 clases Java y legacy en los módulos: `articulomovimiento`, `cierrecaja`, `legajo`, `proveedormovimiento`, `transferencia`, `clienteweb`, `voucher`, `reserva`, `stock`, `valor`, `asiento`, `cliente`, `cuenta`, `inventario`, `feriado`, `moneda`, `comprobante`, `habitacion`, `producto`, `usuario`)

### Changed
- **refactor(logging)**: Eliminación de Log4j2 — removido `spring-boot-starter-logging` exclusion y dependencia `spring-boot-starter-log4j2` de `spring-boot-starter`, revirtiendo al logging default de Spring Boot (Logback)
- **refactor(deps)**: Actualización de Spring Boot 4.0.6 → 4.1.0 (`pom.xml`)
- **refactor(deps)**: Actualización de Spring Cloud 2025.1.0 → 2025.1.3 (`pom.xml`)
- **refactor(deps)**: Actualización de OpenPDF 3.0.3 → 3.0.5 (`pom.xml`)
- **refactor(deps)**: Actualización de MySQL Connector 9.6.0 → 9.7.0 (`pom.xml`)

## [2.4.3] - 2026-06-10

### Changed
- **refactor(controller)**: Sustitución de constructor manual por `@RequiredArgsConstructor` en `ArticuloBarraController`
- **refactor(service)**: Sustitución de constructor manual por `@RequiredArgsConstructor` en `ArticuloBarraService`
- **refactor(logging)**: Refactor de logging en `ArticuloBarraService` — reemplazo del método privado `logArticuloBarra()` (basado en `JsonMapper`) por `log.debug` con `jsonify()` inline, eliminando dependencias no utilizadas (`Objects`, `JsonMapper`, `JsonProcessingException`)

## [2.4.2] - 2026-06-07

### Fixed
- **fix(articulomovimiento)**: Corrección en `AutoCompleteTotalesByClienteMovimientoIdUseCaseImpl` para prevenir `totalConIva` negativo al usar el valor absoluto de `cantidad` (`movimiento.getCantidad().abs()`) en el cálculo de totales

### Docs
- **docs**: Nuevo diagrama de clases `articulomovimiento-class-diagram.mmd` para documentar el módulo `ArticuloMovimiento` y su infraestructura hexagonal

## [2.4.1] - 2026-06-06

### Fixed
- **fix(mapper)**: Null-safety improvements en `ArticuloMovimientoMapper` para campos `stockMovimientoId`, `tenenciaMovimientoId`, `nivel`, `cierreCajaId`, `cierreRestaurantId`, `mozoId`, `precioValuacion`, `comision`, `totalConIva` y `totalSinIva` — prevención de NPEs con valores nulos del dominio
- **fix(error-handling)**: Mejora en manejo de excepciones en `MakeFacturaProgramaDiaService` añadiendo captura genérica de `Exception` para errores imprevistos en envío de email

### Changed
- **refactor(controller)**: Sustitución de constructor manual por `@RequiredArgsConstructor` en `ConceptoFacturadoController`
- **refactor(mapper)**: Sustitución de constructor manual por `@RequiredArgsConstructor` en `ArticuloMovimientoMapper`
- **refactor(service)**: Sustitución de constructor manual por `@RequiredArgsConstructor` en `ConceptoFacturadoService`
- **refactor(service)**: Adición de `@Slf4j` en `GetInvoiceDataByClienteMovimientoIdImpl`
- **refactor(dto)**: Adición del método `jsonify()` en `RegistroCaeResponse` para serialización JSON consistente
- **refactor(logging)**: Mejora de mensajes de depuración con formato estructurado (`\n\n`) en `ArticuloMovimientoService`, `AutoCompleteTotalesByClienteMovimientoIdUseCaseImpl`, `GetInvoiceDataByClienteMovimientoIdImpl`, `InvoiceDataMapper`, `FacturacionService`, `MakeFacturaProgramaDiaService`, `VouchersService` y `ProductsService`

### Dependencies
- **chore(deps)**: Actualización de Java 25 en `pom.xml`

## [2.4.0] - 2026-06-05

### Changed
- **refactor(negocio)**: Refactorización del modelo `Negocio` en el módulo `empresa`:
  - Nuevo campo `databasePort` añadido al modelo de dominio, entidad JPA, mapper, DTO de respuesta y DTO mapper
  - Eliminación de los campos `facturaServer` y `facturaPort` del modelo de dominio, entidad JPA y mapper
  - Reordenación del campo `backendServer` en `NegocioResponse` y `NegocioDtoMapper` para consistencia

### Docs
- **docs**: Nuevo diagrama de clases `negocio-class-diagram.mmd` para documentar el modelo `Negocio` y su infraestructura asociada

## [2.3.1] - 2026-05-30

### Changed
- **refactor(empresa)**: Refactorización completa del módulo `Empresa` para separar correctamente los casos de uso de la capa de servicio en arquitectura hexagonal
- **refactor(empresa)**: Extracción de `GetLastEmpresaUseCaseImpl` con la lógica de generación de UUID y persistencia desde `EmpresaService`
- **refactor(empresa)**: Renombrado de `EmpresaJpaRepository` → `JpaEmpresaRepository` y `EmpresaJpaRepositoryAdapter` → `JpaEmpresaRepositoryAdapter` para consistencia con el resto de módulos hexagonales
- **refactor(empresa)**: Simplificación de `EmpresaService` para que actúe como orquestador delegando en `GetLastEmpresaUseCase`
- **refactor(empresa)**: `EmpresaController` ahora inyecta `EmpresaService` en lugar de `GetLastEmpresaUseCase` directamente
- **test(empresa)**: Nuevos tests unitarios completos para `GetLastEmpresaUseCaseImpl` (3 casos: con businessId, sin businessId genera UUID, empresa no encontrada)
- **test(empresa)**: Simplificación de `EmpresaServiceTest` para verificar únicamente la delegación al caso de uso

## [2.3.0] - 2026-03-30

### 🚀 Migración Masiva a Arquitectura Hexagonal

#### Features
- **feat(clienteMovimiento)**: Nuevo endpoint `POST /byIds` para buscar movimientos de cliente por lista de IDs
- **feat(cliente)**: Nuevo endpoint `POST /byIds` para buscar clientes por lista de IDs
- **feat(hexagonal)**: Nuevos módulos hexagonales completos (puertos, casos de uso, JPA, controladores REST):
  - **`articulo`**: Migración completa del modelo `Articulo` de legacy a Java con arquitectura hexagonal. Casos de uso: creación, búsqueda (por ID, autoNumerico, máscara balanza, texto, voucher), cálculo de totales y actualización. Nuevo controlador REST con endpoints CRUD y DTOs (`ArticuloRequest`, `ArticuloResponse`, `ArticuloResponseForInvoiceData`, `TotalArticuloResponse`)
  - **`articulomovimiento`**: Migración completa del modelo `ArticuloMovimiento` de legacy a Java con arquitectura hexagonal. Casos de uso: creación, consulta por ID, por clienteMovimientoId, por stockMovimientoId, autocompletado de totales y guardado masivo
  - **`comprobante`**: Migración completa del modelo `Comprobante` de legacy a Java con arquitectura hexagonal. Casos de uso: consulta por módulo, comprobantes asociables, comprobantes disponibles y búsqueda por ID
  - **`cuenta`**: Migración completa del modelo `Cuenta` de legacy a Java con arquitectura hexagonal. Casos de uso: listado completo, listado con cuenta maestra, búsqueda por cuenta maestra y por número de cuenta
  - **`transferencia`**: Migración completa del modelo `Transferencia` de legacy a Java con arquitectura hexagonal. Caso de uso: búsqueda por identificador único
- **feat(controllers)**: Nuevos controladores REST para los módulos hexagonales: `ArticuloController`, `ArticuloMovimientoController`, `ComprobanteController`, `CuentaController`, `TransferenciaController`
- **feat(facade)**: Actualización de servicios facade (`PrecioController`, `StockController`, `TransferenciasController`, `VouchersController`) para usar los nuevos servicios hexagonales
- **feat(clients)**: Actualización de `ArticuloClient`, `CuentaClient`, `ParametroClient` con nuevos builders compatibles con Spring Boot 4.0.6

### Changed
- **refactor(dto)**: Ampliación de `ValorMovimientoDto` con los campos `movClieId`, `clienteId`, `nroPlanCta`, `created` y `updated`
- **refactor(mapper)**: Actualización de `ValorMovimientoDtoMapper` para incluir los nuevos campos con null-guard en `valor.getConcepto()`
- **refactor(clients)**: Mejora en `ArticuloBarraClientBuilder`, `ArticuloClientBuilder`, `CuentaClientBuilder`, `ParametroClientBuilder` añadiendo `JacksonDecoder` con `legacyModule` para mejor compatibilidad con modelos legacy
- **refactor(clients)**: Adición de `SpringMvcContract` y `JacksonEncoder` en `ArticuloClientBuilder` para soportar solicitudes y respuestas JSON
- **refactor(controller)**: Mejora en `ArticuloController.add()` especificando `consumes` y `produces` como "application/json"
- **refactor(model)**: Adición de `@JsonIgnoreProperties(ignoreUnknown = true)` en modelos legacy para ignorar propiedades desconocidas en JSON
- **refactor(model)**: Nuevo campo `porcentajeDescuentoPersonal` en `ParametroDto.java`
- **refactor(service)**: Mejora en logging de `ArticulosService` con mensajes más descriptivos
- **refactor(service)**: Uso de `Jsonifier` en lugar de serialización manual en `ArticulosService`

### Removed
- **chore(cleanup)**: Eliminación de modelos legacy obsoletos: `Articulo.java`, `Comprobante.java`, `Cuenta.java`, `Transferencia.java` y sus DTOs asociados
- **chore(cleanup)**: Eliminación de repositorios legacy obsoletos: `ArticuloRepository.java`, `ArticuloMovimientoRepository.java`, `ComprobanteRepository.java`, `CuentaRepository.java`, `TransferenciaRepository.java`
- **chore(cleanup)**: Eliminación de controladores y servicios Java antiguos reemplazados por módulos hexagonales

### Dependencies
- **chore(deps)**: Actualización de Spring Boot 4.0.4→4.0.6 en `pom.xml`
- **chore(deps)**: Actualización de Java 25 en `pom.xml`
- **chore(deps)**: Actualización de SpringDoc OpenAPI 3.0.2→3.0.3 en `pom.xml`
- **chore(deps)**: Actualización de OpenPDF 3.0.0→3.0.3 en `pom.xml`
- **chore(deps)**: Actualización de MySQL Connector 9.5.0→9.6.0 en `pom.xml`
- **chore(deps)**: Nueva dependencia `feign-jackson` para soporte de serialización JSON en clientes Feign
- **chore(deps)**: Nueva dependencia `commons-fileupload` 1.6.0 en dependencyManagement

## [2.2.0] - 2026-03-23

### Changed
- **refactor(migracion)**: Migración completa del modelo `ArticuloMovimiento` de legacy a Java para mejor compatibilidad con Spring Boot 4 y consistencia con el resto de la codebase
- **refactor(clients)**: Simplificación masiva de los builders de clientes Feign (`ArticuloBarraClientBuilder`, `ArticuloClientBuilder`, `CuentaClientBuilder`, `ParametroClientBuilder`) eliminando la configuración compleja de SpringEncoder/SpringDecoder que ya no es necesaria con Spring Cloud 2025
- **refactor(excepciones)**: Migración de `StockMovimientoException` de legacy a Java, manteniendo ambas variantes para compatibilidad hacia atrás
- **refactor(servicios)**: Adición de `@RequiredArgsConstructor` y eliminación de constructores manuales en `ArticuloMovimientoService`, `StockMovimientoService`
- **refactor(impresion-fiscal)**: Extensión del endpoint de impresión fiscal con nuevo parámetro `stockMovimientoId` para obtener datos de stock y artículos asociados
- **chore(deps)**: Actualización de legacy de 2.3.0 a 2.3.20 en `pom.xml`

### Features
- **feat(impresion-fiscal)**: Nuevo endpoint `/fiscal/{ipAddress}/{hWnd}/{clienteId}/{comprobanteId}/{comprobanteOrigenId}/stock/{stockMovimientoId}` que incluye datos de stock y artículos asociados
- **feat(repositories)**: Nuevos métodos de consulta `findAllByStockMovimientoId` en `ArticuloMovimientoRepository` y `findByStockMovimientoId` en `StockMovimientoRepository`

## [2.1.1] - 2026-02-27

### Changed
- **refactor(facturacion-exportacion)**: Cambio del tipo de dato `cantidad` de `Integer` a `BigDecimal` en `FacturaExportacionFacturadorItem` para permitir cantidades decimales en facturación de exportación
- **refactor(facturacion-exportacion)**: Ajuste en `FacturaElectronicaExportacionService` para usar `BigDecimal` directamente sin conversión a `int`
- **refactor(service)**: Simplificación de inyección de dependencias en `ArticuloMovimientoTemporalService` usando `@RequiredArgsConstructor`

## [2.1.0] - 2026-01-31

### Features
- **feat(invoicedata)**: Nuevo módulo hexagonal `InvoiceData` para consulta de datos de facturación completa
  - Nuevo endpoint GET `/api/core/invoiceData/{clienteMovimientoId}` para obtener datos completos de factura
  - Modelo de dominio `InvoiceData` con información de cliente, movimiento, CAE y comprobante asociado
  - Implementación completa de arquitectura hexagonal:
    - `domain/`: Modelos y puertos de entrada
    - `application/`: Servicios y casos de uso
    - `infrastructure/`: DTOs, mappers y controlador REST
  - Mappers para transformación de entidades: `InvoiceDataMapper`, `ClienteMovimientoMapper`, `RegistroCaeMapper`, `ArticuloMovimientoMapper`, `ClienteMapper`, `EmpresaMapper`, `ComprobanteMapper`, `ComprobanteAfipMapper`, `MonedaMapper`, `ConceptoFacturadoMapper`
  - DTOs de respuesta estructurados: `InvoiceDataResponse`, `ClienteMovimientoResponse`, `RegistroCaeResponse`, etc.

### Changed
- **refactor(stock)**: Simplificación de inyección de dependencias en `StockService` usando `@RequiredArgsConstructor` en lugar de constructor manual
- **refactor(empresa)**: Adición de método `toResponse()` en `EmpresaMapper` para soporte de DTOs en módulo invoicedata
- **refactor(model)**: Actualización de `ClienteMovimiento` y `RegistroCae` con nuevas relaciones JPA para soporte de consultas enriquecidas

### Dependencies
- **chore(deps)**: Actualización de Spring Boot 4.0.1 → 4.0.2

## [2.0.0] - 2026-01-27

### 🚀 Major Release - Migración de Legajo a Arquitectura Hexagonal y Actualización de Dependencias

#### Breaking Changes
- **refactor!**: Migración completa del módulo Legajo a arquitectura hexagonal con reorganización de paquetes y estructura de código
- **refactor!**: Actualización de dependencias principales: Spring Boot 3.5.8→4.0.1, Spring Cloud 2025.0.0→2025.1.0
- **refactor!**: Eliminación de tipos nullable en repositorios legacy para mayor consistencia de tipos
- **refactor!**: Cambio en modelo Reserva de `Date` a `LocalTime` para `horaVencimiento`
- **refactor!**: Actualización de builders de clientes Feign para compatibilidad con Spring Boot 4

#### Features
- **feat**: Nueva implementación hexagonal del módulo Legajo con controladores, servicios, dominio y persistencia
- **feat**: Nuevos endpoints para Legajo: GET `/legajo/`, GET `/legajo/{legajoId}`, POST `/legajo/search`, POST `/legajo/`
- **feat**: Servicio de búsqueda avanzada de legajos por substrings
- **feat**: Actualización de dependencias: ZXing 3.5.3→3.5.4, MySQL Connector 9.4.0→9.5.0, SpringDoc OpenAPI 2.8.10→3.0.1, ModelMapper 3.2.4→3.2.6, Commons Lang 3.18.0→3.20.0

#### Changed
- **refactor**: Reorganización completa del módulo Legajo a estructura hexagonal:
  - `hexagonal/legajo/domain/` - Modelos de dominio y puertos
  - `hexagonal/legajo/application/` - Casos de uso y servicios
  - `hexagonal/legajo/infrastructure/` - Adaptadores web y persistencia

## [1.3.0] - 2026-01-08

### Features
- feat: Nuevo endpoint GET `/proveedorMovimiento/resumen/iva/compras/posicion/{anho}/{mes}` para obtener resumen mensual de IVA de compras por posición, basado en cambios en `ProveedorMovimientoService.java`, adición de `ResumenIvaComprasMensualPosicion.java`, `ResumenIvaComprasMensualPosicionResponse.java` y mappers asociados

## [1.2.0] - 2026-01-07

### Features
- feat: Mejora en el endpoint GET `/proveedorMovimiento/resumen/iva/compras/{anho}/{mes}` para incluir el nombre del negocio en la respuesta, basado en cambios en `ProveedorMovimientoController.java`, adición de `ResumenIvaComprasMensualResponse.java` y `ResumenIvaComprasMensualDtoMapper.java`

## [1.1.0] - 2026-01-06

### Features
- feat: Nuevo endpoint GET `/proveedorMovimiento/resumen/iva/compras/{anho}/{mes}` para obtener resumen mensual de IVA de compras
- feat: Nuevo modelo de dominio `ResumenIvaComprasMensual` para cálculos de IVA
- feat: Mejora en la lógica de cálculo de neto en `RegistraFacturaService` incluyendo neto105

### Changed
- refactor: Migración completa de `ProveedorMovimiento` a arquitectura hexagonal con dominio y puertos
- refactor: Actualización de imports en controladores para usar modelos de dominio en lugar de entidades
- refactor: Refactorización de `NegocioController` con parámetro renombrado `negocioIdExcept`
- refactor: Eliminación de anotación `@Transient` innecesaria en `NegocioEntity`
- refactor: Agregado `@RequiredArgsConstructor` en `TransaccionFacturaProgramaDiaService`

### Fixed
- fix: Ajuste en cálculo de neto para incluir neto105 en facturación

## [1.0.0] - 2026-01-01

### 🚀 Major Release - Migración a Arquitectura Hexagonal

#### Breaking Changes
- **refactor!**: Migración completa a arquitectura hexagonal con reorganización de paquetes y estructura de código
- **refactor!**: Actualización de dependencias principales: Java 24→25, Java 25, Spring Boot 3.5.6→3.5.8
- **refactor!**: Reorganización de módulos de facturación en estructura hexagonal nacional/exportación

#### Features
- **feat**: Nueva funcionalidad de facturación de exportación con `FacturaExportacionResponse` y `FacturaExportacionFacturadorItem`
- **feat**: Sistema de ajuste automático de netos e ivas para comprobantes de compras
- **feat**: Implementación de relación proveedor-movimiento con negocio
- **feat**: Optimización del pool de conexiones de base de datos (10→20 conexiones)
- **feat**: Migración completa del módulo Empresa a arquitectura hexagonal
- **feat**: Migración completa del módulo ProveedorMovimiento a arquitectura hexagonal
- **feat**: Migración completa del módulo de facturación nacional a arquitectura hexagonal

#### Changed
- **refactor**: Reorganización completa de servicios a arquitectura hexagonal:
  - `hexagonal/empresa/` - Gestión de empresas
  - `hexagonal/facturacion/arca/nacional/` - Facturación nacional
  - `hexagonal/facturacion/arca/exportacion/` - Facturación de exportación
- **refactor**: Actualización de imports y referencias en toda la codebase
- **refactor**: Mejora en nomenclatura de campos (hwnd→hWnd, impositivoId→idImpositivo)
- **refactor**: Actualización de Dockerfile y CI/CD pipeline para Java 25
- **refactor**: Mejora en manejo de transacciones con `@Transactional` en servicios críticos

#### Fixed
- **fix**: Corrección de nombres de atributos en entidades legacy para mayor consistencia
- **fix**: Optimización de consultas en `ArticuloMovimientoTemporalRepository` con nombres de métodos corregidos
- **fix**: Mejora en manejo de errores y logging en servicios de facturación

#### Performance
- **perf**: Incremento del pool de conexiones Hikari de 10 a 20 para mejor throughput
- **perf**: Optimización de métodos de búsqueda con nombres de campos corregidos

#### Documentation
- **docs**: Actualización de diagramas de arquitectura para reflejar la nueva estructura hexagonal
- **docs**: Documentación de nuevos endpoints de facturación de exportación
- **docs**: Actualización de badges y versiones en README.md

## [0.13.3] - 2025-11-04

### Fixed
- fix: Corrección del orden de sorting en `ProveedorMovimientoService.findAllByRegimenInformacionCompras` para usar `prefijo` en lugar de `puntoVenta`

## [0.13.2] - 2025-11-04

### Features
- feat: Nuevo endpoint GET `/proveedorMovimiento/arca/regimen/informacion/compras/{desde}/{hasta}` para obtener movimientos de régimen de información de compras
- feat: Nuevo método `findAllByRegimenInformacionCompras` en `ProveedorMovimientoService` con filtrado por IVA
- feat: Nuevo método `findAllByFechaContableBetweenAndComprobanteLibroIva` en `ProveedorMovimientoRepository`

### Changed
- refactor: Refactorización de `ProveedorMovimientoController` con `@RequiredArgsConstructor` y eliminación de constructor manual
- refactor: Refactorización de `ProveedorMovimientoService` con `@RequiredArgsConstructor` y eliminación de constructor manual

## [0.13.1] - 2025-11-02

### Features
- feat: Nuevo endpoint GET `/clienteMovimiento/arca/regimen/informacion/ventas/{desde}/{hasta}` para obtener movimientos de régimen de información de ventas
- feat: Nuevo método `findAllByRegimenInformacionVentas` en `ClienteMovimientoService` con filtrado por IVA
- feat: Nuevo método `findAllByFechaComprobanteBetweenAndComprobanteLibroIva` en `ClienteMovimientoRepository`

### Changed
- refactor: Refactorización de `ClienteMovimientoController` con `@RequiredArgsConstructor` y eliminación de constructor manual

## [0.13.0] - 2025-10-31

### Features
- feat: Nuevo endpoint POST `/clienteMovimiento/` para crear movimientos de cliente
- feat: Nuevos endpoints en LegajoController: `findByLegajoId` y `findAllBySearch`
- feat: Nuevo servicio `LegajoSearchService` para búsqueda de legajos
- feat: Nueva excepción `LegajoException` para manejo de errores de legajoEntity
- feat: Actualización de dependencias: Java 25

### Changed
- refactor: Migración masiva de modelos de legacy a Java (ClienteMovimiento, CuentaMovimiento, RegistroCae)
- refactor: Refactorización completa de controladores con `@RequiredArgsConstructor` y eliminación de constructores manuales
- refactor: Mejora en manejo de excepciones con `ResponseStatusException` en controladores
- refactor: Actualización de builders a métodos estáticos en modelos Java
- refactor: Centralización de serialización JSON usando `Jsonifier` en lugar de `JsonMapper`
- refactor: Optimización de constructores en servicios facade (ArticulosService, ContabilidadService, etc.)

### Fixed
- fix: Corrección de códigos de métodos de pago para tarjetas prepagas en FacturacionService

## [0.11.0] - 2025-09-22

### Breaking Changes
- refactor!: Eliminación de requisitos de autenticación, ahora permite todas las solicitudes sin verificaciones de seguridad.

### Changed
- refactor: Centralización de la serialización JSON usando Jsonifier en los modelos Negocio y FacturacionDto.
- refactor: Agregado @RequiredArgsConstructor y eliminación del constructor manual en NegocioService.
- refactor: Mejora del logging en NegocioService y FacturacionElectronicaService.
- refactor: Simplificación de la creación de ResponseEntity en MakeFacturaController.

## [0.10.0] - 2025-09-21

### Features
- feat: Nuevos tipos de tarjeta de crédito agregados: "Visa Prepaga" y "Mastercard Prepaga"
- feat: Actualización de dependencias principales (Spring Boot 3.5.5→3.5.6, Java 25, OpenPDF 2.4.0→3.0.0)

### Breaking Changes
- refactor!: Eliminación de configuración CORS centralizada (WebConfig.java)
- refactor!: Nueva configuración de seguridad con autenticación básica requerida
- refactor!: Eliminación de BasicAuthRequestInterceptor

### Changed
- refactor: Refactorización masiva de controladores para usar inyección de dependencias con Lombok
- refactor: Mejora en serialización JSON usando Jsonifier centralizado
- chore: Eliminación de configuración CORS de bootstrap.yml

### Docs
- docs: Actualización de versiones de dependencias en README.md

## [0.9.0] - 2025-09-07
### Features
- feat: Servicios de consulta `SaldoArticuloService`, `SaldoFechaService` y `ClienteSearchService` agregados.
- feat: Utilidad de serialización JSON centralizada (`Jsonifier`).
- feat: Nueva clase utilitaria `ToolService` para operaciones de fecha, texto y números.

### Changed
- refactor: Migración y reorganización de servicios y repositorios de `api.rest` a `core.service`.
- refactor: Limpieza y mejora de interfaces en repositorios y servicios.
- refactor: Mejora de logging y trazabilidad en servicios críticos.
- chore: Eliminación definitiva de archivos y configuraciones obsoletas (`logback-spring.xml`, `application.yml`, etc).

### Fixed
- fix: Ajustes menores en la configuración de CORS y Consul en `bootstrap.yml`.

### Docs
- docs: Actualización de diagramas y documentación para reflejar la nueva arquitectura y utilidades.
## [0.8.0] - 2025-08-31
### Features
- feat: Nuevo servicio `ProductsService` para procesamiento de productos y vouchers.
- feat: Nuevos endpoints REST para consulta de numeración de facturas y notas de crédito.
- feat: Servicios de consulta `SaldoArticuloService` y `SaldoFechaService` agregados.

### Changed
- chore: Actualización de dependencias principales:
    - spring-boot-starter-parent 3.5.5
    - Java 25
    - openpdf 2.4.0
    - springdoc-openapi-starter-webmvc-ui 2.8.10
- refactor: Mejora de lógica y trazabilidad en `ClienteMovimientoService` y `ComprobanteService`.
- refactor: Ajuste de endpoints y lógica de numeración de comprobantes.
- refactor: Limpieza y mejora de interfaces en repositorios y servicios.
- chore: Eliminación de `logback-spring.xml` y migración completa a configuración YAML.

### Fixed
- fix: Corrección de lógica en la obtención del próximo número de factura y nota de crédito.
- fix: Ajustes en la configuración de CORS y Consul en `bootstrap.yml`.

### Docs
- docs: Actualización de diagramas y documentación para reflejar la nueva arquitectura y dependencias.

## [0.7.2] - 2025-08-27
### Changed
- refactor: Mejora de lógica y trazabilidad en `ClienteMovimientoService` y `ComprobanteService`.
- refactor: Se agrega método `findAllAsociables()` y se ajusta la consulta de comprobantes asociables.
- refactor: Limpieza y mejora de interfaces en `ComprobanteRepository`.
## [0.7.1] - 2025-08-09
### Features
- feat: Nuevos servicios de consulta (`ClienteSearchService`, `SaldoArticuloService`, `SaldoFechaService`).
- feat: Centralización y mejora de utilidades en `ToolService`.
- feat: Refactor y migración de servicios y repositorios de `api.rest` a `core.service`.
- feat: Mejora de logging y trazabilidad en servicios críticos.

### Changed
- chore: Actualización de dependencias principales:
    - spring-boot-starter-parent 3.5.4
    - openpdf 2.2.4
    - mysql-connector-j 9.4.0
- refactor: Cambios en endpoints de controladores para mayor consistencia REST.
- refactor: Mejoras en la gestión de comprobantes y facturación.

### Removed
- chore: Eliminación de archivos y configuraciones obsoletas (`logback-spring.xml`, `application.yml`, scripts de Maven Wrapper, etc).

### Docs
- docs: Actualización de diagramas y documentación para reflejar la nueva arquitectura y dependencias.
## [0.7.0] - 2025-07-24
### Features
- feat: Integración de Spring Security y configuración de seguridad básica.
- feat: Migración de Eureka a Consul para service discovery (dependencias, configuración y código).
- feat: Incorporación de Jacoco para cobertura de tests.
- feat: Nuevos servicios de consulta (`ClienteSearchService`, `SaldoArticuloService`, `SaldoFechaService`).
- feat: Integración de SonarCloud en CI/CD.

### Changed
- refactor: Migración y reorganización de servicios y repositorios de `api.rest` a `core.service`.
- refactor: Centralización y mejora de utilidades en `ToolService`.
- refactor: Mejora de logging y trazabilidad en servicios críticos.
- refactor: Actualización de dependencias principales (`spring-boot-starter-actuator`, `spring-cloud-starter-consul-discovery`, `commons-lang3`).

### Removed
- chore: Eliminación de archivos y configuraciones obsoletas (`logback-spring.xml`, `application.yml`, `Dockerfile.local`, scripts de Maven Wrapper).
- chore: Eliminación de anotaciones y configuraciones de Eureka.

### Docs
- docs: Actualización de diagramas y documentación para reflejar la migración a Consul y la integración de seguridad.

## [0.6.1] - 2025-07-17
### Changed
- refactor: Centralización de la serialización JSON en entidades de dominio (`jsonify()` en modelos legacy).
- refactor: Simplificación y mejora del logging en servicios de facturación y contabilidad.
- chore: Eliminación de métodos privados de logging redundantes en servicios Java.
- chore: Ajuste de niveles de log y trazabilidad en operaciones críticas.

## [0.6.0] - 2025-07-15

### Features
- **Gestión de Cotizaciones:** Implementado sistema para la gestión de cotizaciones de monedas y conversión de valores.
- **Facturación y Transacciones:**
    - Añadido endpoint para el registro de transacciones.
    - Implementado módulo de "snapshots" para el estado de las transacciones.
    - Mejorado el control de comprobantes faltantes.
    - Añadido campo `diferenciaWeb` para control de montos en facturas web.
- **Artículos y Productos:**
    - Implementada la replicación de códigos de barras y la actualización de artículos.
    - Añadida lógica para priorizar tarifas de feriados en la determinación de SKUs.
- **Informes y Consultas:**
    - Añadido endpoint para obtener un resumen diario de asientos contables.
- **Gestión de Feriados:** Implementado CRUD para la entidad `Feriado`.

### Fixes
- **Cálculos y Precisión:** Corregidos problemas con el cálculo de decimales en la facturación.
- **Validaciones:** Añadida validación de nulos para productos infantiles.
- **Precios:** Corregido el cálculo del precio de artículos basado en la fecha.
- **Configuración:** Ajustada la configuración de CORS.

### Refactor
- **Facturación:** Refactorización profunda del sistema de facturación, incluyendo la migración de DTOs a Java y la reorganización de la lógica de negocio.
- **Cotizaciones:** Optimizado el manejo de cotizaciones para mejorar la precisión y el rendimiento.

## [0.5.1] - 2025-07-10

### Changed
- refactor(billing): migrar FacturacionDto a Java y mejorar lógica

### Docs
- docs(release): actualizar CHANGELOG y refactorizar DTO

## [0.5.0] - 2025-07-09

### Added
- feat(billing): add endpoint for transaction registration

### Changed
- refactor(controller): improve error handling in ClienteMovimiento
- refactor(billing): migrar FacturacionDto a Java y mejorar lógica

### Docs
- update CHANGELOG, README, and Dockerfile for new version

## [0.4.2] - 2025-07-08

### Docs
- Eliminar información de contacto personal del README.

## [0.4.1] - 2025-07-06

### Fixed
- fix(docs): restaurar pipeline y diagramas de documentación

## [0.4.0] - 2025-07-05

### Added
- feat(snapshot): Implementar módulo de snapshot y actualizar dependencias.
- feat(iva): Implementar módulo de posición IVA y mejorar sistema de facturación.
- feat(comprobantes): Optimizar control de comprobantes faltantes y actualizar dependencias.
- feat(consolidado): Implementar control de comprobantes faltantes.
- feat(transferencias): Implementar regeneración de transferencias entre negocios.
- feat(api): Implementar HATEOAS y gestión de rubros/artículos.
- feat(currency): Implementar sistema de gestión de tipos de cambio.
- feat(articulos): Implementar actualización de artículos.
- feat(billing): Añadir campo diferenciaWeb para control de montos en facturas web.
- feat(replication): Implementar sistema de replicación de códigos de barras.
- feat(ProductoSkuService): Priorizar día feriado en determinación de tarifa.
- Agregado de la posibilidad de replicación automática de un artículo a otros negocios.

### Fixed
- fix(cors): Ajustar configuración CORS para permitir acceso desde core-service.
- fix(api): Mejorar manejo de errores y logging en impresión fiscal.

### Changed
- refactor(comprobantes): Optimizar control de comprobantes faltantes y mapeo JPA.
- refactor(facturacion): Reorganizar proceso de facturación y actualizar dependencias.
- refactor(cotizaciones): Implementar sistema de conversión entre monedas.
- refactor(monedas): Optimizar manejo de cotizaciones y precisión decimal.
- docs: Actualización de documentación y mejoras en el servicio core.
- docs(readme): Actualizar documentación y enlaces de API.
- Eliminación de copia de actualización de precios.
- Dominio eterea.termaliasa.com agregado a los origenes permitidos de CORS.
- Configuracion de CORS.

## [0.3.0] - 2025-07-02
### Feat
- implementar HATEOAS y gestión de rubros/artículos
- implement exchange rate management system

## [0.7.1] - 2025-08-05
- chore: Actualización de dependencias principales:
  - spring-boot-starter-parent a 3.5.4
  - openpdf a 2.2.4
  - mysql-connector-j a 9.4.0
- fix: Correcciones menores y refactorizaciones internas (ver historial de commits)
### Fix
- ajustar configuración CORS para permitir acceso desde core-service
- mejorar manejo de errores y logging en impresión fiscal
- implementar sistema de conversión entre monedas
- optimizar manejo de cotizaciones y precisión decimal

### Docs
- actualización de documentación y mejoras en el servicio core - Actualización del README con nuevas badges y secciones - Creación del CHANGELOG siguiendo Keep a Changelog - Optimización del comando Maven en GitHub Actions - Mejora en el manejo de conceptos facturados - Actualización de versiones de dependencias - Closes #1
- actualizar documentación y enlaces de API
