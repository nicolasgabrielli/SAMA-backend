package cl.grc.samabackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empresa")        // Nombre de la tabla en la base de datos.
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmpresaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    // Agregar más campos según sea necesario.

    // Considerar cómo se les asignarán los valores a los empleados.
}
