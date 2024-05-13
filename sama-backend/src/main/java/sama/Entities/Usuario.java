package sama.Entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "user")
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
}
