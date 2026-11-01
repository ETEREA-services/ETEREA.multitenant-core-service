package eterea.tenant.service.modules.contable.balance.domain.model;

/**
 * Moneda del catálogo del tenant.
 *
 * @param monedaId identificador legacy ({@code moneda.Mon_ID})
 * @param nombre   nombre visible
 * @param simbolo  símbolo ISO (ARS, USD, EUR, BRL...)
 * @param esBase   si es la moneda base del negocio (ARS, id 1)
 */
public record MonedaInfo(Integer monedaId, String nombre, String simbolo, boolean esBase) {
}
