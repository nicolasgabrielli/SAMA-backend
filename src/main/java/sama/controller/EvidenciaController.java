package sama.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sama.entity.Evidencia;
import sama.service.EvidenciaService;

@RestController
@RequestMapping("/api/evidencia/")
public class EvidenciaController {
    @Autowired
    private EvidenciaService evidenciaService;

    @PostMapping("/{idReporte}")
    public ResponseEntity<String> save(@PathVariable String idReporte, @RequestBody Evidencia evidencia) {
        String response = evidenciaService.save(idReporte, evidencia);
        if (response == null) {
            return ResponseEntity.badRequest().body("Error al guardar la evidencia");
        }
        return ResponseEntity.ok("Evidencia guardada");
    }
}
