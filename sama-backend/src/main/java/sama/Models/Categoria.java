package sama.Models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Categoria {
    private String nombre;
    private List<Seccion> secciones;
}