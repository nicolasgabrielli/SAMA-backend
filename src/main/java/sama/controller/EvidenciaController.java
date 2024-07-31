package sama.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sama.entity.Evidencia;
import sama.service.EvidenciaService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/evidencia/")
public class EvidenciaController {

    private final EvidenciaService evidenciaService;

    @PostMapping("/{idReporte}")
    public ResponseEntity<String> subirEvidencia(@PathVariable String idReporte,
                                                 @RequestParam String nombre,
                                                 @RequestParam String tipo,
                                                 @RequestParam(value = "url", required = false) String url,
                                                 @RequestParam(value = "archivo", required = false) MultipartFile archivo)
    {
        String response = evidenciaService.subirEvidencia(idReporte, nombre, tipo, url, archivo);
        if (response == null) {
            return ResponseEntity.badRequest().body("Error al guardar la evidencia");
        }
        return ResponseEntity.ok("Evidencia guardada");
    }


    @GetMapping("/{idReporte}")
    public ResponseEntity<List<Evidencia>> obtenerEvidenciaReporte(@PathVariable String idReporte) {
        return ResponseEntity.ok(evidenciaService.obtenerEvidenciaReporte(idReporte));
    }

    @GetMapping("url/{idEvidencia}")
    public ResponseEntity<String> obtenerUrlS3(@PathVariable String idEvidencia) {
        return ResponseEntity.ok(evidenciaService.obtenerUrlS3(idEvidencia));
    }

    @DeleteMapping("/{idEvidencia}")
    public ResponseEntity<String> eliminarEvidencia(@PathVariable String idEvidencia) {
        String response = evidenciaService.eliminarEvidencia(idEvidencia);
        if (response == null) {
            return ResponseEntity.badRequest().body("Error al eliminar la evidencia");
        }
        return ResponseEntity.ok("Evidencia eliminada");
    }
}