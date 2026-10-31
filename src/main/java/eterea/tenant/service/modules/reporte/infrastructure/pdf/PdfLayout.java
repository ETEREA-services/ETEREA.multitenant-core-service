package eterea.tenant.service.modules.reporte.infrastructure.pdf;

import org.openpdf.text.Font;
import org.openpdf.text.Utilities;

import java.awt.Color;

/**
 * Medidas y fuentes compartidas por el renderer de reportes.
 */
final class PdfLayout {

    static final float MARGEN_IZQUIERDO = Utilities.millimetersToPoints(15);
    static final float MARGEN_DERECHO = Utilities.millimetersToPoints(15);
    static final float MARGEN_SUPERIOR = Utilities.millimetersToPoints(32);
    static final float MARGEN_INFERIOR = Utilities.millimetersToPoints(16);
    static final float SANGRIA = Utilities.millimetersToPoints(10);

    static final Font FUENTE_TITULO = new Font(Font.HELVETICA, 16, Font.BOLD);
    static final Font FUENTE_NEGOCIO = new Font(Font.HELVETICA, 11, Font.BOLD);
    static final Font FUENTE_FECHA = new Font(Font.HELVETICA, 9);
    static final Font FUENTE_CABECERA = new Font(Font.HELVETICA, 9, Font.BOLD);
    static final Font FUENTE_CUERPO = new Font(Font.HELVETICA, 9);
    static final Font FUENTE_CUERPO_NEGRITA = new Font(Font.HELVETICA, 9, Font.BOLD);
    static final Font FUENTE_PIE = new Font(Font.HELVETICA, 8);

    static final Color COLOR_LINEA = new Color(190, 190, 190);
    static final Color COLOR_LINEA_SUAVE = new Color(220, 220, 220);
    static final Color COLOR_FONDO_CABECERA = new Color(242, 242, 242);

    private PdfLayout() {
    }

}
