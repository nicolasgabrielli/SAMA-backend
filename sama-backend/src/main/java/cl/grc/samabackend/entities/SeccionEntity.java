package cl.grc.samabackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seccion")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SeccionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    // Agregar más campos según sea necesario.

    // Relación muchos a muchos con la tabla reporte.
    @ManyToMany(mappedBy = "secciones")
    private List<ReporteEntity> reportes;
    
    // Relación uno a muchos con la tabla campo.
    @OneToMany(mappedBy = "campo", fetch = FetchType.EAGER)
    private List<CampoEntity> campos;
}
