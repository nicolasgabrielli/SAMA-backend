package sama.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sama.entity.Empresa;

public interface EmpresaRepository extends MongoRepository<Empresa, String> {
}
