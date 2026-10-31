package eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Vista anual de coeficientes: siempre contiene los 12 meses.
 */
@Getter
public class CoeficienteInflacionAnio {

    public static final int MESES = 12;

    private final int anho;
    private final List<CoeficienteMensual> meses;

    public CoeficienteInflacionAnio(int anho, List<CoeficienteMensual> meses) {
        this.anho = anho;
        this.meses = List.copyOf(meses);
    }

    /**
     * Arma la vista anual a partir de los registros existentes.
     *
     * <p>Si hubiera más de un registro para el mismo mes (datos legacy
     * duplicados), se conserva el primero. La unicidad se garantiza en la base
     * y en el servicio para los datos nuevos.</p>
     */
    public static CoeficienteInflacionAnio of(int anho, List<CoeficienteInflacion> registros) {
        Map<Integer, CoeficienteInflacion> porMes = new HashMap<>();
        for (CoeficienteInflacion registro : registros) {
            if (registro.getMes() != null) {
                porMes.merge(registro.getMes(), registro, CoeficienteInflacionAnio::preferir);
            }
        }

        List<CoeficienteMensual> meses = IntStream.rangeClosed(1, MESES)
                .mapToObj(mes -> {
                    CoeficienteInflacion registro = porMes.get(mes);
                    if (registro == null) {
                        return new CoeficienteMensual(mes, null, null, null);
                    }
                    return new CoeficienteMensual(
                            mes,
                            registro.getCoeficiente(),
                            registro.getCoeficienteInflacionId(),
                            registro.getCreated());
                })
                .toList();

        return new CoeficienteInflacionAnio(anho, meses);
    }

    public int cargados() {
        return (int) meses.stream().filter(CoeficienteMensual::cargado).count();
    }

    public int total() {
        return MESES;
    }

    private static CoeficienteInflacion preferir(
            CoeficienteInflacion actual, CoeficienteInflacion nuevo) {
        if (actual.getCoeficiente() == null && nuevo.getCoeficiente() != null) {
            return nuevo;
        }
        if (nuevo.getCoeficiente() == null) {
            return actual;
        }
        Long idActual = actual.getCoeficienteInflacionId();
        Long idNuevo = nuevo.getCoeficienteInflacionId();
        if (idActual != null && idNuevo != null && idNuevo > idActual) {
            return nuevo;
        }
        return actual;
    }

}
