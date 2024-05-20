package sama.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sama.DTO.NuevoContenidoDTO;
import sama.DTO.EncabezadoReporteDTO;
import sama.DTO.ReporteDTO;
import sama.Entities.Reporte;
import sama.Services.ReporteService;

import java.util.List;

@RestController
@RequestMapping("/api/reporte")
public class ReporteController {
    @Autowired
    private ReporteService reporteService;

    @GetMapping("/por-empresa/{empresaId}")
    public ResponseEntity<List<EncabezadoReporteDTO>> obtenerReportesPorEmpresa(@PathVariable String empresaId) {
        List<EncabezadoReporteDTO> reportes = reporteService.findAll(empresaId);
        if (reportes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reportes);
    }

    @GetMapping("/por-id/{id}")
    public ResponseEntity<ReporteDTO> obtenerReporte(@PathVariable String id) {
        Reporte reporte = reporteService.findById(id);
        if (reporte == null) {
            return ResponseEntity.notFound().build();
        }
        ReporteDTO reporteDTO = new ReporteDTO(reporte);
        return ResponseEntity.ok(reporteDTO);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> updateReport(@PathVariable String id, @RequestBody NuevoContenidoDTO contenidoNuevo) {
        Reporte reporte = reporteService.update(id, contenidoNuevo);
        if (reporte == null) {
            return ResponseEntity.status(500).body("Error al actualizar reporte");
        }
        return ResponseEntity.ok("Reporte actualizado");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable String id) {
        boolean deleted = reporteService.delete(id);
        if (!deleted) {
            return ResponseEntity.status(500).body("Error al eliminar reporte");
        }
        return ResponseEntity.ok("Reporte eliminado");
    }

    @PostMapping("/crear/{companyId}")
    public ResponseEntity<String> saveReport(@PathVariable String companyId, @RequestBody Reporte reporte) {
        Reporte savedReport = reporteService.save(reporte, companyId);
        if (savedReport == null) {
            return ResponseEntity.status(500).body("Error al crear reporte");
        }
        return ResponseEntity.ok("Reporte creado");
    }
}