package Services;

import Entities.Empresa;
import Entities.Reporte;
import Repositories.EmpresaRepository;
import Repositories.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private ReporteService reporteService;

    public List<Empresa> obtenerTodasLasEmpresas() {
        return empresaRepository.findAll();
    }
    public Optional<Empresa> obtenerEmpresaPorId(String id) {
        return empresaRepository.findById(id);
    }

    public Empresa crearEmpresa(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public void eliminarEmpresa(String id) {
        empresaRepository.delete(empresaRepository.findById(id).get());
    }

    public Empresa actualizarEmpresa(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public List<Reporte> obtenerReportesDeEmpresa(String idEmpresa) {
        return reporteService.obtenerReportesPorEmpresa(idEmpresa);
    }
}
