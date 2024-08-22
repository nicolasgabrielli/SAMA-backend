package sama.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sama.dto.*;
import sama.entity.Reporte;
import sama.model.Campo;
import sama.model.Categoria;
import sama.model.Seccion;
import sama.service.ReporteDocumentService;
import sama.service.ReporteService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reporte")
public class ReporteController {

    private final ReporteService reporteService;

    private final ReporteDocumentService reporteDocumentService;

    /**
     * Obtiene una lista de reportes por empresa.
     *
     * @param empresaId ID de la empresa.
     * @return ResponseEntity con la lista de reportes.
     */
    @GetMapping("/por-empresa/{empresaId}")
    public ResponseEntity<List<EncabezadoReporteDTO>> obtenerReportesPorEmpresa(@PathVariable String empresaId) {
        List<EncabezadoReporteDTO> reportes = reporteService.findAll(empresaId);
        if (reportes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reportes);
    }

    /**
     * Obtiene un reporte por su ID.
     *
     * @param id ID del reporte.
     * @return ResponseEntity con el reporte encontrado.
     */
    @GetMapping("/por-id/{id}")
    public ResponseEntity<ReporteDTO> obtenerReporte(@PathVariable String id) {
        Reporte reporte = reporteService.findById(id);
        if (reporte == null) {
            return ResponseEntity.notFound().build();
        }
        ReporteDTO reporteDTO = new ReporteDTO(reporte);
        return ResponseEntity.ok(reporteDTO);
    }

