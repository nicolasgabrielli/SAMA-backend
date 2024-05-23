package sama.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sama.Dto.InfoPresetDTO;
import sama.Dto.NuevoContenidoDTO;
import sama.Dto.EncabezadoReporteDTO;
import sama.Entities.Reporte;
import sama.Models.Campo;
import sama.Models.Categoria;
import sama.Models.Seccion;
import sama.Repositories.ReporteRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;
    @Autowired
    private EmpresaService empresaService;

    public List<EncabezadoReporteDTO> findAll(String empresaId) {
        // Obtener lista de tuplas (año, id reporte) para una empresa
        List<Reporte> reportes = reporteRepository.findAllByEmpresaId(empresaId);
        List<EncabezadoReporteDTO> encabezados = new ArrayList<>();
        for (Reporte reporte : reportes) {
            EncabezadoReporteDTO encabezado = new EncabezadoReporteDTO(reporte);
            encabezados.add(encabezado);
        }
        return encabezados.stream().sorted(Comparator.comparing(EncabezadoReporteDTO::getAnio)).toList();
    }

    public Reporte save(Reporte reporte, String companyId) {
        Date dateNow = new Date();
        reporte.setFechaCreacion(dateNow);
        reporte.setFechaModificacion(dateNow);
        reporte.setEstado("Pendiente");
        reporte.setEmpresaId(companyId);
        return reporteRepository.save(reporte);
    }

    public Reporte findById(String id) {
            return reporteRepository.findById(id).get();
    }

    public Reporte update(String id, NuevoContenidoDTO contenidoNuevo) {
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));

        if (contenidoNuevo.getIndexCategoria() != null) {
            updateCategoria(reporte, contenidoNuevo);
        }

        return reporteRepository.save(reporte);
    }

    private void updateCategoria(Reporte reporte, NuevoContenidoDTO contenidoNuevo) {
        if (reporte.getCategorias().isEmpty() || contenidoNuevo.getIndexCategoria() == null){
            // primera categoria
            Categoria categoria = new Categoria(contenidoNuevo.getNuevoTituloCategoria());
            reporte.getCategorias().add(categoria);
        }
        else if(contenidoNuevo.getIndexCategoria() > reporte.getCategorias().size() - 1 ){
            // No tiene secciones, ya que es categoria nueva
            Categoria categoria = new Categoria(contenidoNuevo.getNuevoTituloCategoria());
            reporte.getCategorias().add(categoria);
        }
        else {
            Categoria categoria = reporte.getCategorias().get(contenidoNuevo.getIndexCategoria());
            categoria.setNombre(contenidoNuevo.getNuevoTituloCategoria());
            if (contenidoNuevo.getIndexSeccion() != null) {
                updateSeccion(categoria, contenidoNuevo);
            }
        }
    }

    private void updateSeccion(Categoria categoria, NuevoContenidoDTO contenidoNuevo) {
        if(categoria.getSecciones().isEmpty() || contenidoNuevo.getIndexSeccion() == null){
            // primera seccion
            Seccion seccion = new Seccion(contenidoNuevo.getNuevoTituloSeccion());
            categoria.getSecciones().add(seccion);
        }
        else if (contenidoNuevo.getIndexSeccion() > categoria.getSecciones().size() - 1) {
            // No tiene campos, ya que es seccion nueva
            Seccion seccion = new Seccion(contenidoNuevo.getNuevoTituloSeccion());
            categoria.getSecciones().add(seccion);
        }
        else {
            Seccion seccion = categoria.getSecciones().get(contenidoNuevo.getIndexSeccion());
            seccion.setTitulo(contenidoNuevo.getNuevoTituloSeccion());
            if (contenidoNuevo.getIndexCampo() != null) {
                updateCampo(seccion, contenidoNuevo);
            }
        }
    }

    private void updateCampo(Seccion seccion, NuevoContenidoDTO contenidoNuevo) {
        if(seccion.getCampos().isEmpty() || contenidoNuevo.getIndexCampo() == null){
            // primer campo
            Campo campo = new Campo(contenidoNuevo.getNuevoCampo());
            seccion.getCampos().add(campo);
        }
        else if (contenidoNuevo.getIndexCampo() > seccion.getCampos().size() - 1) {
            // No tiene campos, ya que es campo nuevo
            Campo campo = new Campo(contenidoNuevo.getNuevoCampo());
            seccion.getCampos().add(campo);
        }
        else {
            Campo campo = seccion.getCampos().get(contenidoNuevo.getIndexCampo());
            campo.actualizar(contenidoNuevo.getNuevoCampo());
        }
    }


    public boolean delete(String id) {
        try {
            reporteRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void crearPreset(InfoPresetDTO infoPresetDTO) {
        Reporte reporte = reporteRepository.findById(infoPresetDTO.getId()).get();
        Reporte preset = reporte.clonarYLimpiar();
        preset.setTitulo(infoPresetDTO.getNombre());
        preset.setFechaCreacion(new Date());
        preset.setFechaModificacion(new Date());
        preset.setEstado("Preset");
        reporteRepository.save(preset);
    }

    public List<EncabezadoReporteDTO> findAllPresets() {
        List<Reporte> presets = reporteRepository.findAllByEstado("Preset");
        List<EncabezadoReporteDTO> encabezados = new ArrayList<>();
        for (Reporte preset : presets) {
            EncabezadoReporteDTO encabezado = new EncabezadoReporteDTO(preset);
            encabezados.add(encabezado);
        }
        return encabezados;
    }
}