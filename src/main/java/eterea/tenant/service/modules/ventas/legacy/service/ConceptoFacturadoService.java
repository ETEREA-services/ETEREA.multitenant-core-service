/**
 * 
 */
package eterea.tenant.service.modules.ventas.legacy.service;

import eterea.tenant.service.modules.ventas.legacy.ConceptoFacturadoException;
import eterea.tenant.service.modules.ventas.legacy.ConceptoFacturado;
import eterea.tenant.service.modules.ventas.legacy.ConceptoFacturadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author daniel
 *
 */
@Service
@RequiredArgsConstructor
public class ConceptoFacturadoService {

	private final ConceptoFacturadoRepository repository;

	public ConceptoFacturado findByArticuloMovimientoId(Long articuloMovimientoId) {
		return Objects.requireNonNull(repository.findTopByArticuloMovimientoId(articuloMovimientoId)).orElseThrow(() -> new ConceptoFacturadoException(articuloMovimientoId));
	}

	public ConceptoFacturado add(ConceptoFacturado conceptoFacturado) {
		return repository.save(conceptoFacturado);
	}

}
