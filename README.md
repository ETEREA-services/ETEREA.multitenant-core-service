# ETEREA.multitenant-core-service

[![ETEREA.multitenant-core-service CI](https://github.com/ETEREA-services/ETEREA.multitenant-core-service/actions/workflows/maven.yml/badge.svg?branch=main)](https://github.com/ETEREA-services/ETEREA.multitenant-core-service/actions/workflows/maven.yml)
[![Java](https://img.shields.io/badge/Java-25-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk25-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.2-green.svg)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.1.4-green.svg)](https://spring.io/projects/spring-cloud)
[![Spring Modulith](https://img.shields.io/badge/Spring%20Modulith-2.1.2-green.svg)](https://spring.io/projects/spring-modulith)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-6DB33F.svg)](https://spring.io/projects/spring-security)
[![OpenAPI](https://img.shields.io/badge/OpenAPI-3.2.0-blue.svg)](https://springdoc.org/)
[![MySQL](https://img.shields.io/badge/MySQL-26.10.0-orange.svg)](https://www.mysql.com/)
[![H2](https://img.shields.io/badge/H2-tests-blue.svg)](https://www.h2database.com/)
![License](https://img.shields.io/badge/License-Proprietary-red.svg)
[![Version](https://img.shields.io/badge/Version-4.0.0-blue.svg)](https://github.com/ETEREA-services/ETEREA.multitenant-core-service/releases)

## Descripción

Servicio Core multi-tenant para la gestión financiera y contable, implementado con **arquitectura hexagonal y modular (Spring Modulith)**. Proporciona:

- **Multi-tenancy**: Ruteo dinámico de bases de datos por tenant mediante header `X-TENANT-ID`
- **Arquitectura Hexagonal y Modular**: Puertos, adaptadores y casos de uso con fronteras de módulo verificadas en el build
- **Autenticación JWT**: Login común, refresh tokens en cookie `HttpOnly`, lockout y blanqueo con contraseña temporal
- **Facturación Nacional y de Exportación**: Soporte completo para facturación electrónica argentina
- **Gestión de Artículos, Comprobantes, Cuentas, Transferencias**: Módulos hexagonales migrados a Java
- **Contabilidad**: Plan de cuentas jerárquico, ejercicios y coeficientes de inflación
- **Reportes**: Vista previa JSON y PDF generados desde un modelo único (`ReporteTabla`)
- **InvoiceData**: Consulta de datos completos de facturación
- **Gestión de Legajos y Proveedores**: Con arquitectura hexagonal
- **Ajuste automático** de netos e ivas en comprobantes
- **Utilidades centralizadas**: serialización JSON, operaciones de fecha y texto

## Stack Tecnológico

- **Java 25**
- **Spring Boot 4.1.2** con Spring Cloud 2025.1.4
- **Arquitectura Hexagonal y Modular** con Spring Modulith 2.1.2
- **Spring Security 7** con JWT resource server y Argon2
- **Consul Discovery** y **OpenFeign**
- Spring Data JPA + Hibernate
- Spring HATEOAS
- Spring WebFlux
- Spring Validation
- MySQL en runtime y H2 en tests

### Utilidades
- OpenPDF 3.0.5 (Generación de PDFs)
- ZXing 3.5.4 (Códigos de Barras y QR)
- Lombok
- ModelMapper 3.2.8
- Caffeine Cache
- MySQL Connector 26.10.0
- Jacoco 0.8.16 (Cobertura de tests)
- Apache Commons Lang3 3.21.0

### Multi-tenancy
- Ruteo dinámico de DataSources por tenant (`AbstractRoutingDataSource`)
- Pool HikariCP bajo demanda: 8 conexiones máximo, 1 mínimo y evicción a los 15 minutos de inactividad
- Header HTTP `X-TENANT-ID` para identificación del tenant
- Integración con `NegocioBrain` para lookup de conexiones
- Métricas Micrometer del ciclo de vida de los pools por tenant

### Documentación
- SpringDoc OpenAPI UI 3.2.0

## Arquitectura

El proyecto utiliza una **arquitectura hexagonal dentro de módulos cerrados** (Spring Modulith). Cada módulo de negocio vive en `eterea.tenant.service.modules.<nombre>` y solo expone su paquete `api`; el kernel (`configuration`, `tool`) no depende de los módulos. La guía completa está en [`docs/modules.md`](docs/modules.md).

### Módulos de Negocio
- **`modules/platform/`**: Negocio actual, clientes, usuarios, parámetros y contratos compartidos
- **`modules/compras/`**: Proveedores y movimientos de compras
- **`modules/contable/`**: Cuenta, asientos, ejercicios y coeficientes de inflación
- **`modules/reporte/`**: Modelo transversal de reportes y render PDF
- **`modules/stock/`**: Artículos, movimientos, inventario y saldos
- **`modules/ventas/`**: Comprobantes, vouchers y facturación
- **`modules/reserva/`**: Reservas, hotelería y habitaciones
- **`modules/tesoreria/`**: Caja, cierres y transferencias
- **`modules/personal/`**: Legajos

### Módulos Brain
- **`brain/empresa/`**: Gestión CRUD de empresas Brain
- **`brain/negocio/`**: Gestión CRUD de negocios Brain y búsqueda por tenant
- **`brain/auth/`**: Login JWT, refresh tokens y cookies HttpOnly
- **`brain/usuarios/usuario/`**: Gestión de usuarios Brain, cambio y blanqueo de contraseña
- **`brain/usuarios/usuario_empresa/`**: Vinculación usuario-empresa Brain con validación, reactivación y desactivación en cascada
- **`brain/usuarios/usuario_negocio/`**: Vinculación usuario-negocio Brain con consulta de todos los negocios del usuario y validación de empresa activa

### Estructura por Tecnología
- **Modelos de Dominio**: Implementados en Java siguiendo principios de Clean Architecture
- **Entidades JPA**: Implementadas en Java
- **Servicios y Controladores**: Implementados en Java
- **Casos de Uso y Puertos**: Siguiendo principios de Clean Architecture

> **Nota:** Desde la versión 1.0.0, el proyecto implementa arquitectura hexagonal completa con migración de todos los módulos críticos. A partir de la 4.0.0 el código es 100 % Java y las fronteras de módulo se verifican con `ModularityTests`.

## Módulos Principales

### Facturación
- **Facturación Nacional**: Sistema completo de facturación electrónica AFIP
- **Facturación de Exportación**: Nueva funcionalidad para comprobantes de exportación
- **Ajuste Automático**: Sistema de corrección automática de netos e ivas

### Gestión Empresarial
- **Transferencias**: Gestión de transferencias entre negocios
- **Movimientos**: Control de movimientos contables y valores
- **Cotizaciones**: Administración de cotizaciones de monedas

### Gestión de Productos
- **Artículos**: Gestión de artículos y sus listas de precios
- **Rubros**: Categorización y gestión de rubros comerciales
- **Inventario**: Control de stock y movimientos

### Contabilidad
- **Plan de cuentas**: Árbol jerárquico, alta, edición, baja y detalle por número de cuenta
- **Ejercicios**: CRUD, sugerencia del siguiente ejercicio y bloqueo/desbloqueo
- **Coeficientes de inflación**: Consulta y actualización por año y mes con validación de dominio
- **Reporte del plan de cuentas**: Vista previa JSON y PDF

### Reportes
- **Modelo único**: `ReporteTabla` alimenta la vista previa y el PDF
- **PDF OpenPDF**: A4, cabecera repetida, numeración `Página X de Y` y wrapping de celdas
- **Extensible**: Cada módulo de negocio expone su reporte bajo `/api/tenant/core/reporte/**`

### Consulta de Datos de Facturación
- **InvoiceData**: Endpoint para obtener datos completos de facturas por movimiento de cliente
- Integración con módulos de empresa, cliente, comprobante y CAE

### Autenticación y Seguridad
- **Login común**: `POST /api/brain/auth/login` emite access JWT y refresh token en cookie `HttpOnly`
- **Refresh y logout**: `POST /api/brain/auth/refresh` y `POST /api/brain/auth/logout`
- **Sesión actual**: `GET /api/brain/auth/me`
- **Blanqueo**: `POST /api/brain/core/usuario/{usuarioId}/password-reset` (solo `ADMIN`) genera una contraseña temporal
- **Autorización**: `/api/tenant/core/**` exige JWT y asignaciones activas para el tenant del header

### Servicios Transversales
- **Service Discovery**: Integración con Consul
- **Fronteras de módulo**: Verificadas por Spring Modulith en `ModularityTests`
- **Cobertura de tests**: Jacoco para análisis de cobertura
- **Utilidades**: Herramientas centralizadas para operaciones comunes

### API Brain
- Empresas: `/api/brain/core/empresa`
- Negocios: `/api/brain/core/negocio`, incluida la búsqueda por tenant
- Usuarios: `/api/brain/core/usuario`, cambio y blanqueo de contraseña
- Login común: `/api/brain/auth/login`, refresh, logout y `me` con JWT + cookie HttpOnly
- Vinculación Usuario-Empresa: `/api/brain/core/usuario-empresa`
- Vinculación Usuario-Negocio: `/api/brain/core/usuario-negocio`
- Negocios por usuario: `GET /api/brain/core/usuario-negocio/usuario/{usuarioId}`
- Negocios por empresa: `GET /api/brain/core/negocio/empresa/{empresaId}`

## Configuración del Proyecto

### Requisitos
- **JDK 25**
- Maven 3.8+
- MySQL 8+

### Instalación
```bash
git clone https://github.com/ETEREA-services/ETEREA.multitenant-core-service.git
cd ETEREA.multitenant-core-service
mvn clean install
```

### Ejecución
`APP_JWT_SECRET` es obligatorio (mínimo 32 caracteres) y debe coincidir con el gateway.

```bash
APP_SERVER=localhost:3306 \
APP_DATABASE=eterea-brain \
APP_USER=root \
APP_PASSWORD=... \
APP_CONSUL_HOST=localhost \
APP_CONSUL_PORT=8500 \
APP_JWT_SECRET='cambiar-por-un-secreto-de-32-bytes-minimo' \
APP_JWT_ISSUER=eterea.multitenant \
mvn spring-boot:run
```

### Docker
```bash
# Construir imagen
docker build -t eterea/multitenant-core-service .

# Ejecutar contenedor
docker run -p 8080:8080 eterea/multitenant-core-service
```

## Documentación API

La documentación de la API está disponible en:

- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
- **OpenAPI YAML**: http://localhost:8080/v3/api-docs.yaml

## Documentación Adicional

- [Documentación del Proyecto](https://eterea-services.github.io/ETEREA.multitenant-core-service/)
- [Wiki del Proyecto](https://github.com/ETEREA-services/ETEREA.multitenant-core-service/wiki)
- [Módulos y fronteras](docs/modules.md)
- [AGENTS.md](AGENTS.md)
- [CHANGELOG](CHANGELOG.md)

## Contribución

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am '''Agrega nueva funcionalidad'''`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crea un Pull Request

## Estado del Proyecto

El proyecto está en **desarrollo activo** con arquitectura hexagonal y modular estable. Ver [GitHub Projects](https://github.com/ETEREA-services/ETEREA.multitenant-core-service/projects) para el roadmap.

## Licencia

Este proyecto es privado y de uso exclusivo de Termalia S.A.

## Características

### Arquitectura
- ✅ **Arquitectura Hexagonal** completa con puertos y adaptadores
- ✅ **Módulos cerrados** con Spring Modulith y verificación en `ModularityTests`
- ✅ **Proyecto 100% Java**
- ✅ **Clean Architecture** con separación clara de responsabilidades

### Funcionalidades Core
- ✅ **Multi-tenancy** con ruteo dinámico de DataSources por tenant
- ✅ **Facturación Nacional y Exportación** con integración AFIP
- ✅ **Ajuste Automático** de comprobantes
- ✅ **Gestión de Artículos** con arquitectura hexagonal (Java)
- ✅ **Gestión de Movimientos de Artículos** con arquitectura hexagonal (Java)
- ✅ **Gestión de Comprobantes** con arquitectura hexagonal (Java)
- ✅ **Gestión de Cuentas Contables** con árbol jerárquico y reporte del plan de cuentas
- ✅ **Gestión de Ejercicios** con bloqueo/desbloqueo y sugerencia del siguiente
- ✅ **Coeficientes de Inflación** por año y mes con validación de dominio
- ✅ **Reportes** con vista previa JSON y PDF OpenPDF desde un modelo único
- ✅ **Gestión de Transferencias** con arquitectura hexagonal (Java)
- ✅ **Gestión de Empresas** con arquitectura hexagonal
- ✅ **Gestión de Negocios (sucursales)** con arquitectura hexagonal
- ✅ **Gestión de Legajos** con arquitectura hexagonal
- ✅ **Consulta de Datos de Facturación (InvoiceData)** con arquitectura hexagonal
- ✅ **Gestión de Proveedores** con arquitectura hexagonal
- ✅ **Gestión Brain de Empresas (EmpresaBrain)** con arquitectura hexagonal
- ✅ **Gestión Brain de Negocios (NegocioBrain)** con arquitectura hexagonal
- ✅ **Gestión Brain de Usuarios (UsuarioBrain)** con cambio y blanqueo de contraseña
- ✅ **Autenticación JWT (BrainAuth)** con refresh token en cookie HttpOnly, lockout y contraseña temporal
- ✅ **Vinculación Brain Usuario-Empresa (UsuarioEmpresaBrain)** con activación/desactivación
- ✅ **Vinculación Brain Usuario-Negocio (UsuarioNegocioBrain)** con datos del negocio
- ✅ **Control de Movimientos** contables y valores

### Infraestructura
- ✅ **Multi-tenancy** con `AbstractRoutingDataSource` y pool HikariCP bajo demanda por tenant (8 máximo, 1 mínimo, evicción a los 15 minutos)
- ✅ **Métricas** Micrometer del ciclo de vida de los pools por tenant
- ✅ **Documentación API** con OpenAPI 3.2
- ✅ **Service Discovery** con Consul
- ✅ **Cache distribuido** con Caffeine
- ✅ **Transacciones distribuidas** con JPA
- ✅ **Cobertura de tests** con Jacoco
- ✅ **CI/CD** con GitHub Actions

### Utilidades
- ✅ **Generación de PDFs** con OpenPDF
- ✅ **Códigos de barras y QR** con ZXing
- ✅ **Mapeo de objetos** con ModelMapper
- ✅ **Serialización JSON** centralizada
- ✅ **Herramientas de fecha y texto** utilidades

## Notas Importantes

- El proyecto utiliza arquitectura hexagonal desde la versión 1.0.0
- Todo el código está migrado a Java 25; ya no se compila Kotlin
- Los casos de uso y controladores siguen principios de Clean Architecture
- **Multi-tenancy**: Los endpoints bajo `/api/tenant/**` requieren el header `X-TENANT-ID` con el `tenantId` del negocio para el ruteo dinámico a su base de datos
- **Seguridad**: `/api/tenant/core/**` exige JWT válido y asignaciones activas (los `ADMIN` están exentos)
- Se requiere configuración de Consul para el registro de servicios
- La documentación de la API se genera automáticamente en tiempo de ejecución
- Todas las pruebas unitarias deben seguir la estructura hexagonal
