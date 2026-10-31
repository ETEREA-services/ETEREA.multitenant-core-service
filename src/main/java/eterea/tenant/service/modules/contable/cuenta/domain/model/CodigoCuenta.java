package eterea.tenant.service.modules.contable.cuenta.domain.model;

import eterea.tenant.service.modules.contable.cuenta.domain.exception.CuentaInvalidaException;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

/**
 * Código de cuenta del plan contable.
 *
 * <p>Replica la jerarquía del legacy:
 * <ul>
 *   <li>longitudes 1, 2, 3, 5 y 8 dígitos;</li>
 *   <li>grados calculados con las mismas fórmulas {@code TRUNCATE} de
 *       {@code clsDAOPlanCta.recalcularGrados};</li>
 *   <li>nivel 1..5 con la precedencia del legacy (1 gana sobre 2, 2 sobre 3,
 *       3 sobre 4 y el resto es 5);</li>
 *   <li>padre calculado con la misma lógica de
 *       {@code clsDAOPlanCta.cuentaPadre}, incluyendo los códigos de menos de
 *       8 dígitos como raíz.</li>
 * </ul>
 *
 * <p>Para validar los ancestros de un alta se replica el relleno a 8 dígitos
 * que hace el formulario legacy ({@code lngCuentaAuxiliar}); ver
 * {@link #valorParaAncestros()} y {@link #ancestros()}.
 */
public final class CodigoCuenta {

    private static final long CODIGO_MINIMO = 1L;
    private static final long CODIGO_MAXIMO = 99_999_999L;
    private static final long BASE_OCHO_DIGITOS = 10_000_000L;
    private static final long DIVISOR_GRADO1 = 10_000_000L;
    private static final long DIVISOR_GRADO2 = 1_000_000L;
    private static final long DIVISOR_GRADO3 = 100_000L;
    private static final long DIVISOR_GRADO4 = 1_000L;
    private static final String MENSAJE_CODIGO_INVALIDO =
            "El código debe ser numérico, de 1 a 8 dígitos y distinto de cero.";

    private final long valor;
    private final long grado1;
    private final long grado2;
    private final long grado3;
    private final long grado4;
    private final int nivel;
    private final long padre;

    private CodigoCuenta(long valor) {
        this.valor = valor;
        this.grado1 = truncar(valor, DIVISOR_GRADO1);
        this.grado2 = truncar(valor, DIVISOR_GRADO2);
        this.grado3 = truncar(valor, DIVISOR_GRADO3);
        this.grado4 = truncar(valor, DIVISOR_GRADO4);
        this.nivel = calcularNivel();
        this.padre = calcularPadre();
    }

    public static CodigoCuenta of(long valor) {
        if (valor < CODIGO_MINIMO || valor > CODIGO_MAXIMO) {
            throw new CuentaInvalidaException("CUENTA_CODIGO_INVALIDO", MENSAJE_CODIGO_INVALIDO);
        }
        return new CodigoCuenta(valor);
    }

    public static CodigoCuenta of(String valor) {
        if (valor == null || !valor.matches("\\d{1,8}")) {
            throw new CuentaInvalidaException("CUENTA_CODIGO_INVALIDO", MENSAJE_CODIGO_INVALIDO);
        }
        return of(Long.parseLong(valor));
    }

    public long valor() {
        return valor;
    }

    public long grado1() {
        return grado1;
    }

    public long grado2() {
        return grado2;
    }

    public long grado3() {
        return grado3;
    }

    public long grado4() {
        return grado4;
    }

    public int nivel() {
        return nivel;
    }

    public long padre() {
        return padre;
    }

    public boolean esRaiz() {
        return padre == 0L;
    }

    /**
     * Cota superior del subárbol, con la misma fórmula de
     * {@code clsDAOMovCon.tieneMovimientosIncluyeHijos}: se quitan los ceros
     * finales, se agrega {@code 99999999} y se recortan 8 dígitos.
     */
    public long limiteSuperior() {
        String codigo = Long.toString(valor);
        int posicion = codigo.length();
        while (posicion > 0 && codigo.charAt(posicion - 1) == '0') {
            posicion--;
        }
        String prefijo = codigo.substring(0, posicion);
        String relleno = (prefijo + "99999999").substring(0, 8);
        return Long.parseLong(relleno);
    }

    /**
     * Código rellenado con ceros a la derecha hasta tener 8 dígitos, tal como
     * lo usa el formulario legacy para buscar los ancestros de un alta.
     */
    public long valorParaAncestros() {
        long auxiliar = valor;
        while (auxiliar < BASE_OCHO_DIGITOS) {
            auxiliar *= 10;
        }
        return auxiliar;
    }

    /**
     * Ancestros que el legacy exige al crear la cuenta, en el orden en que los
     * muestra el formulario (grado 4, 3, 2 y 1).
     *
     * <p>Se calculan sobre el código rellenado a 8 dígitos, se deduplican y se
     * excluye el propio código almacenado. Para un código corto, el ancestro
     * "padding" se incluye aunque coincida con el código rellenado, porque no
     * es la misma fila que la cuenta que se está creando.
     */
    public List<Long> ancestros() {
        CodigoCuenta codigoParaAncestros = new CodigoCuenta(valorParaAncestros());
        LinkedHashSet<Long> resultado = new LinkedHashSet<>();
        agregarAncestro(resultado, codigoParaAncestros.grado4);
        agregarAncestro(resultado, codigoParaAncestros.grado3);
        agregarAncestro(resultado, codigoParaAncestros.grado2);
        agregarAncestro(resultado, codigoParaAncestros.grado1);
        return List.copyOf(resultado);
    }

    private void agregarAncestro(LinkedHashSet<Long> ancestros, long candidato) {
        if (candidato != 0L && candidato != valor) {
            ancestros.add(candidato);
        }
    }

    private int calcularNivel() {
        if (grado1 != 0L && valor == grado1) {
            return 1;
        }
        if (grado2 != 0L && valor == grado2) {
            return 2;
        }
        if (grado3 != 0L && valor == grado3) {
            return 3;
        }
        if (grado4 != 0L && valor == grado4) {
            return 4;
        }
        return 5;
    }

    private long calcularPadre() {
        String codigo = Long.toString(valor);
        if (codigo.length() < 8) {
            return 0L;
        }
        if (codigo.endsWith("0000000")) {
            return 0L;
        }
        if (codigo.endsWith("000000")) {
            return Long.parseLong(codigo.substring(0, 1) + "0000000");
        }
        if (codigo.endsWith("00000")) {
            return Long.parseLong(codigo.substring(0, 2) + "000000");
        }
        if (codigo.endsWith("000")) {
            return Long.parseLong(codigo.substring(0, 3) + "00000");
        }
        return Long.parseLong(codigo.substring(0, 5) + "000");
    }

    private static long truncar(long valor, long divisor) {
        return (valor / divisor) * divisor;
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (!(otro instanceof CodigoCuenta that)) {
            return false;
        }
        return valor == that.valor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return Long.toString(valor);
    }
}
