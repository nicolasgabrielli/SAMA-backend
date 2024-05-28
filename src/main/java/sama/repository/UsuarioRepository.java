package sama.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sama.entity.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
}
