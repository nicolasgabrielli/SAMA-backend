package sama.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Campo {
    private String titulo;
    private String tipo;
    private Object contenido;
    private List<Campo> subCampos;

    public Campo() {
        this.subCampos = new ArrayList<>();
    }

    public Campo(Campo nuevoCampo) {
        this.titulo = nuevoCampo.getTitulo();
        this.tipo = nuevoCampo.getTipo();
        this.contenido = nuevoCampo.getContenido();
        this.subCampos = nuevoCampo.getSubCampos();
    }

    public void actualizar(Campo nuevoCampo) {
        this.titulo = nuevoCampo.getTitulo();
        this.tipo = nuevoCampo.getTipo();
        this.contenido = nuevoCampo.getContenido();
        this.subCampos = nuevoCampo.getSubCampos();
    }

    public Campo clonarYLimpiar() {
        Campo clone = new Campo();
        clone.setTitulo(this.titulo);
        clone.setTipo(this.tipo);
        clone.setContenido("");
        if (this.subCampos != null) {
            List<Campo> subCamposClone = new ArrayList<>();
            for (Campo subCampo : this.subCampos) {
                subCamposClone.add(subCampo.clonarYLimpiar());
            }
            clone.setSubCampos(subCamposClone);
        }
        return clone;
    }
}