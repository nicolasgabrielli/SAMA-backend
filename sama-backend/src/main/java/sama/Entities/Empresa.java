package sama.Entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
}