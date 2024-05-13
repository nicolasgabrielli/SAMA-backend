package sama.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sama.Entities.Reporte;

import java.util.List;

public interface ReporteRepository extends MongoRepository<Reporte, String> {
    List<Reporte> findAllByEmpresaId(String empresaId);
}