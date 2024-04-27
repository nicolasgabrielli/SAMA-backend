package cl.grc.samabackend.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CampoRepository extends JpaRepository<CampoRepository, Integer>{
    
}
