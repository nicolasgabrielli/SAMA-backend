package sama.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfoAutorizador {
    private String nombreAutorizador;
    private String correoAutorizador;
    private Date fechaAutorizacion;
}