package eterea.tenant.service.brain.usuarios.usuario.application.service;

import java.security.SecureRandom;
import org.springframework.stereotype.Component;

/**
 * Genera claves temporales cortas y fáciles de dictar.
 *
 * <p>Excluye caracteres ambiguos (O, 0, I, l) y garantiza al menos una letra y
 * un dígito.</p>
 */
@Component
public class TemporaryPasswordGenerator {

    private static final char[] ALFABETO =
            "ABCDEFGHJKMNPQRSTUVWXYZ23456789".toCharArray();
    private static final int LONGITUD = 10;
    private static final SecureRandom RANDOM = new SecureRandom();

    public String generate() {
        String clave;
        do {
            StringBuilder builder = new StringBuilder(LONGITUD)
                    .append(aleatorio())
                    .append(aleatorio())
                    .append(aleatorio())
                    .append(aleatorio())
                    .append(aleatorio())
                    .append(aleatorio())
                    .append(aleatorio())
                    .append(aleatorio())
                    .append(aleatorio())
                    .append(aleatorio());
            clave = builder.toString();
        } while (!tieneLetraYDigito(clave));

        return clave;
    }

    private char aleatorio() {
        return ALFABETO[RANDOM.nextInt(ALFABETO.length)];
    }

    private boolean tieneLetraYDigito(String clave) {
        boolean letra = false;
        boolean digito = false;
        for (char caracter : clave.toCharArray()) {
            if (Character.isDigit(caracter)) {
                digito = true;
            } else {
                letra = true;
            }
        }
        return letra && digito;
    }
}
