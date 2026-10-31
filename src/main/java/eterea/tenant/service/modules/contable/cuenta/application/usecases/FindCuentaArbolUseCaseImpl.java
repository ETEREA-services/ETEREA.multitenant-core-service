package eterea.tenant.service.modules.contable.cuenta.application.usecases;

import eterea.tenant.service.modules.contable.cuenta.domain.model.CodigoCuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.model.CuentaNodo;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.in.FindCuentaArbolUseCase;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class FindCuentaArbolUseCaseImpl implements FindCuentaArbolUseCase {

    private final CuentaRepository repository;

    public FindCuentaArbolUseCaseImpl(CuentaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CuentaNodo> findArbol() {
        List<Cuenta> cuentas = repository.findAllOrdered();
        Map<Long, Cuenta> porCodigo = new LinkedHashMap<>();
        for (Cuenta cuenta : cuentas) {
            porCodigo.put(cuenta.getNumeroCuenta(), cuenta);
        }

        Map<Long, List<Cuenta>> hijosPorPadre = new LinkedHashMap<>();
        List<Cuenta> raices = new ArrayList<>();
        Set<Long> huerfanas = new HashSet<>();

        for (Cuenta cuenta : cuentas) {
            long padre = CodigoCuenta.of(cuenta.getNumeroCuenta()).padre();
            if (padre == 0L) {
                raices.add(cuenta);
            } else if (!porCodigo.containsKey(padre)) {
                raices.add(cuenta);
                huerfanas.add(cuenta.getNumeroCuenta());
            } else {
                hijosPorPadre.computeIfAbsent(padre, clave -> new ArrayList<>()).add(cuenta);
            }
        }

        List<CuentaNodo> arbol = new ArrayList<>();
        for (Cuenta raiz : raices) {
            arbol.add(construirNodo(raiz, hijosPorPadre, huerfanas));
        }
        return arbol;
    }

    private CuentaNodo construirNodo(
            Cuenta cuenta, Map<Long, List<Cuenta>> hijosPorPadre, Set<Long> huerfanas) {
        CuentaNodo nodo = new CuentaNodo(cuenta, huerfanas.contains(cuenta.getNumeroCuenta()));
        for (Cuenta hijo : hijosPorPadre.getOrDefault(cuenta.getNumeroCuenta(), List.of())) {
            nodo.agregarHijo(construirNodo(hijo, hijosPorPadre, huerfanas));
        }
        return nodo;
    }
}
