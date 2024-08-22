package sama.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sama.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}