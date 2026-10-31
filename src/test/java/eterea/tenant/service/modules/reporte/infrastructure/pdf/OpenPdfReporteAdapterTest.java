package eterea.tenant.service.modules.reporte.infrastructure.pdf;

import eterea.tenant.service.modules.reporte.api.ReporteAlineacion;
import eterea.tenant.service.modules.reporte.api.ReporteCabecera;
import eterea.tenant.service.modules.reporte.api.ReporteColumna;
import eterea.tenant.service.modules.reporte.api.ReporteFecha;
import eterea.tenant.service.modules.reporte.api.ReporteFila;
import eterea.tenant.service.modules.reporte.api.ReporteTabla;
import org.junit.jupiter.api.Test;
import org.openpdf.text.pdf.PdfReader;
import org.openpdf.text.pdf.parser.PdfTextExtractor;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OpenPdfReporteAdapterTest {

    private final OpenPdfReporteAdapter adapter = new OpenPdfReporteAdapter();

    @Test
    void generaUnPdfConCabeceraColumnasPaginacionYFormatoDeFecha() throws IOException {
        List<ReporteFila> filas = new ArrayList<>();
        filas.add(new ReporteFila(1, true, List.of("10000000", "ACTIVO", "")));
        filas.add(new ReporteFila(2, true, List.of("11000000", "DISPONIBILIDADES", "")));
        filas.add(new ReporteFila(5, false,
                List.of("11101001", "Caja", "10101001002")));
        filas.add(new ReporteFila(5, false,
                List.of("11203001", "Tarjeta de Crédito Visa", "10101005001")));
        filas.add(new ReporteFila(5, false,
                List.of("12102001", "IVA Crédito Fiscal", "10103001001")));
        filas.add(new ReporteFila(5, false,
                List.of("44101000", "Hotelería Cacheuta", "40101001000")));
        filas.add(new ReporteFila(5, false,
                List.of("44202002", "Débito Fiscal", "20102001002")));
        for (int indice = 0; indice < 150; indice++) {
            filas.add(new ReporteFila(5, false,
                    List.of(String.valueOf(50_000_000L + indice),
                            "Cuenta de prueba " + indice,
                            "")));
        }

        ReporteTabla tabla = new ReporteTabla(
                new ReporteCabecera("plan-cuentas", "Plan de Cuentas", "TSA Agencia",
                        LocalDate.of(2026, 10, 31)),
                List.of(
                        new ReporteColumna("codigo", "Código",
                                ReporteAlineacion.DERECHA, 0.18f, false),
                        new ReporteColumna("nombre", "Cuenta",
                                ReporteAlineacion.IZQUIERDA, 0.60f, true),
                        new ReporteColumna("cuentaMaestra", "Cuenta maestra",
                                ReporteAlineacion.DERECHA, 0.22f, false)),
                filas);

        byte[] pdf = adapter.generar(tabla);

        assertThat(pdf).isNotEmpty();
        try (PdfReader reader = new PdfReader(pdf)) {
            int paginas = reader.getNumberOfPages();
            assertThat(paginas).isGreaterThan(1);

            String primera = new PdfTextExtractor(reader).getTextFromPage(1);
            assertThat(primera)
                    .contains("31-10-2026")
                    .contains("TSA Agencia")
                    .contains("Plan de Cuentas")
                    .contains("Código")
                    .contains("Cuenta maestra")
                    .contains("11101001")
                    .contains("Caja")
                    .contains("10101001002")
                    .contains("Página 1 de " + paginas)
                    .doesNotContain("2026-10-31");

            String ultima = new PdfTextExtractor(reader).getTextFromPage(paginas);
            assertThat(ultima).contains("Página " + paginas + " de " + paginas);
        }
    }

    @Test
    void conservaTildesYEnies() throws IOException {
        ReporteTabla tabla = new ReporteTabla(
                new ReporteCabecera("plan-cuentas", "Plan de Cuentas", "Agencia Ñandú",
                        LocalDate.of(2026, 10, 31)),
                List.of(
                        new ReporteColumna("nombre", "Cuenta",
                                ReporteAlineacion.IZQUIERDA, 1f, true)),
                List.of(new ReporteFila(5, false, List.of("Hotelería y Débito"))));

        byte[] pdf = adapter.generar(tabla);

        try (PdfReader reader = new PdfReader(pdf)) {
            String texto = new PdfTextExtractor(reader).getTextFromPage(1);
            assertThat(texto)
                    .contains("Agencia Ñandú")
                    .contains("Hotelería y Débito");
        }
    }

    @Test
    void imprimeSinDatosSiLaTablaEstaVacia() throws IOException {
        ReporteTabla tabla = new ReporteTabla(
                new ReporteCabecera("plan-cuentas", "Plan de Cuentas", "TSA Agencia",
                        LocalDate.of(2026, 10, 31)),
                List.of(new ReporteColumna("codigo", "Código",
                        ReporteAlineacion.DERECHA, 1f, false)),
                List.of());

        byte[] pdf = adapter.generar(tabla);

        try (PdfReader reader = new PdfReader(pdf)) {
            assertThat(reader.getNumberOfPages()).isEqualTo(1);
            assertThat(new PdfTextExtractor(reader).getTextFromPage(1))
                    .contains("Sin datos para el negocio activo.")
                    .contains("31-10-2026");
        }
    }

    @Test
    void elJsonDelReporteUsaElFormatoDeFechaAcordado() {
        assertThat(ReporteFecha.formatear(LocalDate.of(2026, 10, 31)))
                .isEqualTo("31-10-2026");
    }

}
