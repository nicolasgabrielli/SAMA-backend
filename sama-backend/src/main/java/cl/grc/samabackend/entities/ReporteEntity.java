package cl.grc.samabackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "reporte")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReporteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre_estandar;
    private String año;
    
    // Agregar más campos según sea necesario.

    // Relación muchos a muchos con la tabla seccion.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "reporte_seccion",
        joinColumns = @JoinColumn(name = "reporte_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "seccion_id", referencedColumnName = "id"))
    private List<SeccionEntity> secciones;


    
}
