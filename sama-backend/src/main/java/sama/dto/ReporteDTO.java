package sama.dto;

import lombok.Getter;
import lombok.Setter;
import sama.entities.Reporte;
import sama.Models.Categoria;

import java.util.List;

@Getter
@Setter
public class ReporteDTO {
    private String id;
    private int anio;
    private List<Categoria> categorias;
    private String estado;
    private String empresaId;

    public ReporteDTO(Reporte reporte) {
        this.id = reporte.getId();
        this.anio = reporte.getAnio();
        this.categorias = reporte.getCategorias();
        this.estado = reporte.getEstado();
        this.empresaId = reporte.getEmpresaId();
    }
}