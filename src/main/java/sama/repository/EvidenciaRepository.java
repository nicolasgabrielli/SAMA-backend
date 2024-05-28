package sama.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sama.entity.Evidencia;

public interface EvidenciaRepository extends MongoRepository<Evidencia, String>{
}
