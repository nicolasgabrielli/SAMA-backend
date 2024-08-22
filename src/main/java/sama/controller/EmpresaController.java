package sama.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sama.dto.EmpresaDTO;
import sama.dto.ListadoEmpresaDTO;
import sama.entity.Empresa;
import sama.service.EmpresaService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/empresa")
public class EmpresaController {

    private final EmpresaService empresaService;

    /**
     * Obtiene una lista de todas las empresas.
     *
     * @return ResponseEntity con la lista de empresas.
     */
    @GetMapping
    public ResponseEntity<List<Empresa>> obtenerEmpresas() {
        List<Empresa> empresas = empresaService.findAll();
        if (empresas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(empresas);
    }

    /**
     * Obtiene una lista de nombres de todas las empresas.
     *
     * @return ResponseEntity con la lista de nombres de empresas.
     */
    @GetMapping("/nombres")
    public ResponseEntity<List<ListadoEmpresaDTO>> obtenerNombresEmpresas() {
        List<ListadoEmpresaDTO> empresas = empresaService.obtenerNombres();
        if (empresas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(empresas);
    }

    /**
     * Obtiene una empresa por su ID.
     *
     * @param id ID de la empresa.
     * @return ResponseEntity con la empresa encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> obtenerEmpresa(@PathVariable String id) {
        Empresa empresa = empresaService.findById(id);
        if (empresa == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(empresa);
    }

    /**
     * Elimina una empresa por su ID.
     *
     * @param id ID de la empresa a eliminar.
     * @return ResponseEntity con el resultado de la operación.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEmpresa(@PathVariable String id) {
        empresaService.deleteById(id);
        if (empresaService.findById(id) != null) {
            return ResponseEntity.status(500).body("Error al eliminar empresa");
        }
        return ResponseEntity.ok("Empresa eliminada");
    }

    /**
     * Actualiza una empresa.
     *
     * @param empresa DTO de la empresa a actualizar.
     * @return ResponseEntity con el resultado de la operación.
     */
    @PutMapping()
    public ResponseEntity<String> actualizarEmpresa(@RequestBody EmpresaDTO empresa) {
        Empresa data = new Empresa(empresa);
        int response = empresaService.update(data);
        if (response == 1) {
            return ResponseEntity.status(500).body("Error al actualizar empresa");
        }
        return ResponseEntity.ok("Empresa actualizada");
    }

    /**
     * Guarda una nueva empresa.
     *
     * @param empresa DTO de la empresa a guardar.
     * @return ResponseEntity con el resultado de la operación.
     */
    @PostMapping()
    public ResponseEntity<String> guardarEmpresa(@RequestBody EmpresaDTO empresa) {
        Empresa data = new Empresa(empresa);
        int response = empresaService.save(data);
        if (response == 1) {
            return ResponseEntity.status(500).body("Empresa ya existe");
        }
        return ResponseEntity.ok("Empresa guardada");
    }
}