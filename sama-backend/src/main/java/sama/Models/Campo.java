package sama.Models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Campo {
    private String nombre;
    private String tipo;
    private Object contenido;
    private List<Campo> subCampos;

    public void actualizar(Campo nuevoCampo) {
        this.nombre = nuevoCampo.getNombre();
        this.tipo = nuevoCampo.getTipo();
        this.contenido = nuevoCampo.getContenido();
        this.subCampos = nuevoCampo.getSubCampos();
    }
}