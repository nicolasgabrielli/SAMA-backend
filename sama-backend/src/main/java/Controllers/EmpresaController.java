package Controllers;

import Entities.Empresa;
import Entities.Reporte;
import Services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {
    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    public List<Empresa> obtenerTodasLasEmpresas() {
        return empresaService.obtenerTodasLasEmpresas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> obtenerEmpresaPorId(@PathVariable String id) {
        Optional<Empresa> empresa = empresaService.obtenerEmpresaPorId(id);
        return empresa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Empresa> crearEmpresa(@RequestBody Empresa empresa) {
        Empresa nuevaEmpresa = empresaService.crearEmpresa(empresa);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaEmpresa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> actualizarEmpresa(@RequestBody Empresa empresaActualizada) {
        Empresa empresa = empresaService.actualizarEmpresa(empresaActualizada);
        return ResponseEntity.ok(empresa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable String id) {
        empresaService.eliminarEmpresa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/reportes")
    public ResponseEntity<List<Reporte>> obtenerReportesDeEmpresa(@PathVariable String id) {
        List<Reporte> reportes = empresaService.obtenerReportesDeEmpresa(id);
        return ResponseEntity.ok(reportes);
    }
}
