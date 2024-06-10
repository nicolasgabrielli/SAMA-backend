package sama.model;

import lombok.Getter;
import lombok.Setter;
import sama.entity.Evidencia;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Campo {
    private String titulo;
    private String tipo;
    private Object contenido;
    private List<Campo> subCampos;
    private List<Evidencia> evidencias;

    public Campo() {
        this.subCampos = new ArrayList<>();
    }

    public Campo(Campo nuevoCampo) {
        this.titulo = nuevoCampo.getTitulo();
        this.tipo = nuevoCampo.getTipo();
        this.contenido = nuevoCampo.getContenido();
        this.subCampos = nuevoCampo.getSubCampos();
        this.evidencias = nuevoCampo.getEvidencias();
    }

    public void actualizar(Campo nuevoCampo) {
        this.titulo = nuevoCampo.getTitulo();
        this.tipo = nuevoCampo.getTipo();
        this.contenido = nuevoCampo.getContenido();
        this.subCampos = nuevoCampo.getSubCampos();
        this.evidencias = nuevoCampo.getEvidencias();
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