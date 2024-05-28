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

    public Seccion() {
        this.campos = new ArrayList<>();
    }

    public Seccion(String nuevoTituloSeccion) {
        this.titulo = nuevoTituloSeccion;
        this.campos = new ArrayList<>();
    }

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
