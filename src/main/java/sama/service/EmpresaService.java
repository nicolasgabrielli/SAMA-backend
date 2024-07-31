package sama.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sama.dto.ListadoEmpresaDTO;
import sama.entity.Empresa;
import sama.repository.EmpresaRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }

    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public void deleteById(String id) {
        empresaRepository.deleteById(id);
    }

    public List<ListadoEmpresaDTO> obtenerNombres() {
        List<Empresa> empresas = empresaRepository.findAll();
        List<ListadoEmpresaDTO> nombres = new ArrayList<>();
        for (Empresa empresa : empresas) {
            nombres.add(new ListadoEmpresaDTO(empresa.getId(), empresa.getNombre()));
        }
        return nombres;
    }

    public Empresa findById(String id) {
        return empresaRepository.findById(id).orElse(null);
    }
}