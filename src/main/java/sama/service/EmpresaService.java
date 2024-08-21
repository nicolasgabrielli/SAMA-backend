package sama.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sama.dto.ListadoEmpresaDTO;
import sama.entity.Empresa;
import sama.repository.EmpresaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }

    public int save(Empresa empresa) {
        if (empresaRepository.existsByRut(empresa.getRut())) { // Se puede validar por otro atributo si se desea
            return 1; // Empresa ya existe
        }
        empresaRepository.save(empresa);
        return 0; // Empresa guardada
    }

    public int update(Empresa empresa) {

        if(!empresaRepository.existsById(empresa.getId())) {
            return 1; // Empresa no existe
        }

        Empresa updatedEmpresa = empresaRepository.findById(empresa.getId()).get();

        updatedEmpresa.setNombre(Optional.ofNullable(empresa.getNombre()).orElse(updatedEmpresa.getNombre()));
        updatedEmpresa.setTipoSociedad(Optional.ofNullable(empresa.getTipoSociedad()).orElse(updatedEmpresa.getTipoSociedad()));
        updatedEmpresa.setRut(Optional.ofNullable(empresa.getRut()).orElse(updatedEmpresa.getRut()));
        updatedEmpresa.setDomicilioEmpresa(Optional.ofNullable(empresa.getDomicilioEmpresa()).orElse(updatedEmpresa.getDomicilioEmpresa()));
        updatedEmpresa.setPaginaWeb(Optional.ofNullable(empresa.getPaginaWeb()).orElse(updatedEmpresa.getPaginaWeb()));
        updatedEmpresa.setEmail(Optional.ofNullable(empresa.getEmail()).orElse(updatedEmpresa.getEmail()));
        updatedEmpresa.setDomicilioContacto(Optional.ofNullable(empresa.getDomicilioContacto()).orElse(updatedEmpresa.getDomicilioContacto()));
        updatedEmpresa.setTelefono(Optional.ofNullable(empresa.getTelefono()).orElse(updatedEmpresa.getTelefono()));
        updatedEmpresa.setRazonSocial(Optional.ofNullable(empresa.getRazonSocial()).orElse(updatedEmpresa.getRazonSocial()));

        empresaRepository.save(updatedEmpresa);

        return 0; // Empresa actualizada
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