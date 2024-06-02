package sama.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sama.entity.Evidencia;

import java.util.List;

public interface EvidenciaRepository extends MongoRepository<Evidencia, String>{
    List<Evidencia> findByIdReporte(String idReporte);
}
