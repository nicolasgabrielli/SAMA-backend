package sama.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "empresas")
public class Empresa {

    @Id
    private String id;

    private String nombre;

    @DBRef
    private List<String> reportes;

    public Empresa() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getReportes() {
        return reportes;
    }

    public void setReportes(List<String> reportes) {
        this.reportes = reportes;
    }

    public void addReporte(String idReporte) {
        reportes.add(idReporte);
    }
}