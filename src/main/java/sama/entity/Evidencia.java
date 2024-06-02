package sama.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "evidencia")
public class Evidencia {
    @Id
    private String id;
    private String idReporte;
    private String nombre;
    private String tipo;
    private String url;
    private String rutaEvidencia;// Puede ser la url de una pagína web. O el nombre del archivo en el bucket de S3
}