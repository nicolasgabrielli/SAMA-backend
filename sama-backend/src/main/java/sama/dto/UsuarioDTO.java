package sama.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
public class UsuarioDTO {
    @Id
    private String id;
    private String nombre;
    private String contrasenia;
    private String correo;
    private String rol;
    private List<String> empresas;

    public UsuarioDTO(){
        //Method empty on purpose
    }
}
