package eterea.tenant.service.modules.reporte.infrastructure.xlsx;

import eterea.tenant.service.modules.reporte.api.ReporteAlineacion;
import eterea.tenant.service.modules.reporte.api.ReporteCabecera;
import eterea.tenant.service.modules.reporte.api.ReporteColumna;
import eterea.tenant.service.modules.reporte.api.ReporteFila;
import eterea.tenant.service.modules.reporte.api.ReporteTabla;
import eterea.tenant.service.modules.reporte.api.ReporteTipoColumna;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class OpenPoiXlsxAdapterTest {

    private final OpenPoiXlsxAdapter adapter = new OpenPoiXlsxAdapter();

    @Test
    void escribeTituloCabeceraNumerosYTotal() throws Exception {
        ReporteTabla tabla = new ReporteTabla(
                new ReporteCabecera(
                        "balance-general",
                        "Balance General",
                        "TSA Agencia",
                        LocalDate.of(2026, 10, 31),
                        "Período: 31-10-2026 - 31-10-2026 · Registrado en ARS · Expresado en ARS"),
                columnas(),
                filas());

        byte[] bytes = adapter.generar(tabla);
        assertThat(bytes).isNotEmpty();

        try (XSSFWorkbook workbook = new XSSFWorkbook(new ByteArrayInputStream(bytes))) {
            var sheet = workbook.getSheetAt(0);
            assertThat(sheet.getSheetName()).isEqualTo("Balance General");
            assertThat(sheet.getRow(0).getCell(0).getStringCellValue()).isEqualTo("Balance General");
            assertThat(sheet.getRow(1).getCell(0).getStringCellValue())
                    .contains("TSA Agencia")
                    .contains("31-10-2026");
            assertThat(sheet.getRow(2).getCell(0).getStringCellValue())
                    .contains("Expresado en ARS");

            Row cabecera = sheet.getRow(4);
            assertThat(cabecera.getCell(0).getStringCellValue()).isEqualTo("Código");
            assertThat(cabecera.getCell(2).getStringCellValue()).isEqualTo("Deudores");

            Row detalle = sheet.getRow(5);
            assertThat(detalle.getCell(0).getStringCellValue()).isEqualTo("10000000");
            assertThat(detalle.getCell(1).getStringCellValue()).isEqualTo("ACTIVO");
            assertThat(detalle.getCell(2).getCellType()).isEqualTo(CellType.NUMERIC);
            assertThat(detalle.getCell(2).getNumericCellValue())
                    .isCloseTo(21815500.11d, within(0.001d));
            assertThat(detalle.getCell(3).getCellType()).isEqualTo(CellType.NUMERIC);
            assertThat(detalle.getCell(3).getNumericCellValue()).isZero();

            Row total = sheet.getRow(6);
            assertThat(total.getCell(1).getStringCellValue()).isEqualTo("TOTALES");
            assertThat(total.getCell(4).getCellType()).isEqualTo(CellType.NUMERIC);
            assertThat(total.getCell(4).getNumericCellValue()).isEqualTo(-0.46d);
        }
    }

    private static List<ReporteColumna> columnas() {
        return List.of(
                new ReporteColumna("codigo", "Código", ReporteAlineacion.DERECHA, 0.14f, false,
                        ReporteTipoColumna.TEXTO),
                new ReporteColumna("cuenta", "Cuenta", ReporteAlineacion.IZQUIERDA, 0.43f, true,
                        ReporteTipoColumna.TEXTO),
                new ReporteColumna("deudores", "Deudores", ReporteAlineacion.DERECHA, 0.15f, false,
                        ReporteTipoColumna.NUMERO),
                new ReporteColumna("acreedores", "Acreedores", ReporteAlineacion.DERECHA, 0.15f, false,
                        ReporteTipoColumna.NUMERO),
                new ReporteColumna("diferencia", "Diferencia", ReporteAlineacion.DERECHA, 0.13f, false,
                        ReporteTipoColumna.NUMERO));
    }

    private static List<ReporteFila> filas() {
        return List.of(
                new ReporteFila(1, true, List.of("10000000", "ACTIVO", "21.815.500,11", "0,00", "")),
                new ReporteFila(1, true,
                        List.of("", "TOTALES", "21.815.500,11", "21.815.500,57", "(0,46)")));
    }

}
