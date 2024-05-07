package sama.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sama.Entities.Empresa;
import sama.Repositories.EmpresaRepository;

import java.util.List;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }

    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public void asociarReporte(String idReporte, String idEmpresa) {
        Empresa empresa = empresaRepository.findById(idEmpresa).get();
        empresa.addReporte(idReporte);
        empresaRepository.save(empresa);
    }

    public Empresa findById(String id) {
        return empresaRepository.findById(id).get();
    }

    public void deleteById(String id) {
        empresaRepository.deleteById(id);
    }

    public Empresa update(String id, Empresa empresa) {
        empresa.setId(id);
        return empresaRepository.save(empresa);
    }

    public List<String> getReportes() {
        return empresaRepository.getReportes();
    }
}