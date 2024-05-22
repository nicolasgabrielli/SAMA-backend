package sama.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sama.entities.Empresa;

public interface EmpresaRepository extends MongoRepository<Empresa, String> {
}
