package sama.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sama.Entities.Empresa;

public interface EmpresaRepository extends MongoRepository<Empresa, String> {
}
