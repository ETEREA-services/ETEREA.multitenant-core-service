package eterea.tenant.service.modules.contable.balance.domain.model;

/**
 * Modo de presentación del balance.
 *
 * <p>{@link #ESTRUCTURA} replica el Balance General jerárquico;
 * {@link #POR_CUENTA} replica los Saldos Contables planos.</p>
 */
public enum BalanceModo {
    ESTRUCTURA,
    POR_CUENTA
}
