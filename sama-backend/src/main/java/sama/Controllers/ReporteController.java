package sama.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sama.DTO.ReporteDTO;
import sama.Entities.Reporte;
import sama.Services.ReporteService;

import java.util.List;

@RestController
@RequestMapping("/reporte")
public class ReporteController {
    @Autowired
    private ReporteService reporteService;

    @GetMapping("/por-empresa/{idEmpresa}")
    public List<Reporte> getReportes(@RequestParam String idEmpresa) {
        return reporteService.findAll(idEmpresa);
    }

    @GetMapping("/por-id/{id}")
    public Reporte getReporteById(@PathVariable String id) {
        return reporteService.findById(id);
    }

    @PutMapping
    public Reporte updateReporte(@RequestBody Reporte reporte) {
        return reporteService.update(reporte);
    }

    @PostMapping
    public Reporte saveReporte(@RequestBody ReporteDTO reporteDTO) {
        return reporteService.save(reporteDTO.getReporte(), reporteDTO.getIdEmpresa());
    }
}