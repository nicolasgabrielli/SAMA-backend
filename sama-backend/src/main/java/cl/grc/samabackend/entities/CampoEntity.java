package cl.grc.samabackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "campo")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CampoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer tipo_de_campo;  // 1: Boolean, 2: Table, 3: Text
    
    private Boolean valor;
    private String tabla;           // Aquí falta definir que tipo de dato podría tener la tabla. Pero por ahora se deja como String. 
    private String texto;

    // Agregar más campos según sea necesario.
    @ManyToOne
    @JoinColumn(name = "seccion_id", referencedColumnName = "id")
    private SeccionEntity seccion;
}
