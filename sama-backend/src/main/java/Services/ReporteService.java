package Services;

import Entities.Reporte;
import Repositories.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;

    public Optional<Reporte> obtenerReportePorId(String id) {
        return reporteRepository.findById(id);
    }

    public Reporte crearReporte(Reporte reporte) {
        return reporteRepository.save(reporte);
    }

    public void eliminarReporte(String id) {
        reporteRepository.deleteById(String.valueOf(reporteRepository.findById(id)));
    }

    public Reporte actualizarReporte(Reporte reporte) {
        return reporteRepository.save(reporte);
    }

    public List<Reporte> obtenerReportesPorEmpresa(String idEmpresa) {
        return reporteRepository.findAllByEmpresaId(idEmpresa);
    }
}