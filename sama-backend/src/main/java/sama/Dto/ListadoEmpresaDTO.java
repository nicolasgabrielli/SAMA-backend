package sama.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListadoEmpresaDTO {
    private String nombre;
    private String id;

    public ListadoEmpresaDTO(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}