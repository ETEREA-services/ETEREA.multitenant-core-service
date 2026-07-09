/**
 * 
 */
package eterea.tenant.service.tenant.service;

import eterea.tenant.service.tenant.exception.ClienteInternetException;
import eterea.tenant.service.tenant.kotlin.model.Authorities;
import eterea.tenant.service.tenant.kotlin.model.ClienteInternet;
import eterea.tenant.service.tenant.repository.AuthoritiesRepository;
import eterea.tenant.service.tenant.repository.ClienteInternetRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author daniel
 *
 */
@Service
public class ClienteInternetService {

	private final ClienteInternetRepository repository;
	private final AuthoritiesRepository authoritiesrepository;

	public ClienteInternetService(ClienteInternetRepository repository, AuthoritiesRepository authoritiesrepository) {
		this.repository = repository;
		this.authoritiesrepository = authoritiesrepository;
	}

	public ClienteInternet findById(Long clienteId) {
		return repository.findById(clienteId).orElseThrow(() -> new ClienteInternetException(clienteId));
	}

	public ClienteInternet add(ClienteInternet clienteinternet) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		clienteinternet.setPassword(encoder.encode(clienteinternet.getPassword()));
		repository.save(clienteinternet);
		this.addAuthorities(clienteinternet.getClienteId());
		return clienteinternet;
	}

	public void delete(Long clienteId) {
		repository.deleteById(clienteId);
	}

	public ClienteInternet update(ClienteInternet clienteinternet, Long clienteId) {
		for (Authorities a : authoritiesrepository.findAllByClienteId(clienteId))
			authoritiesrepository.deleteById(a.getId());
		delete(clienteId);
		return add(clienteinternet);
	}

	private void addAuthorities(Long clienteId) {
		authoritiesrepository.save(new Authorities(null, clienteId, "ROLE_USER"));
		authoritiesrepository.save(new Authorities(null, clienteId, "ROLE_ADMIN"));
	}
}
