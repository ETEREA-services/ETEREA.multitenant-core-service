package eterea.tenant.service.tenant.service;

import eterea.tenant.service.tenant.kotlin.model.ComprobanteFaltante;
import eterea.tenant.service.tenant.kotlin.repository.ComprobanteFaltanteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class ComprobanteFaltanteService {

    private final ComprobanteFaltanteRepository repository;

    public ComprobanteFaltanteService(ComprobanteFaltanteRepository repository) {
        this.repository = repository;
    }

    public ComprobanteFaltante createOne(Integer negocioId,
                            Integer comprobanteId,
                            OffsetDateTime fechaComprobante,
                            Integer prefijo,
                            Long numeroComprobante) {
        return new ComprobanteFaltante.Builder()
                .negocioId(negocioId)
                .comprobanteId(comprobanteId)
                .fecha(fechaComprobante)
                .prefijo(prefijo)
                .numero(numeroComprobante)
                .build();
    }

    @Transactional
    public List<ComprobanteFaltante> saveAll(List<ComprobanteFaltante> faltantes) {
        return repository.saveAll(faltantes);
    }

    @Transactional
    public void deleteAllByFecha(OffsetDateTime fecha) {
        repository.deleteAllByFecha(fecha);
    }

}
