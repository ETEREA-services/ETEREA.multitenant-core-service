package eterea.tenant.service.configuration;

import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OperationCustomizer customizeTenantOperations() {
        return (operation, handlerMethod) -> {
            Class<?> controllerClass = handlerMethod.getBeanType();
            String packageName = controllerClass.getPackageName();
            
            // Si el controlador pertenece al paquete tenant, agregar la cabecera X-TENANT-ID en Swagger
            if (packageName.startsWith("eterea.tenant.service.tenant")) {
                Parameter tenantIdHeader = new Parameter()
                        .in("header")
                        .schema(new StringSchema())
                        .name("X-TENANT-ID")
                        .description("ID de Tenant para ruteo de base de datos")
                        .required(true);
                operation.addParametersItem(tenantIdHeader);
            }
            return operation;
        };
    }
}
