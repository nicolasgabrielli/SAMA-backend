package Entities;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "empresas")
public class Empresa {
    @Id
    private ObjectId id;

    private String nombre;
    private List<String> reportes;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }


    public List<String> getReportes() {
        return reportes;
    }

    public void setReportes(List<String> reportes) {
        this.reportes = reportes;
    }
}
