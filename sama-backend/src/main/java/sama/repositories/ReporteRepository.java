package sama.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sama.entities.Reporte;

import java.util.List;

public interface ReporteRepository extends MongoRepository<Reporte, String> {
    List<Reporte> findAllByEmpresaId(String empresaId);

    List<Reporte> findAllByEstado(String preset);
}