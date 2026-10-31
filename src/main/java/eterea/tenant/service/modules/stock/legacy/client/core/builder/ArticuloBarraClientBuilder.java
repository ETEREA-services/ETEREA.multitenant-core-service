package eterea.tenant.service.modules.stock.legacy.client.core.builder;

import com.fasterxml.jackson.databind.json.JsonMapper;
import eterea.tenant.service.modules.stock.legacy.client.core.ArticuloBarraClient;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

public class ArticuloBarraClientBuilder {

    public static ArticuloBarraClient buildClient(String baseUrl) {
        return Feign.builder()
                .contract(new SpringMvcContract())
                .decoder(new JacksonDecoder(
                        JsonMapper.builder()
                                .findAndAddModules()
                                .build()))
                .logger(new Logger.ErrorLogger())
                .requestInterceptor(template -> {
                    template.header("X-Service-Name", "core-service");
                })
                .target(ArticuloBarraClient.class, baseUrl + "/api/core/articulobarra");
    }

}
