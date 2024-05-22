package sama.Dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class EmpresaDTO {
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

    public EmpresaDTO(){
        //Method empty on purpose
    }
}
