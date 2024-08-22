package sama.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import sama.dto.EmpresaDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "empresa")
public class Empresa {
    @Id
    private String id;

    private String nombre;
    private String tipoSociedad;
    private String rut;
    private String domicilioEmpresa;
    private String paginaWeb;
    private String email;
    private String domicilioContacto;
    private String telefono;
    private String razonSocial;

    /**
     * Constructor que crea una instancia de {@link Empresa} basada en un {@link EmpresaDTO}.
     * Este constructor copia los valores de los campos desde el objeto DTO proporcionado a la nueva instancia de Empresa.
     * Esto incluye el id, nombre, tipo de sociedad, RUT, domicilio de la empresa, página web, email, domicilio de contacto y teléfono.
     *
     * @param copy El objeto {@link EmpresaDTO} del cual se copian los datos.
     */
    public Empresa(EmpresaDTO copy) {
        this.id = copy.getId();
        this.nombre = copy.getNombre();
        this.tipoSociedad = copy.getTipoSociedad();
        this.rut = copy.getRut();
        this.domicilioEmpresa = copy.getDomicilioEmpresa();
        this.paginaWeb = copy.getPaginaWeb();
        this.email = copy.getEmail();
        this.domicilioContacto = copy.getDomicilioContacto();
        this.telefono = copy.getTelefono();
        this.razonSocial = copy.getRazonSocial();
    }
}