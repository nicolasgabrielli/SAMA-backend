package sama.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sama.dto.*;
import sama.entity.Reporte;
import sama.model.Campo;
import sama.model.Categoria;
import sama.model.Seccion;
import sama.service.ReporteService;

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

    @GetMapping("/preset")
    public ResponseEntity<List<EncabezadoReporteDTO>> obtenerPresets() {
        List<EncabezadoReporteDTO> presets = reporteService.findAllPresets();
        if (presets.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(presets);
    }

    @GetMapping("/preset/{id}")
    public ResponseEntity<ReporteDTO> obtenerPreset(@PathVariable String id) {
        Reporte preset = reporteService.findById(id);
        if (preset == null) {
            return ResponseEntity.notFound().build();
        }
        ReporteDTO presetDTO = new ReporteDTO(preset);
        return ResponseEntity.ok(presetDTO);
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<List<Categoria>> obtenerCategorias(@PathVariable String id){
        Reporte reporte = reporteService.findById(id);
        if (reporte == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reporte.getCategorias());
    }

    @GetMapping("/secciones/{id}")
    public ResponseEntity<List<Seccion>> obtenerSecciones(@PathVariable String id, @RequestBody CoordenadasReporteDTO coordenadas){
        Reporte reporte = reporteService.findById(id);
        if (reporte == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reporte.getCategorias().get(coordenadas.getIndexCategoria()).getSecciones());
    }

    @GetMapping("/campos/{id}")
    public ResponseEntity<List<Campo>> obtenerCampos(@PathVariable String id, @RequestBody CoordenadasReporteDTO coordenadas){
        Reporte reporte = reporteService.findById(id);
        if (reporte == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reporte.getCategorias().get(coordenadas.getIndexCategoria()).getSecciones().get(coordenadas.getIndexSeccion()).getCampos());
    }

    @PostMapping("/crear/{companyId}")
    public ResponseEntity<String> crearReport(@PathVariable String companyId, @RequestBody Reporte reporte) {
        Reporte savedReport = reporteService.save(reporte, companyId);
        if (savedReport == null) {
            return ResponseEntity.status(500).body("Error al crear reporte");
        }
        return ResponseEntity.ok("Reporte creado");
    }

    @PostMapping("/preset")
    public ResponseEntity<String> crearPreset(@RequestBody InfoPresetDTO infoPresetDTO) {
        reporteService.crearPreset(infoPresetDTO);
        return ResponseEntity.ok("Preset creado");
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> updateReport(@PathVariable String id, @RequestBody InfoActualizacionDTO contenidoNuevo) {
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

    @DeleteMapping("/eliminar-contenido/{id}")
    public ResponseEntity<String> deleteContent(@PathVariable String id, @RequestBody CoordenadasReporteDTO coordenadas) {
        Reporte reporte = reporteService.eliminarContenido(id, coordenadas);
        if (reporte == null) {
            return ResponseEntity.status(500).body("Error al eliminar contenido");
        }
        return ResponseEntity.ok("Contenido eliminado");
    }

    @PutMapping("/eliminar-contenido/{id}")
    public ResponseEntity<String> deleteContent2(@PathVariable String id, @RequestBody CoordenadasReporteDTO coordenadas) {
        Reporte reporte = reporteService.eliminarContenido(id, coordenadas);
        if (reporte == null) {
            return ResponseEntity.status(500).body("Error al eliminar contenido");
        }
        return ResponseEntity.ok("Contenido eliminado");
    }
}