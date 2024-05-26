package sama.dto;

import lombok.Getter;
import lombok.Setter;
import sama.model.Campo;

@Getter
@Setter
public class InfoActualizacionDTO {
    private CoordenadasReporteDTO coordenadas;
    private String nuevoTituloCategoria;
    private String nuevoTituloSeccion;
    private Campo nuevoCampo;
}