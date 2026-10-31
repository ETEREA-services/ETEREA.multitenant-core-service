package eterea.tenant.service.modules.reporte.api;

import java.util.Objects;

/**
 * Definición de una columna de reporte.
 *
 * @param clave          identificador de la columna en el JSON de vista previa
 * @param titulo         texto visible en la cabecera
 * @param alineacion     alineación horizontal
 * @param anchoRelativo  peso relativo; el renderer reparte el ancho útil
 * @param admiteSangria  si la columna se indenta según el nivel de la fila
 */
public record ReporteColumna(
        String clave,
        String titulo,
        ReporteAlineacion alineacion,
        float anchoRelativo,
        boolean admiteSangria) {

    public ReporteColumna {
        clave = textoRequerido(clave, "clave");
        titulo = textoRequerido(titulo, "titulo");
        alineacion = Objects.requireNonNull(alineacion, "alineacion");
        if (anchoRelativo <= 0f) {
            throw new IllegalArgumentException("anchoRelativo debe ser mayor que cero");
        }
    }

    private static String textoRequerido(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " es obligatorio");
        }
        return valor;
    }

}
