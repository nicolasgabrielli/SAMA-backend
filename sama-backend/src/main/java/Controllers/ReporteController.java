package Controllers;

import Entities.Reporte;
import Services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@RestController
@RequestMapping("/reporte")
public class ReporteController {
    @Autowired
    private ReporteService reporteService;

    @GetMapping("/{id}")
    public ResponseEntity<Reporte> obtenerReportePorId(@PathVariable String id) {
        Optional<Reporte> reporte = reporteService.obtenerReportePorId(id);
        return reporte.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Reporte> crearReporte(@RequestBody Reporte reporte) {
        Reporte nuevoReporte = reporteService.crearReporte(reporte);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoReporte);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reporte> actualizarReporte(@PathVariable String id, @RequestBody Reporte reporteActualizado) {
        Reporte reporte = reporteService.actualizarReporte(reporteActualizado);
        return ResponseEntity.ok(reporte);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReporte(@PathVariable String id) {
        reporteService.eliminarReporte(id);
        return ResponseEntity.noContent().build();
    }
}
