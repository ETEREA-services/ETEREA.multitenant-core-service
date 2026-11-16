-- Baja de las columnas backend_ip y backend_port de la tabla negocio.
-- Base: eterea-brain (MySQL 8).
-- Aplicado el 2026-11-04 sobre la instancia 10.147.17.188:3309.
--
-- Esos datos no se usaban para conectar ni rutear: el backend de cada negocio
-- se resuelve desde la tabla negocio de su propia base tenant
-- (backend_server / backend_ip_lan / backend_port). Los valores de brain eran
-- una copia redundante.
--
-- Ya aplicado: no re-ejecutar (las columnas ya no existen).
-- Ejecutar después de desplegar el core y el administrador sin esos campos.
-- ALGORITHM=INSTANT requiere MySQL 8.0.29 o superior y no aplica si la columna
-- participa de un índice (no es el caso).

ALTER TABLE `eterea-brain`.`negocio`
    DROP COLUMN backend_ip,
    DROP COLUMN backend_port,
    ALGORITHM = INSTANT;
