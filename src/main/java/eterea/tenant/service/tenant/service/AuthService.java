package eterea.tenant.service.tenant.service;

import org.springframework.stereotype.Service;

import eterea.tenant.service.tenant.exception.UsuarioException;
import eterea.tenant.service.tenant.repository.UsuarioRepository;
import eterea.tenant.service.tenant.kotlin.model.Usuario;

@Service
public class AuthService {
   private final UsuarioRepository usuarioRepository;

   public AuthService(UsuarioRepository usuarioRepository) {
      this.usuarioRepository = usuarioRepository;
   }

   public boolean login(String username, String password) throws UsuarioException {
      Usuario usuario = usuarioRepository
         .findById(username)
         .orElseThrow(() -> new UsuarioException(username));
      String passwordHash = usuarioRepository.findPasswordHash(password);

      return usuario.getPassword().equals(passwordHash);
   }
}
