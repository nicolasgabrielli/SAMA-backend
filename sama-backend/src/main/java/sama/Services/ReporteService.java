package sama.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sama.Entities.Reporte;
import sama.Models.TuplaReporte;
import sama.Repositories.ReporteRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private EmpresaService empresaService;

    public List<TuplaReporte> findAll(String empresaId) {
        // Obtener lista de tuplas (año, id reporte) para una empresa
        List<Reporte> reportes = reporteRepository.findAllByEmpresaId(empresaId);
        List<TuplaReporte> tuplas = new ArrayList<>();
        for (Reporte reporte : reportes) {
            tuplas.add(new TuplaReporte(reporte.getAnio(), reporte.getId()));
        }
        return tuplas;
    }

    public Reporte save(Reporte reporte, String companyId) {
        Date dateNow = new Date();
        reporte.setFechaCreacion(dateNow);
        reporte.setFechaModificacion(dateNow);
        reporte.setEstado("Pendiente");
        reporte.setEmpresaId(companyId);
        return reporteRepository.save(reporte);
    }

    public Reporte findById(String id) {
        return reporteRepository.findById(id).get();
    }

    public Reporte update(Reporte reporte) {
        reporte.setFechaModificacion(new Date());
        return reporteRepository.save(reporte);
    }

    public boolean delete(String id) {
        try {
            reporteRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}