    /**
     * Obtiene una lista de presets.
     *
     * @return ResponseEntity con la lista de presets.
     */
    @GetMapping("/preset")
    public ResponseEntity<List<EncabezadoReporteDTO>> obtenerPresets() {
        List<EncabezadoReporteDTO> presets = reporteService.findAllPresets();
        if (presets.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(presets);
    }

    /**
     * Obtiene un preset por su ID.
     *
     * @param id ID del preset.
     * @return ResponseEntity con el preset encontrado.
     */
    @GetMapping("/preset/{id}")
    public ResponseEntity<ReporteDTO> obtenerPreset(@PathVariable String id) {
        Reporte preset = reporteService.findById(id);
        if (preset == null) {
            return ResponseEntity.notFound().build();
        }
        ReporteDTO presetDTO = new ReporteDTO(preset);
        return ResponseEntity.ok(presetDTO);
    }

    /**
     * Obtiene una lista de categorías de un reporte.
     *
     * @param id ID del reporte.
     * @return ResponseEntity con la lista de categorías.
     */
    @GetMapping("/categorias/{id}")
    public ResponseEntity<List<Categoria>> obtenerCategorias(@PathVariable String id) {
        Reporte reporte = reporteService.findById(id);
        if (reporte == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reporte.getCategorias());
    }

    /**
     * Obtiene una lista de secciones de una categoría de un reporte.
     *
     * @param id          ID del reporte.
     * @param coordenadas Coordenadas del reporte.
     * @return ResponseEntity con la lista de secciones.
     */
    @GetMapping("/secciones/{id}")
    public ResponseEntity<List<Seccion>> obtenerSecciones(@PathVariable String id, @RequestBody CoordenadasReporteDTO coordenadas) {
        Reporte reporte = reporteService.findById(id);
        if (reporte == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reporte.getCategorias().get(coordenadas.getIndexCategoria()).getSecciones());
    }

    /**
     * Obtiene una lista de campos de una sección de una categoría de un reporte.
     *
     * @param id          ID del reporte.
     * @param coordenadas Coordenadas del reporte.
     * @return ResponseEntity con la lista de campos.
     */
    @GetMapping("/campos/{id}")
    public ResponseEntity<List<Campo>> obtenerCampos(@PathVariable String id, @RequestBody CoordenadasReporteDTO coordenadas) {
        Reporte reporte = reporteService.findById(id);
        if (reporte == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reporte.getCategorias().get(coordenadas.getIndexCategoria()).getSecciones().get(coordenadas.getIndexSeccion()).getCampos());
    }

    /**
     * Genera y obtiene un documento Word de un reporte.
     *
     * @param idReporte ID del reporte.
     * @return ResponseEntity con el documento Word.
     * @throws IOException Si ocurre un error al generar el documento.
     */
    @GetMapping("/word/{idReporte}")
    public ResponseEntity<byte[]> obtenerWord(@PathVariable String idReporte) throws IOException {
        byte[] pdf = reporteDocumentService.generarWord(idReporte);
        String tituloReporte = reporteService.findById(idReporte).getTitulo();
        String filename = tituloReporte + ".docx";
        if (pdf == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    /**
     * Genera y obtiene un documento PDF de un reporte.
     *
     * @param idReporte ID del reporte.
     * @return ResponseEntity con el documento PDF.
     * @throws Exception Si ocurre un error al generar el documento.
     */
    @GetMapping("/pdf/{idReporte}")
    public ResponseEntity<byte[]> obtenerPdf(@PathVariable String idReporte) throws Exception {
        byte[] pdf = reporteDocumentService.generarPdf(idReporte);
        String tituloReporte = reporteService.findById(idReporte).getTitulo();
        String filename = tituloReporte + ".pdf";
        if (pdf == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    /**
     * Crea un nuevo reporte.
     *
     * @param companyId ID de la empresa.
     * @param reporte   Objeto Reporte a crear.
     * @return ResponseEntity con el resultado de la operación.
     */
    @PostMapping("/crear/{companyId}")
    public ResponseEntity<String> crearReport(@PathVariable String companyId, @RequestBody Reporte reporte) {
        Reporte savedReport = reporteService.save(reporte, companyId);
        if (savedReport == null) {
            return ResponseEntity.status(500).body("Error al crear reporte");
        }
        return ResponseEntity.ok("Reporte creado");
    }

    /**
     * Crea un nuevo preset.
     *
     * @param infoPresetDTO Información del preset a crear.
     * @return ResponseEntity con el resultado de la operación.
     */
    @PostMapping("/preset")
    public ResponseEntity<String> crearPreset(@RequestBody InfoPresetDTO infoPresetDTO) {
        reporteService.crearPreset(infoPresetDTO);
        return ResponseEntity.ok("Preset creado");
    }

    /**
     * Actualiza un reporte.
     *
     * @param id             ID del reporte.
     * @param contenidoNuevo Información nueva del reporte.
     * @return ResponseEntity con el resultado de la operación.
     */
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> updateReport(@PathVariable String id, @RequestBody InfoActualizacionDTO contenidoNuevo) {
        Reporte reporte = reporteService.update(id, contenidoNuevo);
        if (reporte == null) {
            return ResponseEntity.status(500).body("Error al actualizar reporte");
        }
        return ResponseEntity.ok("Reporte actualizado");
    }

    /**
     * Reescribe un reporte.
     *
     * @param id           ID del reporte.
     * @param nuevoReporte Nuevo objeto Reporte.
     * @return ResponseEntity con el resultado de la operación.
     */
    @PutMapping("/actualizar/reescribir/{id}")
    public ResponseEntity<String> reescribirReporte(@PathVariable String id, @RequestBody Reporte nuevoReporte) {
        Reporte reporte = reporteService.reescribirReporte(id, nuevoReporte);
        if (reporte == null) {
            return ResponseEntity.status(500).body("Error al reescribir reporte");
        }
        return ResponseEntity.ok("Reporte reescrito");
    }

    /**
     * Autoriza un campo de un reporte.
     *
     * @param id                   ID del reporte.
     * @param autorizacionCampoDTO Información de autorización del campo.
     * @return ResponseEntity con el resultado de la operación.
     */
    @PutMapping("/autorizar/campo/{id}")
    public ResponseEntity<String> autorizarCampo(@PathVariable String id, @RequestBody AutorizacionCampoDTO autorizacionCampoDTO) {
        Reporte reporte = reporteService.autorizacionCampo(id, autorizacionCampoDTO.getCoordenadas(), autorizacionCampoDTO.getIdUsuario());
        if (reporte == null) {
            return ResponseEntity.status(500).body("Error al autorizar campo");
        }
        return ResponseEntity.ok("Campo autorizado");
    }

    /**
     * Autoriza todos los campos de un reporte.
     *
     * @param id                   ID del reporte.
     * @param autorizacionCampoDTO Información de autorización de los campos.
     * @return ResponseEntity con el resultado de la operación.
     */
    @PutMapping("/autorizar/all-campos/{id}")
    public ResponseEntity<String> autorizarAllCampos(@PathVariable String id, @RequestBody AutorizacionCampoDTO autorizacionCampoDTO) {
        Reporte reporte = reporteService.autorizarTodosLosCampos(id, autorizacionCampoDTO.getIdUsuario());
        if (reporte == null) {
            return ResponseEntity.status(500).body("Error al autorizar campos");
        }
        return ResponseEntity.ok("Campos autorizados");
    }

    /**
     * Elimina un reporte.
     *
     * @param id ID del reporte.
     * @return ResponseEntity con el resultado de la operación.
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable String id) {
        boolean deleted = reporteService.delete(id);
        if (!deleted) {
            return ResponseEntity.status(500).body("Error al eliminar reporte");
        }
        return ResponseEntity.ok("Reporte eliminado");
    }

    /**
     * Elimina contenido de un reporte.
     *
     * @param id          ID del reporte.
     * @param coordenadas Coordenadas del contenido a eliminar.
     * @return ResponseEntity con el resultado de la operación.
     */
    @DeleteMapping("/eliminar-contenido/{id}")
    public ResponseEntity<String> deleteContent(@PathVariable String id, @RequestBody CoordenadasReporteDTO coordenadas) {
        Reporte reporte = reporteService.eliminarContenido(id, coordenadas);
        if (reporte == null) {
            return ResponseEntity.status(500).body("Error al eliminar contenido");
        }
        return ResponseEntity.ok("Contenido eliminado");
    }


    /**
     * Elimina contenido de un reporte.
     *
     * @param id          ID del reporte.
     * @param coordenadas Coordenadas del contenido a eliminar.
     * @return ResponseEntity con el resultado de la operación.
     */
    @PutMapping("/eliminar-contenido/{id}")
    public ResponseEntity<String> deleteContent2(@PathVariable String id, @RequestBody CoordenadasReporteDTO coordenadas) {
        Reporte reporte = reporteService.eliminarContenido(id, coordenadas);
        if (reporte == null) {
            return ResponseEntity.status(500).body("Error al eliminar contenido");
        }
        return ResponseEntity.ok("Contenido eliminado");
    }
}