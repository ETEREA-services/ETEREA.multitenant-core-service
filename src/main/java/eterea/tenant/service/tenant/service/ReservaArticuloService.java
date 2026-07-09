/**
 * 
 */
package eterea.tenant.service.tenant.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eterea.tenant.service.tenant.kotlin.exception.ReservaArticuloException;
import eterea.tenant.service.tenant.kotlin.model.ReservaArticulo;
import eterea.tenant.service.tenant.kotlin.repository.ReservaArticuloRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * @author daniel
 *
 */
@Service
public class ReservaArticuloService {

	private final ReservaArticuloRepository repository;

	public ReservaArticuloService(ReservaArticuloRepository repository) {
		this.repository = repository;
	}

	public List<ReservaArticulo> findAllByReservaId(Long reservaId) {
		return repository.findAllByReservaId(reservaId);
	}

    public List<ReservaArticulo> findAllByVoucherId(Long reservaId, Long voucherId) {
		return repository.findAllByReservaIdAndVoucherId(reservaId, voucherId);
    }

	@Transactional
	public void deleteByReservaArticuloId(Long reservaArticuloId) {
		repository.deleteByReservaArticuloId(reservaArticuloId);
	}

	public List<ReservaArticulo> saveAll(List<ReservaArticulo> reservaArticulos) {
		return repository.saveAll(reservaArticulos);
	}

	public ReservaArticulo add(ReservaArticulo reservaArticulo) {
		return repository.save(reservaArticulo);
	}

    public ReservaArticulo update(ReservaArticulo newReservaArticulo, Long reservaArticuloId) {
		ReservaArticulo reservaArticulo = repository.findByReservaArticuloId(reservaArticuloId)
				.orElseThrow(() -> new ReservaArticuloException(reservaArticuloId));

		reservaArticulo.setNegocioId(newReservaArticulo.getNegocioId());
		reservaArticulo.setReservaId(newReservaArticulo.getReservaId());
		reservaArticulo.setVoucherId(newReservaArticulo.getVoucherId());
		reservaArticulo.setArticuloId(newReservaArticulo.getArticuloId());
		reservaArticulo.setCantidad(newReservaArticulo.getCantidad());
		reservaArticulo.setComision(newReservaArticulo.getComision());
		reservaArticulo.setPrecioUnitarioSinComision(newReservaArticulo.getPrecioUnitarioSinComision());
		reservaArticulo.setPrecioUnitario(newReservaArticulo.getPrecioUnitario());
		reservaArticulo.setPrecioCompra(newReservaArticulo.getPrecioCompra());
		reservaArticulo.setObservaciones(newReservaArticulo.getObservaciones());
		reservaArticulo.setTrackUuid(newReservaArticulo.getTrackUuid());

		return repository.save(reservaArticulo);
    }

}
