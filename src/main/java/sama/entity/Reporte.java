package sama.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import sama.model.Categoria;

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
    private String idUsuarioAutorizador;
    private List<Evidencia> evidencias;

    public Reporte(String empresaId) {
        //Method empty on purpose
    }

    /**
     * Constructor por defecto de Reporte.
     * Inicializa una nueva instancia de la clase Reporte sin establecer valores iniciales para los campos.
     * Este método está vacío intencionalmente y se utiliza para crear un objeto Reporte sin datos predefinidos.
     */
    public Reporte() {
        //Method empty on purpose
    }

    /**
     * Constructor para crear un nuevo reporte con detalles específicos.
     * Este constructor inicializa un reporte con el año, las categorías, las fechas de creación y modificación,
     * el estado del reporte y el ID de la empresa asociada.
     *
     * @param anio              El año del reporte.
     * @param categorias        La lista de categorías asociadas al reporte.
     * @param fechaCreacion     La fecha de creación del reporte.
     * @param fechaModificacion La fecha de última modificación del reporte.
     * @param estado            El estado actual del reporte.
     * @param empresaId         El ID de la empresa asociada al reporte.
     */
    public Reporte(int anio, List<Categoria> categorias, Date fechaCreacion, Date fechaModificacion, String estado, String empresaId) {
        this.anio = anio;
        this.categorias = categorias;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.estado = estado;
        this.empresaId = empresaId;
    }

    /**
     * Clona y limpia el reporte actual.
     * Crea una copia del reporte actual, incluyendo el año, las fechas de creación y modificación.
     * Para las categorías, realiza una copia profunda mediante el método `clonarYLimpiar` de cada categoría,
     * asegurando que no se incluyan datos residuales en las copias.
     * Este método es útil para trabajar con una versión limpia del reporte sin modificar el original.
     *
     * @return Una nueva instancia de {@link Reporte} que es una copia limpia del reporte actual.
     */
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