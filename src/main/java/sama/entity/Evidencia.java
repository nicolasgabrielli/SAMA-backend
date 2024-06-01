package sama.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection = "evidencia")
public class Evidencia {
    @Id
    private String id;
    private String idReporte;
    private String nombre;
    private String tipo;
    private String url; // URL de la evidencia, puede ser a una pagina web o a un archivo en el filesystem
    // Atributos que no se si incluir
    private String descripcion;
    private String idUsuario; // Usuario que subio la evidencia
    private Date fechaSubida;
}