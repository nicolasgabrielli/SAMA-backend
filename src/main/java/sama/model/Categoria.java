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

    public Categoria() {
        this.secciones = new ArrayList<>();
    }

    public Categoria(String nuevoTituloCategoria) {
        this.titulo = nuevoTituloCategoria;
        this.secciones = new ArrayList<>();
    }

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