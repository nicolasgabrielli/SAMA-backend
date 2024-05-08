package sama.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sama.Entities.Company;
import sama.Entities.Report;
import sama.Repositories.CompanyRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company save(String companyName) {
        Company company = new Company();
        company.setName(companyName);
        company.setReports(new ArrayList<>());
        return companyRepository.save(company);
    }

    public void asociarReporte(Report report, String idEmpresa) {
        Company company = companyRepository.findById(idEmpresa).get();
        company.addReport(report);
        companyRepository.save(company);
    }

    public Company findById(String id) {
        return companyRepository.findById(id).get();
    }

    public void deleteById(String id) {
        companyRepository.deleteById(id);
    }

    public Company update(String id, Company company) {
        company.setId(id);
        return companyRepository.save(company);
    }

    public void desasociarReporte(String id) {
        List<Company> companies = companyRepository.findAll();
        for (Company company : companies) {
            company.getReports().removeIf(report -> report.getReportId().equals(id));
            companyRepository.save(company);
        }
    }
}