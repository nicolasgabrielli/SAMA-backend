package sama.Entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import sama.Models.Categoria;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection = "reporte")
public class Reporte {

    @Id
    private String id;
    private int anio;
    private List<Categoria> categorias;
    private Date fechaCreacion;
    private Date fechaModificacion;
    private String estado;
    private String empresaId;

    public Reporte(String empresaId) {
    }

    public Reporte() {
    }

    public Reporte(int anio, List<Categoria> categorias, Date fechaCreacion, Date fechaModificacion, String estado, String empresaId) {
        this.anio = anio;
        this.categorias = categorias;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.estado = estado;
        this.empresaId = empresaId;
    }
}