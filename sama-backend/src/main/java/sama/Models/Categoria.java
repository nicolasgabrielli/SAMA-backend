package sama.Models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Categoria {
    private String nombre;
    private List<Seccion> secciones;

    public Categoria clonarYLimpiar() {
        Categoria clone = new Categoria();
        clone.setNombre(this.nombre);
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