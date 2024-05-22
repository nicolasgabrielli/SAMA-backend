package sama.dto;

import lombok.Getter;
import lombok.Setter;
import sama.Models.Campo;

@Getter
@Setter
public class NuevoContenidoDTO {
    private Integer indexCategoria;
    private String nuevoTituloCategoria;
    private Integer indexSeccion;
    private String nuevoTituloSeccion;
    private Integer indexCampo;
    private Campo nuevoCampo;
}