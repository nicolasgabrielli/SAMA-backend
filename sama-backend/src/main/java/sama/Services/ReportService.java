package sama.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sama.Entities.Report;
import sama.Models.ReportTuple;
import sama.Repositories.ReportRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private CompanyService companyService;

    public List<Report> findAll(String companyId) {

        List<ReportTuple> aux = companyService.findById(companyId).getReports();
        List<String> idsReportes = aux.stream().map(ReportTuple::getReportId).toList();
        List<Report> reports = new ArrayList<>();
        for (String idReporte : idsReportes) {
            reports.add(reportRepository.findById(idReporte).get());
        }
        return reports;
    }

    public Report save(Report report, String companyId) {
        Report response = reportRepository.save(report);
        companyService.asociarReporte(response, companyId);
        return response;
    }

    public Report findById(String id) {
        return reportRepository.findById(id).get();
    }

    public Report update(Report report) {
        return reportRepository.save(report);
    }
}
