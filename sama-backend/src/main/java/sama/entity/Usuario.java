package sama.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import sama.dto.UsuarioDTO;

import java.util.List;

@Getter
@Setter
@Document(collection = "usuario")
public class Usuario {
    @Id
    private String id;
    private String nombre;
    private String contrasenia;
    private String correo;
    private String rol;
    private List<String> empresas;

    public Usuario() {

    }

    public Usuario(UsuarioDTO copy){
        this.id = copy.getId();
        this.nombre = copy.getNombre();
        this.contrasenia = copy.getContrasenia();
        this.correo = copy.getCorreo();
        this.rol = copy.getRol();
        this.empresas = copy.getEmpresas();
    }
}