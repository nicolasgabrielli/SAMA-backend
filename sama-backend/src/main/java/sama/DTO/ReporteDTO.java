package sama.DTO;

import sama.Entities.Reporte;

public class ReporteDTO {
    private Reporte reporte;
    private String idEmpresa;

    public Reporte getReporte() {
        return reporte;
    }

    public void setReporte(Reporte reporte) {
        this.reporte = reporte;
    }

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
}