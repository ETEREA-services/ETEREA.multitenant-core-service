package eterea.tenant.service.modules.reporte.infrastructure.xlsx;

import eterea.tenant.service.modules.reporte.api.ReporteColumna;
import eterea.tenant.service.modules.reporte.api.ReporteException;
import eterea.tenant.service.modules.reporte.api.ReporteFecha;
import eterea.tenant.service.modules.reporte.api.ReporteFila;
import eterea.tenant.service.modules.reporte.api.ReporteTabla;
import eterea.tenant.service.modules.reporte.api.ReporteTipoColumna;
import eterea.tenant.service.modules.reporte.domain.ports.out.ReporteXlsxPort;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Exportador XLSX de reportes sobre Apache POI.
 *
 * <p>Escribe una hoja con título, metadatos, cabecera, filas y totales. Las
 * columnas marcadas como {@link ReporteTipoColumna#NUMERO} se escriben como
 * números (no texto) y con formato de dos decimales.</p>
 */
@Component
public class OpenPoiXlsxAdapter implements ReporteXlsxPort {

    private static final int FILA_TITULO = 0;
    private static final int FILA_META = 1;
    private static final int FILA_SUBTITULO = 2;
    private static final int FILA_CABECERA = 4;

    @Override
    public byte[] generar(ReporteTabla tabla) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream salida = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet(nombreHoja(tabla.cabecera().titulo()));
            List<ReporteColumna> columnas = tabla.columnas();
            Estilos estilos = new Estilos(workbook);

            filaTitulo(sheet, tabla, columnas.size(), estilos);
            filaMeta(sheet, tabla, columnas.size(), estilos);
            filaSubtitulo(sheet, tabla, columnas.size(), estilos);
            filaCabecera(sheet, columnas, estilos);

            int numeroFila = FILA_CABECERA + 1;
            for (ReporteFila fila : tabla.filas()) {
                Row row = sheet.createRow(numeroFila++);
                for (int indice = 0; indice < columnas.size(); indice++) {
                    ReporteColumna columna = columnas.get(indice);
                    String valor = indice < fila.celdas().size() ? fila.celdas().get(indice) : "";
                    escribirCelda(row, indice, columna, fila, valor, estilos);
                }
            }

            if (tabla.filas().isEmpty()) {
                Row row = sheet.createRow(numeroFila);
                Cell cell = row.createCell(0);
                cell.setCellValue("Sin datos para el negocio activo.");
                cell.setCellStyle(estilos.texto(false, 0));
            }

            ajustarAnchos(sheet, columnas);
            sheet.createFreezePane(0, FILA_CABECERA + 1);

            workbook.write(salida);
            return salida.toByteArray();
        } catch (IOException exception) {
            throw new ReporteException(
                    "REPORTE_GENERACION_FALLIDA",
                    "No se pudo generar el XLSX del reporte.",
                    exception);
        }
    }

    private void filaTitulo(Sheet sheet, ReporteTabla tabla, int columnas, Estilos estilos) {
        Row row = sheet.createRow(FILA_TITULO);
        Cell cell = row.createCell(0);
        cell.setCellValue(tabla.cabecera().titulo());
        cell.setCellStyle(estilos.titulo());
        if (columnas > 1) {
            sheet.addMergedRegion(new CellRangeAddress(FILA_TITULO, FILA_TITULO, 0, columnas - 1));
        }
    }

    private void filaMeta(Sheet sheet, ReporteTabla tabla, int columnas, Estilos estilos) {
        Row row = sheet.createRow(FILA_META);
        Cell cell = row.createCell(0);
        cell.setCellValue(tabla.cabecera().negocio()
                + " · " + ReporteFecha.formatear(tabla.cabecera().fecha()));
        cell.setCellStyle(estilos.meta());
        if (columnas > 1) {
            sheet.addMergedRegion(new CellRangeAddress(FILA_META, FILA_META, 0, columnas - 1));
        }
    }

    private void filaSubtitulo(Sheet sheet, ReporteTabla tabla, int columnas, Estilos estilos) {
        Row row = sheet.createRow(FILA_SUBTITULO);
        Cell cell = row.createCell(0);
        cell.setCellValue(tabla.cabecera().subtitulo());
        cell.setCellStyle(estilos.subtitulo());
        if (columnas > 1) {
            sheet.addMergedRegion(new CellRangeAddress(
                    FILA_SUBTITULO, FILA_SUBTITULO, 0, columnas - 1));
        }
    }

    private void filaCabecera(
            Sheet sheet, List<ReporteColumna> columnas, Estilos estilos) {
        Row row = sheet.createRow(FILA_CABECERA);
        for (int indice = 0; indice < columnas.size(); indice++) {
            Cell cell = row.createCell(indice);
            cell.setCellValue(columnas.get(indice).titulo());
            cell.setCellStyle(estilos.cabecera());
        }
    }

    private void escribirCelda(
            Row row,
            int indice,
            ReporteColumna columna,
            ReporteFila fila,
            String valor,
            Estilos estilos) {
        Cell cell = row.createCell(indice);
        int sangria = columna.admiteSangria() ? Math.max(0, fila.nivel() - 1) : 0;

        if (columna.tipo() == ReporteTipoColumna.NUMERO) {
            Double numero = numero(valor);
            if (numero != null) {
                cell.setCellValue(numero);
                cell.setCellStyle(estilos.numero(fila.negrita(), sangria));
                return;
            }
        }

        cell.setCellValue(valor);
        cell.setCellStyle(estilos.texto(fila.negrita(), sangria));
    }

    private void ajustarAnchos(Sheet sheet, List<ReporteColumna> columnas) {
        for (int indice = 0; indice < columnas.size(); indice++) {
            int ancho = (int) Math.max(10, Math.round(columnas.get(indice).anchoRelativo() * 100));
            sheet.setColumnWidth(indice, ancho * 256);
        }
    }

    private static Double numero(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        String limpio = valor.trim();
        boolean negativo = limpio.startsWith("(") && limpio.endsWith(")");
        limpio = limpio.replace("(", "").replace(")", "").replace(".", "").replace(",", ".");
        try {
            double numero = Double.parseDouble(limpio);
            return negativo ? -numero : numero;
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    private static String nombreHoja(String titulo) {
        String nombre = titulo == null ? "Reporte" : titulo.replaceAll("[\\\\/?*\\[\\]:]", "-").trim();
        if (nombre.isBlank()) {
            nombre = "Reporte";
        }
        return nombre.length() > 31 ? nombre.substring(0, 31) : nombre;
    }

    /**
     * Estilos reutilizables. La sangría se aplica al nombre de cuenta y se
     * reutiliza por nivel para no crear una hoja de estilos desmedida.
     */
    private static final class Estilos {

        private final Workbook workbook;
        private final Font normal;
        private final Font negrita;
        private final Map<String, CellStyle> cache = new HashMap<>();

        private Estilos(Workbook workbook) {
            this.workbook = workbook;
            this.normal = workbook.createFont();
            this.normal.setFontHeightInPoints((short) 10);
            this.negrita = workbook.createFont();
            this.negrita.setBold(true);
            this.negrita.setFontHeightInPoints((short) 10);
        }

        private CellStyle titulo() {
            return cache.computeIfAbsent("titulo", clave -> {
                CellStyle estilo = workbook.createCellStyle();
                Font fuente = workbook.createFont();
                fuente.setBold(true);
                fuente.setFontHeightInPoints((short) 15);
                estilo.setFont(fuente);
                return estilo;
            });
        }

        private CellStyle meta() {
            return cache.computeIfAbsent("meta", clave -> {
                CellStyle estilo = workbook.createCellStyle();
                Font fuente = workbook.createFont();
                fuente.setFontHeightInPoints((short) 10);
                fuente.setColor(IndexedColors.GREY_50_PERCENT.getIndex());
                estilo.setFont(fuente);
                return estilo;
            });
        }

        private CellStyle subtitulo() {
            return cache.computeIfAbsent("subtitulo", clave -> {
                CellStyle estilo = workbook.createCellStyle();
                Font fuente = workbook.createFont();
                fuente.setItalic(true);
                fuente.setFontHeightInPoints((short) 9);
                fuente.setColor(IndexedColors.GREY_50_PERCENT.getIndex());
                estilo.setFont(fuente);
                return estilo;
            });
        }

        private CellStyle cabecera() {
            return cache.computeIfAbsent("cabecera", clave -> {
                CellStyle estilo = workbook.createCellStyle();
                Font fuente = workbook.createFont();
                fuente.setBold(true);
                fuente.setFontHeightInPoints((short) 10);
                estilo.setFont(fuente);
                estilo.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                estilo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                estilo.setBorderBottom(BorderStyle.THIN);
                return estilo;
            });
        }

        private CellStyle texto(boolean negrita, int sangria) {
            String clave = "texto|" + negrita + "|" + sangria;
            return cache.computeIfAbsent(clave, k -> {
                CellStyle estilo = workbook.createCellStyle();
                estilo.setFont(negrita ? this.negrita : normal);
                if (sangria > 0) {
                    estilo.setIndention((short) sangria);
                }
                return estilo;
            });
        }

        private CellStyle numero(boolean negrita, int sangria) {
            String clave = "numero|" + negrita + "|" + sangria;
            return cache.computeIfAbsent(clave, k -> {
                CellStyle estilo = workbook.createCellStyle();
                estilo.setFont(negrita ? this.negrita : normal);
                estilo.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));
                if (sangria > 0) {
                    estilo.setIndention((short) sangria);
                }
                return estilo;
            });
        }

    }

}
