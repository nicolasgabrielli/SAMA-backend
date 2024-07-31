package sama.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sama.entity.Evidencia;
import sama.repository.EvidenciaRepository;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EvidenciaService {

    private final EvidenciaRepository evidenciaRepository;

    private final S3Client s3Client;

    private final S3Presigner s3Presigner;

    private final ReporteService reporteService;

    public String subirEvidencia(String idReporte, String nombre, String tipo, String url, MultipartFile archivo) {
        Evidencia evidencia = new Evidencia();
        evidencia.setIdReporte(idReporte);
        evidencia.setNombre(nombre);
        evidencia.setTipo(tipo);
        evidencia.setUrl(url);
        evidencia.setNombreOriginal(nombre);
        if (archivo != null && !archivo.isEmpty()) {
            evidencia.setNombreOriginal(archivo.getOriginalFilename());
            String nombreUnico = generarNombreUnico(archivo.getOriginalFilename(), reporteService.findById(idReporte).getTitulo());
            try {
                PutObjectRequest request = PutObjectRequest.builder()
                        .bucket("sama-testing")
                        .key(nombreUnico)
                        .build();
                RequestBody requestBody = RequestBody.fromInputStream(archivo.getInputStream(), archivo.getSize());
                s3Client.putObject(request, requestBody);
                evidencia.setRutaEvidencia(nombreUnico);

            } catch (S3Exception | IOException e) {
                return null;
            }
        }
        Evidencia evidenciaGuardada = evidenciaRepository.save(evidencia);
        reporteService.asociarEvidencia(idReporte, evidenciaGuardada);
        return "Evidencia guardada";
    }

    private String generarNombreUnico(String nombreOriginal, String tituloReporte) {
        UUID uuid = UUID.randomUUID();
        String seccionUUID = uuid.toString().substring(0,4);
        return tituloReporte + " - " + nombreOriginal + " - " + seccionUUID;
    }

    public List<Evidencia> obtenerEvidenciaReporte(String idReporte) {
        return evidenciaRepository.findByIdReporte(idReporte);
    }

    public URL generarURLTemporal(String bucketName, String rutaArchivo) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(rutaArchivo)
                    .build();

            GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))
                    .getObjectRequest(getObjectRequest)
                    .build();

            PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(getObjectPresignRequest);

            return presignedGetObjectRequest.url();
        } catch (Exception e) {
            return null;
        }
    }

    public String eliminarEvidencia(String idEvidencia) {
        Evidencia evidencia = evidenciaRepository.findById(idEvidencia).orElse(null);
        if (evidencia == null) {
            return null;
        }
        evidenciaRepository.delete(evidencia);
        if (evidencia.getRutaEvidencia() != null) {
            try {
                s3Client.deleteObject(builder -> builder.bucket("sama-testing").key(evidencia.getRutaEvidencia()));
            } catch (S3Exception e) {
                return null;
            }
        }
        return "Evidencia eliminada";
    }

    public String obtenerUrlS3(String idEvidencia) {
        Evidencia evidencia = evidenciaRepository.findById(idEvidencia).orElse(null);
        if (evidencia == null) {
            return null;
        }
        return generarURLTemporal("sama-testing", evidencia.getRutaEvidencia()).toString();
    }
}