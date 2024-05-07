package sama.Repositories;
import org.springframework.data.mongodb.repository.MongoRepository;
import sama.Entities.Reporte;

public interface ReporteRepository extends MongoRepository<Reporte, String> {
}
