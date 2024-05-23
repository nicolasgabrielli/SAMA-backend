package sama.Dto;

import lombok.Getter;
import lombok.Setter;
import sama.Models.Campo;

@Getter
@Setter
public class InfoActualizacionDTO {
    private Integer indexCategoria;
    private String nuevoTituloCategoria;
    private Integer indexSeccion;
    private String nuevoTituloSeccion;
    private Integer indexCampo;
    private Campo nuevoCampo;
}