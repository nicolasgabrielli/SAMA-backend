package cl.grc.samabackend.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ReporteRepository extends JpaRepository<ReporteRepository, Integer>{
    
}
