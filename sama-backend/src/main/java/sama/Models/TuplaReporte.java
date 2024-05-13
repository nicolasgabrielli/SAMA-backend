package sama.Models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TuplaReporte {
    private int anio;
    private String reporteId;

    public TuplaReporte() {
    }

    public TuplaReporte(int anio, String reporteId) {
        this.anio = anio;
        this.reporteId = reporteId;
    }
}