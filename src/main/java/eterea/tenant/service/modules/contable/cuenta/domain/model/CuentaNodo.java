package eterea.tenant.service.modules.contable.cuenta.domain.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Nodo del árbol del plan de cuentas.
 *
 * <p>{@code huerfana} indica que el código tiene un padre calculado que no
 * existe en los datos. El legacy lo resolvía creando una fila "Falta CUENTA";
 * la versión nueva sólo lo marca para que el operador decida.
 */
@Getter
public class CuentaNodo {

    private final Cuenta cuenta;
    private final boolean huerfana;
    private final List<CuentaNodo> hijos = new ArrayList<>();

    public CuentaNodo(Cuenta cuenta, boolean huerfana) {
        this.cuenta = cuenta;
        this.huerfana = huerfana;
    }

    public void agregarHijo(CuentaNodo hijo) {
        hijos.add(hijo);
    }
}
