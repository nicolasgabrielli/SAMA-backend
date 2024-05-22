package sama.dto;

import lombok.Getter;
import lombok.Setter;
import sama.entities.Reporte;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class EncabezadoReporteDTO {
    private String id;
    private String titulo;
    private int anio;
    private String estado;
    private String fechaCreacion;
    private String fechaModificacion;

    public EncabezadoReporteDTO(Reporte reporte) {
        this.id = reporte.getId();
        this.titulo = reporte.getTitulo();
        this.anio = reporte.getAnio();
        this.estado = reporte.getEstado();
        this.fechaCreacion = transformarFecha(reporte.getFechaCreacion());
        this.fechaModificacion = transformarFecha(reporte.getFechaModificacion());
    }

    private String transformarFecha(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return formato.format(fecha);
    }
}
