package eterea.tenant.service.modules.contable.mayor.reporte.application.service;

import eterea.tenant.service.modules.reporte.api.ReporteFecha;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.Locale;

/**
 * Formato común de las columnas del Mayor.
 *
 * <p>Los importes se muestran en es-AR con 2 decimales y signo menos para los
 * negativos. En las columnas Debe/Haber el cero se representa vacío, igual que
 * la exportación legacy.</p>
 */
public final class MayorReporteFormato {

    private MayorReporteFormato() {
    }

    public static String fecha(LocalDate fecha) {
        return ReporteFecha.formatear(fecha);
    }

    public static String texto(String valor) {
        return valor == null ? "" : valor;
    }

    public static String asiento(Integer valor) {
        return valor == null ? "" : String.valueOf(valor);
    }

    public static String importe(BigDecimal valor) {
        return valor == null || valor.signum() == 0 ? "" : formatear(valor);
    }

    public static String formatear(BigDecimal valor) {
        BigDecimal redondeado = (valor == null ? BigDecimal.ZERO : valor)
                .setScale(2, RoundingMode.HALF_UP);
        return formateador().format(redondeado);
    }

    private static DecimalFormat formateador() {
        DecimalFormatSymbols simbolos = DecimalFormatSymbols.getInstance(
                Locale.forLanguageTag("es-AR"));
        simbolos.setGroupingSeparator('.');
        simbolos.setDecimalSeparator(',');
        DecimalFormat formato = new DecimalFormat("#,##0.00", simbolos);
        formato.setRoundingMode(RoundingMode.HALF_UP);
        return formato;
    }

}
