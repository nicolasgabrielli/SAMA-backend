package sama.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Seccion {
    private String titulo;
    private List<Campo> campos;

    /**
     * Constructor por defecto de Seccion.
     * Inicializa la lista de campos como una lista vacía.
     */
    public Seccion() {
        this.campos = new ArrayList<>();
    }

    /**
     * Constructor de Seccion con título.
     * Inicializa la sección con un título específico y una lista vacía de campos.
     *
     * @param nuevoTituloSeccion El título de la nueva sección.
     */
    public Seccion(String nuevoTituloSeccion) {
        this.titulo = nuevoTituloSeccion;
        this.campos = new ArrayList<>();
    }

    /**
     * Clona y limpia la sección actual.
     * Crea una copia profunda de la sección actual, incluyendo una copia de cada campo,
     * pero limpiando los datos específicos de cada campo mediante el método `clonarYLimpiar`.
     * Esto es útil para crear una copia limpia de la sección sin datos residuales.
     *
     * @return Una nueva instancia de {@link Seccion} que es una copia limpia de la instancia actual.
     */
    public Seccion clonarYLimpiar() {
        Seccion clone = new Seccion();
        clone.setTitulo(this.titulo);
        if (this.campos != null) {
            List<Campo> camposClone = new ArrayList<>();
            for (Campo campo : this.campos) {
                camposClone.add(campo.clonarYLimpiar());
            }
            clone.setCampos(camposClone);
        }
        return clone;
    }
}