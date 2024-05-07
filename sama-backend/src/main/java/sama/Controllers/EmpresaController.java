package sama.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sama.Entities.Empresa;
import sama.Services.EmpresaService;

import java.util.List;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {
    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    public List<Empresa> getEmpresa() {
        return empresaService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteEmpresa(@PathVariable String id) {
        empresaService.deleteById(id);
    }

    @PutMapping("/{id}")
    public Empresa updateEmpresa(@PathVariable String id, @RequestBody Empresa empresa) {
        return empresaService.update(id, empresa);
    }

    @PostMapping
    public Empresa saveEmpresa(@RequestBody Empresa empresa) {
        return empresaService.save(empresa);
    }
}