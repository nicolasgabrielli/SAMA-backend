package sama.Models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Seccion {
    private String titulo;
    private List<Campo> campos;
}
