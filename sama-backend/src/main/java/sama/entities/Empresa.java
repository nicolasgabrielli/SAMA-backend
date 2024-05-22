package sama.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import sama.dto.EmpresaDTO;

@Setter
@Getter
@Document(collection = "empresa")
public class Empresa {
    @Id
    private String id;

    private String nombre;
    private String tipoSociedad;
    private String rut;
    private String domicilioEmpresa;
    private String paginaWeb;
    private String email;
    private String domicilioContacto;
    private String telefono;

    public Empresa() {
    }
    public Empresa(EmpresaDTO copy) {
        this.id = copy.getId();
        this.nombre = copy.getNombre();
        this.tipoSociedad = copy.getTipoSociedad();
        this.rut = copy.getRut();
        this.domicilioEmpresa = copy.getDomicilioEmpresa();
        this.paginaWeb = copy.getPaginaWeb();
        this.email = copy.getEmail();
        this.domicilioContacto = copy.getDomicilioContacto();
        this.telefono = copy.getTelefono();
    }
}