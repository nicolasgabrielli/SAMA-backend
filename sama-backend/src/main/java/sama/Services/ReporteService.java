package sama.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sama.Entities.Reporte;
import sama.Repositories.ReporteRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private EmpresaService empresaService;

    public List<Reporte> findAll(String idEmpresa) {
        List<String> idsReportes = empresaService.findById(idEmpresa).getReportes();
        List<Reporte> reportes = new ArrayList<>();
        for (String idReporte : idsReportes) {
            reportes.add(reporteRepository.findById(idReporte).get());
        }
        return reportes;
    }

    public Reporte save(Reporte reporte, String idEmpresa) {
        Reporte response = reporteRepository.save(reporte);
        String idReporte = response.getId();
        empresaService.asociarReporte(idReporte, idEmpresa);
        return response;
    }

    public Reporte findById(String id) {
        return reporteRepository.findById(id).get();
    }

    public Reporte update(Reporte reporte) {
        return reporteRepository.save(reporte);
    }
}
