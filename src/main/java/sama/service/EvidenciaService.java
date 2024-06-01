package sama.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sama.entity.Evidencia;
import sama.repository.EvidenciaRepository;

@Service
public class EvidenciaService {

    @Autowired
    private EvidenciaRepository evidenciaRepository;

    public String save(String idReporte, Evidencia evidencia) {
        evidencia.setIdReporte(idReporte);
        try {
            evidenciaRepository.save(evidencia);
            return "Evidencia guardada";
        } catch (Exception e) {
            return null;
        }
    }
}
