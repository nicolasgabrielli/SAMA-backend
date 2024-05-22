package sama.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sama.entities.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
}
