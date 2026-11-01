package eterea.tenant.service.modules.reporte.infrastructure.pdf;

import eterea.tenant.service.modules.reporte.api.ReporteCabecera;
import eterea.tenant.service.modules.reporte.api.ReporteFecha;
import org.openpdf.text.Document;
import org.openpdf.text.Element;
import org.openpdf.text.Phrase;
import org.openpdf.text.Rectangle;
import org.openpdf.text.Utilities;
import org.openpdf.text.pdf.ColumnText;
import org.openpdf.text.pdf.PdfContentByte;
import org.openpdf.text.pdf.PdfPageEventHelper;
import org.openpdf.text.pdf.PdfWriter;

/**
 * Dibuja la cabecera y el pie en todas las páginas del reporte.
 */
class ReportePageEvent extends PdfPageEventHelper {

    private final ReporteCabecera cabecera;
    private final int totalPaginas;
    private int paginas;

    ReportePageEvent(ReporteCabecera cabecera, int totalPaginas) {
        this.cabecera = cabecera;
        this.totalPaginas = totalPaginas;
    }

    int getPaginas() {
        return paginas;
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        dibujarCabecera(writer, document);
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        paginas = writer.getPageNumber();
        dibujarPie(writer, document);
    }

    private void dibujarCabecera(PdfWriter writer, Document document) {
        PdfContentByte canvas = writer.getDirectContent();
        Rectangle pagina = document.getPageSize();
        float izquierda = PdfLayout.MARGEN_IZQUIERDO;
        float derecha = pagina.getWidth() - PdfLayout.MARGEN_DERECHO;
        float yFecha = pagina.getHeight() - Utilities.millimetersToPoints(10);

        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
                new Phrase(ReporteFecha.formatear(cabecera.fecha()), PdfLayout.FUENTE_FECHA),
                izquierda, yFecha, 0);
        ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT,
                new Phrase(cabecera.negocio(), PdfLayout.FUENTE_NEGOCIO),
                derecha, yFecha, 0);

        float yTitulo = pagina.getHeight() - Utilities.millimetersToPoints(18);
        ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                new Phrase(cabecera.titulo(), PdfLayout.FUENTE_TITULO),
                pagina.getWidth() / 2, yTitulo, 0);

        float milimetrosLinea = 24;
        if (!cabecera.subtitulo().isBlank()) {
            float ySubtitulo = pagina.getHeight() - Utilities.millimetersToPoints(22);
            ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                    new Phrase(cabecera.subtitulo(), PdfLayout.FUENTE_FECHA),
                    pagina.getWidth() / 2, ySubtitulo, 0);
            milimetrosLinea = 27;
        }

        float yLinea = pagina.getHeight() - Utilities.millimetersToPoints(milimetrosLinea);
        canvas.setColorStroke(PdfLayout.COLOR_LINEA);
        canvas.setLineWidth(0.6f);
        canvas.moveTo(izquierda, yLinea);
        canvas.lineTo(derecha, yLinea);
        canvas.stroke();
    }

    private void dibujarPie(PdfWriter writer, Document document) {
        PdfContentByte canvas = writer.getDirectContent();
        Rectangle pagina = document.getPageSize();
        String total = totalPaginas > 0 ? String.valueOf(totalPaginas) : "…";
        String texto = "Página " + writer.getPageNumber() + " de " + total;

        ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                new Phrase(texto, PdfLayout.FUENTE_PIE),
                pagina.getWidth() / 2, PdfLayout.MARGEN_INFERIOR / 2, 0);
    }

}
