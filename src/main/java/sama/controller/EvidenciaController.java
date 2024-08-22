package sama.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sama.entity.Evidencia;
import sama.service.EvidenciaService;

import java.io.InputStream;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/evidencia/")
public class EvidenciaController {

    private final EvidenciaService evidenciaService;

    /**
     * Sube una nueva evidencia para un reporte específico.
     *
     * @param idReporte ID del reporte al que se adjunta la evidencia.
     * @param nombre    Nombre de la evidencia.
     * @param tipo      Tipo de la evidencia.
     * @param url       URL opcional de la evidencia.
     * @param archivo   Archivo opcional de la evidencia.
     * @return ResponseEntity con el resultado de la operación.
     */
    @PostMapping("/{idReporte}")
    public ResponseEntity<String> subirEvidencia(@PathVariable String idReporte,
                                                 @RequestParam String nombre,
                                                 @RequestParam String tipo,
                                                 @RequestParam(value = "url", required = false) String url,
                                                 @RequestParam(value = "archivo", required = false) MultipartFile archivo) {
        String response = evidenciaService.subirEvidencia(idReporte, nombre, tipo, url, archivo);
        if (response == null) {
            return ResponseEntity.badRequest().body("Error al guardar la evidencia");
        }
        return ResponseEntity.ok("Evidencia guardada");
    }

    /**
     * Obtiene la lista de evidencias de un reporte específico.
     *
     * @param idReporte ID del reporte.
     * @return ResponseEntity con la lista de evidencias.
     */
    @GetMapping("/{idReporte}")
    public ResponseEntity<List<Evidencia>> obtenerEvidenciaReporte(@PathVariable String idReporte) {
        return ResponseEntity.ok(evidenciaService.obtenerEvidenciaReporte(idReporte));
    }

    /**
     * Obtiene la URL de S3 de una evidencia específica.
     *
     * @param idEvidencia ID de la evidencia.
     * @return ResponseEntity con la URL de S3.
     */
    @GetMapping("url/{idEvidencia}")
    public ResponseEntity<String> obtenerUrlS3(@PathVariable String idEvidencia) {
        return ResponseEntity.ok(evidenciaService.obtenerUrlS3(idEvidencia));
    }

    /**
     * Descarga una evidencia específica.
     *
     * @param idEvidencia ID de la evidencia.
     * @return ResponseEntity con el recurso de la evidencia.
     */
    @GetMapping("descargar/{idEvidencia}")
    public ResponseEntity<InputStreamResource> descargarEvidencia(@PathVariable String idEvidencia) {
        Evidencia evidencia = evidenciaService.obtenerEvidencia(idEvidencia);
        if (evidencia == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            InputStream inputStream = evidenciaService.descargarEvidencia(evidencia.getRutaEvidencia());
            if (inputStream == null) {
                return ResponseEntity.status(500).build();
            }
            String nombreArchivo = evidencia.getNombreOriginal();
            return ResponseEntity.ok()
                    .contentLength(inputStream.available())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreArchivo)
                    .body(new InputStreamResource(inputStream));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Elimina una evidencia específica.
     *
     * @param idEvidencia ID de la evidencia a eliminar.
     * @return ResponseEntity con el resultado de la operación.
     */
    @DeleteMapping("/{idEvidencia}")
    public ResponseEntity<String> eliminarEvidencia(@PathVariable String idEvidencia) {
        String response = evidenciaService.eliminarEvidencia(idEvidencia);
        if (response == null) {
            return ResponseEntity.badRequest().body("Error al eliminar la evidencia");
        }
        return ResponseEntity.ok("Evidencia eliminada");
    }
}