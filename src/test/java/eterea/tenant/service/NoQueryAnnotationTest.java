package eterea.tenant.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

/**
 * Guard de regresión: el proyecto no usa la anotación {@code @Query}.
 *
 * <p>Las consultas no derivadas viven en {@code *RepositoryCustomImpl} con
 * {@link jakarta.persistence.EntityManager}. Si alguien reintroduce la
 * anotación, este test falla.</p>
 */
class NoQueryAnnotationTest {

    private static final String QUERY_ANNOTATION = "@Query";

    @Test
    void noHayAnotacionQueryEnElCodigoDeProduccion() throws IOException {
        Path sourceRoot = Path.of("src", "main");
        assertThat(sourceRoot).exists();

        List<String> offenders;
        try (Stream<Path> files = Files.walk(sourceRoot)) {
            offenders = files
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java")
                            || path.toString().endsWith(".kt"))
                    .filter(this::containsQueryAnnotation)
                    .map(Path::toString)
                    .toList();
        }

        assertThat(offenders)
                .as("Archivos con @Query; usar un *RepositoryCustomImpl con EntityManager")
                .isEmpty();
    }

    private boolean containsQueryAnnotation(Path path) {
        try {
            return Files.readString(path).contains(QUERY_ANNOTATION);
        } catch (IOException exception) {
            throw new IllegalStateException("No se pudo leer " + path, exception);
        }
    }

}
