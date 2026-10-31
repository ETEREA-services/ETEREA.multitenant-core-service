-- Coeficientes de inflación: unicidad por año y mes
--
-- NO ejecutar este script sin completar la fase 0 de
-- PLAN-MIGRACION-COEF-INFLACION.md. Primero hay que confirmar el esquema real
-- en las bases de los tenants y resolver los duplicados existentes.
--
-- La aplicación usa spring.jpa.hibernate.ddl-auto=none: el esquema se
-- administra fuera de la app.

-- 1. Detectar duplicados de (anho, mes).
SELECT anho, mes, COUNT(*) AS cantidad
FROM coeficiente_inflacion
GROUP BY anho, mes
HAVING COUNT(*) > 1;

-- 2. Detectar valores que no cumplen las reglas del dominio.
SELECT *
FROM coeficiente_inflacion
WHERE mes < 1
   OR mes > 12
   OR anho < 1900
   OR anho > 2100
   OR coeficiente < 0;

-- 3. Crear el índice único después de resolver los duplicados.
--    Descomentar y ejecutar de forma coordinada.
--
-- ALTER TABLE coeficiente_inflacion
--   ADD CONSTRAINT uk_coeficiente_inflacion_anho_mes UNIQUE (anho, mes);

-- 4. Si la columna coeficiente no es DECIMAL(18,6), evaluar el cambio de tipo
--    con un respaldo previo de la tabla.
--
-- ALTER TABLE coeficiente_inflacion
--   MODIFY coeficiente DECIMAL(18,6) NOT NULL;
