-- Unicidad del tenantId de los negocios.
-- Base: eterea-brain (MySQL 8).
-- Aplicado el 2026-10-30 sobre la instancia 10.147.17.188:3309.
-- Es aditivo: MySQL permite varios NULL en un índice UNIQUE.

ALTER TABLE negocio
    ADD UNIQUE KEY uq_negocio_tenant_id (tenant_id);
