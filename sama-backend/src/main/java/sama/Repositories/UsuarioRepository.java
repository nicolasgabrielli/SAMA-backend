package sama.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sama.Entities.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
}
