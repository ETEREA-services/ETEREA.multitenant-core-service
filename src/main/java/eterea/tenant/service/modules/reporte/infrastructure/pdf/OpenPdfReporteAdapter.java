package eterea.tenant.service.modules.reporte.infrastructure.pdf;

import eterea.tenant.service.modules.reporte.api.ReporteAlineacion;
import eterea.tenant.service.modules.reporte.api.ReporteColumna;
import eterea.tenant.service.modules.reporte.api.ReporteException;
import eterea.tenant.service.modules.reporte.api.ReporteFila;
import eterea.tenant.service.modules.reporte.api.ReporteTabla;
import eterea.tenant.service.modules.reporte.domain.ports.out.ReportePdfPort;
import org.openpdf.text.Document;
import org.openpdf.text.DocumentException;
import org.openpdf.text.Element;
import org.openpdf.text.PageSize;
import org.openpdf.text.Paragraph;
import org.openpdf.text.Phrase;
import org.openpdf.text.Rectangle;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Renderer de reportes sobre OpenPDF.
 *
 * <p>Genera el documento dos veces: la primera para conocer el total de
 * páginas y la segunda para escribir el pie “Página X de Y”. El contenido es
 * determinista y el costo es despreciable para los volúmenes actuales.</p>
 */
@Component
public class OpenPdfReporteAdapter implements ReportePdfPort {

    private static final float PADDING_BASE = 3f;
    private static final float PADDING_VERTICAL = 1.5f;

    @Override
    public byte[] generar(ReporteTabla tabla) {
        int totalPaginas = renderizar(tabla, null, 0);

        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        renderizar(tabla, salida, totalPaginas);
        return salida.toByteArray();
    }

    private int renderizar(ReporteTabla tabla, OutputStream salida, int totalPaginas) {
        Document documento = new Document(
                PageSize.A4,
                PdfLayout.MARGEN_IZQUIERDO,
                PdfLayout.MARGEN_DERECHO,
                PdfLayout.MARGEN_SUPERIOR,
                PdfLayout.MARGEN_INFERIOR);

        try {
            OutputStream destino = salida != null ? salida : OutputStream.nullOutputStream();
            PdfWriter writer = PdfWriter.getInstance(documento, destino);
            ReportePageEvent evento = new ReportePageEvent(tabla.cabecera(), totalPaginas);
            writer.setPageEvent(evento);

            documento.open();
            documento.add(crearTabla(tabla));
            if (tabla.filas().isEmpty()) {
                documento.add(new Paragraph(
                        "Sin datos para el negocio activo.", PdfLayout.FUENTE_CUERPO));
            }
            documento.close();
            return evento.getPaginas();
        } catch (DocumentException exception) {
            throw new ReporteException(
                    "REPORTE_GENERACION_FALLIDA",
                    "No se pudo generar el PDF del reporte.",
                    exception);
        } finally {
            if (documento.isOpen()) {
                documento.close();
            }
        }
    }

    private PdfPTable crearTabla(ReporteTabla tabla) throws DocumentException {
        List<ReporteColumna> columnas = tabla.columnas();
        PdfPTable table = new PdfPTable(columnas.size());
        table.setWidthPercentage(100f);
        float[] anchos = new float[columnas.size()];
        for (int indice = 0; indice < columnas.size(); indice++) {
            anchos[indice] = columnas.get(indice).anchoRelativo();
        }
        table.setWidths(anchos);
        table.setHeaderRows(1);
        table.setSplitLate(true);
        table.setSpacingBefore(4f);

        for (ReporteColumna columna : columnas) {
            table.addCell(celdaCabecera(columna));
        }

        for (ReporteFila fila : tabla.filas()) {
            for (int indice = 0; indice < columnas.size(); indice++) {
                table.addCell(celdaCuerpo(columnas.get(indice), fila, fila.celdas().get(indice)));
            }
        }

        return table;
    }

    private PdfPCell celdaCabecera(ReporteColumna columna) {
        PdfPCell celda = new PdfPCell(
                new Phrase(columna.titulo(), PdfLayout.FUENTE_CABECERA));
        celda.setBackgroundColor(PdfLayout.COLOR_FONDO_CABECERA);
        celda.setBorder(Rectangle.BOTTOM);
        celda.setBorderColor(PdfLayout.COLOR_LINEA);
        celda.setBorderWidthBottom(0.8f);
        celda.setPadding(PADDING_BASE + 1f);
        celda.setHorizontalAlignment(alineacion(columna.alineacion()));
        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return celda;
    }

    private PdfPCell celdaCuerpo(ReporteColumna columna, ReporteFila fila, String valor) {
        String texto = valor == null ? "" : valor;
        PdfPCell celda = new PdfPCell(new Phrase(
                texto,
                fila.negrita() ? PdfLayout.FUENTE_CUERPO_NEGRITA : PdfLayout.FUENTE_CUERPO));

        celda.setBorder(Rectangle.BOTTOM);
        celda.setBorderColor(PdfLayout.COLOR_LINEA_SUAVE);
        celda.setPaddingTop(PADDING_VERTICAL);
        celda.setPaddingBottom(PADDING_VERTICAL);
        celda.setPaddingRight(PADDING_BASE + 2f);
        celda.setPaddingLeft(sangria(columna, fila));
        celda.setHorizontalAlignment(alineacion(columna.alineacion()));
        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return celda;
    }

    private float sangria(ReporteColumna columna, ReporteFila fila) {
        if (!columna.admiteSangria()) {
            return PADDING_BASE;
        }
        return PADDING_BASE + Math.max(0, fila.nivel() - 1) * PdfLayout.SANGRIA;
    }

    private int alineacion(ReporteAlineacion alineacion) {
        return switch (alineacion) {
            case DERECHA -> Element.ALIGN_RIGHT;
            case CENTRO -> Element.ALIGN_CENTER;
            case IZQUIERDA -> Element.ALIGN_LEFT;
        };
    }

}
