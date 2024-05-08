package sama.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sama.Entities.Report;
import sama.Services.ReportService;

import java.util.List;

@RestController
@RequestMapping("/reporte")
public class ReportController {
    @Autowired
    private ReportService reportService;

    // REVISAR UTILIDAD DE ESTE ENDPOINT (Se puede lograr lo mismo al listar una empresa, es decir que se liste los id_report de una empresa)
    @GetMapping("/por-empresa/{companyId}")
    public List<Report> getReports(@PathVariable String companyId) {
        return reportService.findAll(companyId);
    }

    @GetMapping("/por-id/{id}")
    public Report getReportById(@PathVariable String id) {
        return reportService.findById(id);
    }

    @PutMapping("/actualizar")
    public Report updateReport(@RequestBody Report report) {
        return reportService.update(report);
    }

    @DeleteMapping("/eliminar/{id}")
    public void deleteReport(@PathVariable String id) {
        reportService.deleteById(id);
    }

    @PostMapping("/crear/{companyId}")
    public Report saveReport(@PathVariable String companyId, @RequestBody Report report) {
        return reportService.save(report, companyId);
    }
}