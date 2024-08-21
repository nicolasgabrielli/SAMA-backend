package sama.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Categoria {
    private String titulo;
    private List<Seccion> secciones;

    /**
     * Constructor por defecto de Categoria.
     * Inicializa la lista de secciones como una lista vacía.
     */
    public Categoria() {
        this.secciones = new ArrayList<>();
    }

    /**
     * Constructor de Categoria con título.
     * Inicializa la categoría con un título específico y una lista vacía de secciones.
     *
     * @param nuevoTituloCategoria El título de la nueva categoría.
     */
    public Categoria(String nuevoTituloCategoria) {
        this.titulo = nuevoTituloCategoria;
        this.secciones = new ArrayList<>();
    }

    /**
     * Crea y devuelve una copia de esta categoría, incluyendo copias de sus secciones.
     * Las secciones son también clonadas y limpiadas mediante su propio método `clonarYLimpiar`.
     * Esto significa que cada sección dentro de la categoría clonada es una copia limpia de la original.
     *
     * @return Una nueva instancia de {@link Categoria} que es una copia de esta instancia,
     * pero con las secciones también clonadas y limpiadas.
     */
    public Categoria clonarYLimpiar() {
        Categoria clone = new Categoria();
        clone.setTitulo(this.titulo);
        if (this.secciones != null) {
            List<Seccion> seccionesClone = new ArrayList<>();
            for (Seccion seccion : this.secciones) {
                seccionesClone.add(seccion.clonarYLimpiar());
            }
            clone.setSecciones(seccionesClone);
        }
        return clone;
    }
}