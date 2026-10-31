-- Blanqueo de contraseña con clave temporal.
-- Base: eterea-brain (MySQL 8).
-- Aplicado el 2026-10-30 sobre la instancia 10.147.17.188:3309.
-- Es aditivo y no modifica filas existentes: ambos flags quedan en su default.

ALTER TABLE usuario
    ADD COLUMN must_change_password TINYINT(1) NOT NULL DEFAULT 0
        AFTER password_changed_at,
    ADD COLUMN temporary_password_expires_at DATETIME NULL
        AFTER must_change_password;
