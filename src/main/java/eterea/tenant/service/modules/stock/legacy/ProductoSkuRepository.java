package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.modules.stock.legacy.ProductoSku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductoSkuRepository extends JpaRepository<ProductoSku, Long> {

    Optional<ProductoSku> findBySkuAndLunes(String sku, byte lunes);

    Optional<ProductoSku> findBySkuAndMartes(String sku, byte martes);

    Optional<ProductoSku> findBySkuAndMiercoles(String sku, byte miercoles);

    Optional<ProductoSku> findBySkuAndJueves(String sku, byte jueves);

    Optional<ProductoSku> findBySkuAndViernes(String sku, byte viernes);

    Optional<ProductoSku> findBySkuAndSabado(String sku, byte sabado);

    Optional<ProductoSku> findBySkuAndDomingo(String sku, byte domingo);

    Optional<ProductoSku> findBySkuAndFeriado(String sku, byte feriado);

}
