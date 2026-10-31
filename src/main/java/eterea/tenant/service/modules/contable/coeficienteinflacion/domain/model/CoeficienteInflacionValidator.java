package eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model;

import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.exception.CoeficienteInflacionInvalidaException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Validaciones de los coeficientes de inflación.
 */
public final class CoeficienteInflacionValidator {

    public static final int ANHO_MINIMO = 1900;
    public static final int ANHO_MAXIMO = 2100;
    public static final int MES_MINIMO = 1;
    public static final int MES_MAXIMO = 12;
    public static final int ESCALA = 6;

    private static final BigDecimal COEFICIENTE_MAXIMO =
            new BigDecimal("999999999999.999999");

    private CoeficienteInflacionValidator() {
    }

    public static void validarAnio(Integer anho) {
        if (anho == null || anho < ANHO_MINIMO || anho > ANHO_MAXIMO) {
            throw new CoeficienteInflacionInvalidaException(
                    "COEFICIENTE_ANIO_INVALIDO",
                    "El año debe estar entre " + ANHO_MINIMO + " y " + ANHO_MAXIMO + ".");
        }
    }

    public static void validarMes(Integer mes) {
        if (mes == null || mes < MES_MINIMO || mes > MES_MAXIMO) {
            throw new CoeficienteInflacionInvalidaException(
                    "COEFICIENTE_MES_INVALIDO",
                    "El mes debe estar entre 1 y 12.");
        }
    }

    /**
     * Valida y normaliza un coeficiente. Un {@code null} representa un mes a
     * limpiar y se devuelve sin cambios.
     */
    public static BigDecimal normalizar(BigDecimal coeficiente) {
        if (coeficiente == null) {
            return null;
        }
        if (coeficiente.signum() < 0) {
            throw new CoeficienteInflacionInvalidaException(
                    "COEFICIENTE_VALOR_INVALIDO",
                    "El coeficiente no puede ser negativo.");
        }
        if (coeficiente.stripTrailingZeros().scale() > ESCALA) {
            throw new CoeficienteInflacionInvalidaException(
                    "COEFICIENTE_PRECISION_INVALIDA",
                    "El coeficiente admite hasta " + ESCALA + " decimales.");
        }

        BigDecimal normalizado = coeficiente.setScale(ESCALA, RoundingMode.HALF_UP);
        if (normalizado.compareTo(COEFICIENTE_MAXIMO) > 0) {
            throw new CoeficienteInflacionInvalidaException(
                    "COEFICIENTE_VALOR_INVALIDO",
                    "El coeficiente supera el máximo permitido.");
        }
        return normalizado;
    }

    public static void validarCambios(List<CoeficienteMensualCambio> cambios) {
        if (cambios == null) {
            throw new CoeficienteInflacionInvalidaException(
                    "COEFICIENTE_SIN_CAMBIOS",
                    "No se recibieron coeficientes para guardar.");
        }

        Set<Integer> meses = new HashSet<>();
        for (CoeficienteMensualCambio cambio : cambios) {
            if (cambio == null) {
                throw new CoeficienteInflacionInvalidaException(
                        "COEFICIENTE_MES_INVALIDO",
                        "Hay un mes inválido en la solicitud.");
            }
            validarMes(cambio.mes());
            if (!meses.add(cambio.mes())) {
                throw new CoeficienteInflacionInvalidaException(
                        "COEFICIENTE_MES_DUPLICADO",
                        "El mes " + cambio.mes() + " está repetido en la solicitud.");
            }
            normalizar(cambio.coeficiente());
        }
    }

}
