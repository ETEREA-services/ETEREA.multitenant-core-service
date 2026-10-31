# Módulos del core

## Objetivo

El core es **un solo deployable** que atiende los 90 negocios con routing de
datasource por tenant. La separación de módulos es **de código, no de
infraestructura**: no se crean servicios ni repositorios de GitHub por módulo.
La frontera se verifica en el build.

```text
eterea.tenant.service
├── configuration        kernel: tenant routing, datasources, seguridad
├── tool                 kernel: utilidades transversales y Auditable
├── brain                plataforma: empresa, negocio, usuario, asignaciones
└── modules              módulos de negocio (Spring Modulith)
    ├── platform         negocio actual, clientes, usuarios y contratos
    ├── compras          proveedores
    ├── contable         cuenta, asientos, ejercicios y coeficientes de inflación
    ├── stock            artículos, productos, inventario y saldos
    ├── ventas           comprobantes, vouchers y facturación
    ├── reserva          reservas y hotelería
    ├── tesoreria        caja, cierre y transferencias
    └── personal         legajo
```

## Estado actual

| Módulo | Paquete | Contenido | Estado |
|---|---|---|---|
| `platform` | `modules.platform` | negocio, clientes, usuarios, parámetros y contratos | Migrado |
| `compras` | `modules.compras` | proveedores | Migrado |
| `contable` | `modules.contable` | cuenta, asientos, ejercicios y coeficientes de inflación | Migrado |
| `stock` | `modules.stock` | artículos, productos, inventario y saldos | Migrado |
| `ventas` | `modules.ventas` | comprobantes, vouchers y facturación | Migrado |
| `reserva` | `modules.reserva` | reservas y hotelería | Migrado |
| `tesoreria` | `modules.tesoreria` | caja, cierre y transferencias | Migrado |
| `personal` | `modules.personal` | legajo | Migrado |

El antiguo paquete `eterea.tenant.service.tenant` ya no existe: todo su
contenido está distribuido en los módulos de negocio y en sus paquetes `legacy`
internos. Los módulos están cerrados y `ModularityTests.verify()` corre en cada
build.

## Contratos entre módulos

Los paquetes accesibles desde otro módulo son los `api` anotados con
`@NamedInterface("api")`. Los paquetes `legacy` internos de cada módulo también
están expuestos temporalmente como `api` para los servicios compartidos que
todavía no se extrajeron a contratos finos. Hoy existen:

| Contrato | Módulo | Uso |
|---|---|---|
| `NegocioActual` | `modules.platform.api` | Resolver el negocio del request sin depender de la tabla `negocio` |
| `CuentaQuery` / `CuentaResumen` | `modules.contable.api` | Consultar una cuenta desde stock y ventas |

Reglas:

1. **Un módulo no accede a los internals de otro.** Solo a su `api`.
2. **El dominio no depende de Spring, JPA, DTOs ni HTTP.**
3. **Dirección de dependencias:** `modules → kernel`. El kernel
   (`configuration`, `tool`) no importa módulos.
4. **No duplicar entidades JPA entre módulos.** Si un módulo necesita datos de
   otro, usa su `api` y guarda solo la FK en su propia tabla. Ejemplo: `stock`
   guarda `numeroCuentaVentas` y resuelve la cuenta con `CuentaQuery`.
5. **Nada de `@Query` ni `@RequestParam`** en código nuevo.
6. **Los controladores nuevos van bajo `/api/tenant/**`.** El
   `TenantInterceptor` resuelve el tenant por path además de por paquete.

## Dependencias compartidas

`Auditable` vive en el kernel (`eterea.tenant.service.tool`). Las clases
migradas desde el paquete `tenant` están en `modules.<módulo>.legacy` y los
controladores/servicios de cada feature se distribuyeron en su módulo.

Se eliminaron las relaciones JPA que cruzaban módulos en:

- `Voucher` → `Usuario`, `Cliente`, `Hotel`, `Reserva`, `Proveedor`
- `ReservaArticulo` → `ArticuloEntity`
- `ArticuloMovimientoPrevio` → `ArticuloEntity`, `CuentaEntity`
- `ValorMovimiento` → `CuentaEntity`
- `CuentaMovimiento` → `ComprobanteEntity`
- `TransferenciaEntity` → `ComprobanteEntity`
- `ProveedorMovimientoEntity` → `ComprobanteEntity`

Las referencias compartidas que quedan se resuelven por los paquetes `api` de
cada módulo. La deuda pendiente es reemplazar esos paquetes por contratos de
lectura finos (`*Query` / `*Resumen`) y agregar pruebas de contrato.

## Cómo agregar un módulo

1. Crear el paquete `modules.<nombre>` con su `package-info.java`:

   ```java
   @org.springframework.modulith.ApplicationModule(displayName = "Compras")
   package eterea.tenant.service.modules.compras;
   ```

2. Usar la estructura hexagonal ya establecida:

   ```text
   modules/<nombre>/
     api/                         contrato para otros módulos (si hace falta)
     <agregado>/domain/model
     <agregado>/domain/ports/in
     <agregado>/domain/ports/out
     <agregado>/application/service
     <agregado>/application/usecases
     <agregado>/infrastructure/web
     <agregado>/infrastructure/persistence
   ```

3. Exponer solo lo necesario en `api`:

   ```java
   @org.springframework.modulith.NamedInterface("api")
   package eterea.tenant.service.modules.contable.api;
   ```

4. Agregar el módulo a la aserción de `ModularityTests`.

5. Verificar:

   ```bash
   mvn -Dtest=ModularityTests test
   mvn -B verify
   ```

## Qué verifica `ModularityTests`

`ApplicationModules.of("eterea.tenant.service.modules").verify()` falla si:

- hay un ciclo de dependencias entre módulos;
- un módulo referencia tipos de un paquete interno de otro;
- se declara `allowedDependencies` y el código lo incumple.

El test también fija los módulos esperados: si alguien borra o renombra un
módulo sin actualizar la lista, el build falla.

## Mapa objetivo

```text
platform     negocio actual, clientes, usuarios, parámetros y contratos compartidos
contable     plan de cuentas, asientos, ejercicios, períodos, saldos, IVA, inflación
compras      proveedores, movimientos de compras, resumen de IVA compras
ventas       comprobantes, vouchers, facturación, cobranzas
reserva      reservas, hotelería y habitaciones
tesoreria    caja, cierres, anticipos, bancos, transferencias
stock        artículos, movimientos, inventario, saldos
personal     legajos, novedades
```

No hace falta crear todos ahora. Se agregan cuando se empieza a migrar cada
módulo del VB6.

## Migración de una slice legacy

1. Mover el paquete completo a `modules.<nombre>.<agregado>`.
2. Reemplazar el prefijo de paquete en `src/main/java` y `src/test`.
3. Resolver el acoplamiento con otros módulos: si la slice usaba una entidad o
   un DTO de otro módulo, crear el contrato en el `api` correspondiente.
4. Correr los tests del slice y `ModularityTests`.
5. No cambiar rutas HTTP ni contratos en la misma iteración.

## Relación con los repositorios de GitHub

- Un repositorio por **deployable**, no por módulo.
- Mientras el core sea un solo deployable, todo vive en
  `eterea.multitenant-core-service`.
- Solo se crea un repo nuevo cuando un módulo se extrae como servicio, y los
  disparadores están documentados en el `AGENTS.md` del workspace.
- Las fronteras de módulo son lo que hace que esa extracción sea mecánica.
