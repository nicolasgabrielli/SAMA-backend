package sama.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sama.Entities.Empresa;

import java.util.List;

public interface EmpresaRepository extends MongoRepository<Empresa, String>{
    List<String> getReportes();
}
