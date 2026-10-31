package eterea.tenant.service.brain.usuarios.usuario.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TemporaryPasswordGeneratorTest {

    private final TemporaryPasswordGenerator generator = new TemporaryPasswordGenerator();

    @Test
    void generaClavesCortasSinCaracteresAmbiguos() {
        for (int i = 0; i < 500; i++) {
            String clave = generator.generate();

            assertThat(clave).hasSize(10);
            assertThat(clave).doesNotContain("O", "0", "I", "l");
            assertThat(clave.chars().anyMatch(Character::isDigit)).isTrue();
            assertThat(clave.chars().anyMatch(Character::isLetter)).isTrue();
        }
    }
}
