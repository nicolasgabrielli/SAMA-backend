package sama.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sama.entity.Reporte;

import java.util.List;

public interface ReporteRepository extends MongoRepository<Reporte, String> {
    List<Reporte> findAllByEmpresaId(String empresaId);

    List<Reporte> findAllByEstado(String preset);
}