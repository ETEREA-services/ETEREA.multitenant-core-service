package eterea.tenant.service.modules.contable.balance.infrastructure.client;

import eterea.tenant.service.modules.contable.balance.domain.exception.BalanceException;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.time.Duration;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Cliente mínimo del BCRA para cotizaciones.
 *
 * <p>Usa el {@link RestClient} por defecto (TLS validado, sin
 * {@code InsecureTrustManagerFactory}). Si la respuesta no trae tasas, falla
 * con {@code BALANCE_COTIZACION_FALTANTE}; nunca asume 1,0.</p>
 */
@Component
public class BcraCotizacionClient {

    private static final String BASE_URL =
            "https://api.bcra.gob.ar/estadisticascambiarias/v1.0";

    private final RestClient restClient;

    public BcraCotizacionClient() {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(httpClient);
        factory.setReadTimeout(Duration.ofSeconds(15));
        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .requestFactory(factory)
                .build();
    }

    public Map<LocalDate, BigDecimal> cotizaciones(
            String simbolo, LocalDate desde, LocalDate hasta) {
        try {
            BcraResponse respuesta = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/Cotizaciones/{moneda}")
                            .queryParam("fechadesde", desde)
                            .queryParam("fechahasta", hasta)
                            .build(simbolo))
                    .retrieve()
                    .body(BcraResponse.class);

            if (respuesta == null || respuesta.results() == null
                    || respuesta.results().isEmpty()) {
                throw faltante(simbolo, desde, hasta);
            }

            Map<LocalDate, BigDecimal> cotizaciones = new LinkedHashMap<>();
            for (BcraResult resultado : respuesta.results()) {
                if (resultado.detalle() == null || resultado.detalle().isEmpty()) {
                    continue;
                }
                BcraDetalle detalle = resultado.detalle().getFirst();
                if (detalle.tipoCotizacion() == null || detalle.tipoCotizacion().signum() <= 0) {
                    continue;
                }
                cotizaciones.put(LocalDate.parse(resultado.fecha()), detalle.tipoCotizacion());
            }
            if (cotizaciones.isEmpty()) {
                throw faltante(simbolo, desde, hasta);
            }
            return cotizaciones;
        } catch (RestClientException exception) {
            throw new BalanceException(
                    "BALANCE_COTIZACION_FALTANTE",
                    "No se pudo obtener la cotización de " + simbolo
                            + " entre " + desde + " y " + hasta + ".",
                    exception);
        }
    }

    private static BalanceException faltante(
            String simbolo, LocalDate desde, LocalDate hasta) {
        return new BalanceException(
                "BALANCE_COTIZACION_FALTANTE",
                "BCRA no devolvió cotizaciones de " + simbolo
                        + " entre " + desde + " y " + hasta + ".");
    }

    private record BcraResponse(Integer status, List<BcraResult> results) {
    }

    private record BcraResult(String fecha, List<BcraDetalle> detalle) {
    }

    private record BcraDetalle(String codigoMoneda, BigDecimal tipoCotizacion) {
    }

}
