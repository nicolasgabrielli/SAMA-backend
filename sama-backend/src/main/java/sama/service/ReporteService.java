package sama.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sama.dto.EncabezadoReporteDTO;
import sama.dto.InfoActualizacionDTO;
import sama.dto.InfoPresetDTO;
import sama.entity.Reporte;
import sama.models.Campo;
import sama.models.Categoria;
import sama.models.Seccion;
import sama.repository.ReporteRepository;

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

    public Reporte update(String id, InfoActualizacionDTO contenidoNuevo) {
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));

        if (contenidoNuevo.getIndexCategoria() != null) {
            updateCategoria(reporte, contenidoNuevo);
        }

        return reporteRepository.save(reporte);
    }

    private void updateCategoria(Reporte reporte, InfoActualizacionDTO contenidoNuevo) {
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

    private void updateSeccion(Categoria categoria, InfoActualizacionDTO contenidoNuevo) {
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

    private void updateCampo(Seccion seccion, InfoActualizacionDTO contenidoNuevo) {
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

    public Reporte eliminarContenido(String id, InfoActualizacionDTO contenidoAEliminar) {
        Reporte reporte = reporteRepository.findById(id).get();
        if (contenidoAEliminar.getIndexCategoria() !=  null && contenidoAEliminar.getIndexSeccion() == null) {
            reporte.getCategorias().remove(contenidoAEliminar.getIndexCategoria().intValue());
        }
        if (contenidoAEliminar.getIndexCategoria() !=  null && contenidoAEliminar.getIndexSeccion() != null && contenidoAEliminar.getIndexCampo() == null) {
            reporte.getCategorias().get(contenidoAEliminar.getIndexCategoria()).getSecciones().remove(contenidoAEliminar.getIndexSeccion().intValue());
        }
        if (contenidoAEliminar.getIndexCategoria() !=  null && contenidoAEliminar.getIndexSeccion() != null && contenidoAEliminar.getIndexCampo() != null) {
            reporte.getCategorias().get(contenidoAEliminar.getIndexCategoria()).getSecciones().get(contenidoAEliminar.getIndexSeccion()).getCampos().remove(contenidoAEliminar.getIndexCampo().intValue());
        }
        return reporteRepository.save(reporte);
    }
}