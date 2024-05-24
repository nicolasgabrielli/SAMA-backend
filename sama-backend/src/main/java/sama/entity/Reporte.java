package sama.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import sama.models.Categoria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection = "reporte")
public class Reporte {

    @Id
    private String id;
    private Integer anio;
    private String titulo;
    private List<Categoria> categorias;
    private Date fechaCreacion;
    private Date fechaModificacion;
    private String estado;
    private String empresaId;

    public Reporte(String empresaId) {
        //Method empty on purpose
    }

    public Reporte() {
        //Method empty on purpose
    }

    public Reporte(int anio, List<Categoria> categorias, Date fechaCreacion, Date fechaModificacion, String estado, String empresaId) {
        this.anio = anio;
        this.categorias = categorias;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.estado = estado;
        this.empresaId = empresaId;
    }

    public Reporte clonarYLimpiar() {
        Reporte clone = new Reporte();
        clone.setAnio(this.anio);
        clone.setFechaCreacion(this.fechaCreacion);
        clone.setFechaModificacion(this.fechaModificacion);
        if (this.categorias != null) {
            List<Categoria> categoriasClone = new ArrayList<>();
            for (Categoria categoria : this.categorias) {
                categoriasClone.add(categoria.clonarYLimpiar());
            }
            clone.setCategorias(categoriasClone);
        }
        return clone;
    }
}