package service_desk_api.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service_desk_api.api.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByEmail(String email);

}
