package sama.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sama.dto.EmpresaDTO;
import sama.dto.ListadoEmpresaDTO;
import sama.entity.Empresa;
import sama.service.EmpresaService;

import java.util.List;

@RestController
@RequestMapping("/api/empresa")
public class EmpresaController {
    @Autowired
    private EmpresaService empresaService;

    @GetMapping
// Entrega una lista de todas las empresas (con todos sus datos: id, nombre, reportes). Revisar si se ocupara.
    public ResponseEntity<List<Empresa>> obtenerEmpresas() {
        List<Empresa> empresas = empresaService.findAll();
        if (empresas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/nombres") // Entrega una lista de "ElementoListaEmpresa" que contiene el id y nombre de cada empresa
    public ResponseEntity<List<ListadoEmpresaDTO>> obtenerNombresEmpresas() {
        List<ListadoEmpresaDTO> empresas = empresaService.obtenerNombres();
        if (empresas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> obtenerEmpresa(@PathVariable String id) {
        Empresa empresa = empresaService.findById(id);
        if (empresa == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(empresa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEmpresa(@PathVariable String id) {
        empresaService.deleteById(id);
        if (empresaService.findById(id) != null) {
            return ResponseEntity.status(500).body("Error al eliminar empresa");
        }
        return ResponseEntity.ok("Empresa eliminada");
    }

    @PutMapping()
    public ResponseEntity<String> actualizarEmpresa(@RequestBody EmpresaDTO empresa) {
        Empresa data = new Empresa(empresa);
        Empresa updatedEmpresa = empresaService.save(data);
        if (updatedEmpresa == null) {
            return ResponseEntity.status(500).body("Error al actualizar empresa");
        }
        return ResponseEntity.ok("Empresa actualizada");
    }

    @PostMapping()
    public ResponseEntity<String> guardarEmpresa(@RequestBody EmpresaDTO empresa) {
        Empresa data = new Empresa(empresa);
        Empresa savedEmpresa = empresaService.save(data);
        if (savedEmpresa == null) {
            return ResponseEntity.status(500).body("Error al guardar empresa");
        }
        return ResponseEntity.ok("Empresa guardada");
    }
}