/**
 * 
 */
package eterea.tenant.service.tenant.service;

import eterea.tenant.service.tenant.kotlin.exception.ConceptoFacturadoException;
import eterea.tenant.service.tenant.kotlin.model.ConceptoFacturado;
import eterea.tenant.service.tenant.kotlin.repository.ConceptoFacturadoRepository;
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
