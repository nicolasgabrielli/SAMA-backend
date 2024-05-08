package sama.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sama.DTO.CompanyName;
import sama.Entities.Company;
import sama.Services.CompanyService;

import java.util.List;

@RestController
@RequestMapping("/empresa")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<Company> getCompany() {
        return companyService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable String id) {
        companyService.deleteById(id);
    }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable String id, @RequestBody Company company) {
        return companyService.update(id, company);
    }

    @PostMapping("/crear")
    public Company saveCompany(@RequestBody CompanyName name) {
        return companyService.save(name.getName());
    }
}