package sama.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sama.dto.CoordenadasReporteDTO;
import sama.dto.EncabezadoReporteDTO;
import sama.dto.InfoActualizacionDTO;
import sama.dto.InfoPresetDTO;
import sama.entity.Evidencia;
import sama.entity.Reporte;
import sama.model.Campo;
import sama.model.Categoria;
import sama.model.Seccion;
import sama.repository.ReporteRepository;

import java.util.*;

@Service
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;

    public List<EncabezadoReporteDTO> findAll(String empresaId) {
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
        Optional<Reporte> reporteOptional = reporteRepository.findById(id);
        if (reporteOptional.isPresent()) {
            return reporteOptional.get();
        } else {
            throw new RuntimeException("Reporte con id " + id + " no encontrado");
        }
    }

    public Reporte update(String id, InfoActualizacionDTO contenidoNuevo) {
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));

        if (contenidoNuevo.getCoordenadas().getIndexCategoria() != null) {
            updateCategoria(reporte, contenidoNuevo);
        }

        return reporteRepository.save(reporte);
    }

    private void updateCategoria(Reporte reporte, InfoActualizacionDTO contenidoNuevo) {
        if (reporte.getCategorias().isEmpty() || contenidoNuevo.getCoordenadas().getIndexCategoria() == null) {
            // primera categoria
            Categoria categoria = new Categoria(contenidoNuevo.getNuevoTituloCategoria());
            reporte.getCategorias().add(categoria);
        } else if (contenidoNuevo.getCoordenadas().getIndexCategoria() > reporte.getCategorias().size() - 1) {
            // No tiene secciones, ya que es categoria nueva
            Categoria categoria = new Categoria(contenidoNuevo.getNuevoTituloCategoria());
            reporte.getCategorias().add(categoria);
        } else {
            Categoria categoria = reporte.getCategorias().get(contenidoNuevo.getCoordenadas().getIndexCategoria());
            categoria.setTitulo(contenidoNuevo.getNuevoTituloCategoria());
            if (contenidoNuevo.getCoordenadas().getIndexSeccion() != null) {
                updateSeccion(categoria, contenidoNuevo);
            }
        }
    }

    private void updateSeccion(Categoria categoria, InfoActualizacionDTO contenidoNuevo) {
        if (categoria.getSecciones().isEmpty() || contenidoNuevo.getCoordenadas().getIndexSeccion() == null) {
            // primera seccion
            Seccion seccion = new Seccion(contenidoNuevo.getNuevoTituloSeccion());
            categoria.getSecciones().add(seccion);
        } else if (contenidoNuevo.getCoordenadas().getIndexSeccion() > categoria.getSecciones().size() - 1) {
            // No tiene campos, ya que es seccion nueva
            Seccion seccion = new Seccion(contenidoNuevo.getNuevoTituloSeccion());
            categoria.getSecciones().add(seccion);
        } else {
            Seccion seccion = categoria.getSecciones().get(contenidoNuevo.getCoordenadas().getIndexSeccion());
            seccion.setTitulo(contenidoNuevo.getNuevoTituloSeccion());
            if (contenidoNuevo.getCoordenadas().getIndexCampo() != null) {
                updateCampo(seccion, contenidoNuevo);
            }
        }
    }

    private void updateCampo(Seccion seccion, InfoActualizacionDTO contenidoNuevo) {
        if (seccion.getCampos().isEmpty() || contenidoNuevo.getCoordenadas().getIndexCampo() == null) {
            // primer campo
            Campo campo = new Campo(contenidoNuevo.getNuevoCampo());
            campo.setAutorizacion(false);
            seccion.getCampos().add(campo);
        } else if (contenidoNuevo.getCoordenadas().getIndexCampo() > seccion.getCampos().size() - 1) {
            // No tiene campos, ya que es campo nuevo
            Campo campo = new Campo(contenidoNuevo.getNuevoCampo());
            seccion.getCampos().add(campo);
        } else {
            Campo campo = seccion.getCampos().get(contenidoNuevo.getCoordenadas().getIndexCampo());
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
        Optional<Reporte> reporteOptional = reporteRepository.findById(infoPresetDTO.getId());
        if (reporteOptional.isPresent()) {
            Reporte reporte = reporteOptional.get();
            Reporte preset = reporte.clonarYLimpiar();
            preset.setTitulo(infoPresetDTO.getNombre());
            preset.setFechaCreacion(new Date());
            preset.setFechaModificacion(new Date());
            preset.setEstado("Preset");
            reporteRepository.save(preset);
        } else {
            throw new RuntimeException("Reporte con id " + infoPresetDTO.getId() + " no encontrado");
        }
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


    public Reporte eliminarContenido(String id, CoordenadasReporteDTO coordenadas) {
        Optional<Reporte> reporteOptional = reporteRepository.findById(id);
        if (reporteOptional.isPresent()) {
            Reporte reporte = reporteOptional.get();
            if (coordenadas.getIndexCategoria() != null && coordenadas.getIndexSeccion() == null) {
                reporte.getCategorias().remove(coordenadas.getIndexCategoria().intValue());
            }
            if (coordenadas.getIndexCategoria() != null && coordenadas.getIndexSeccion() != null && coordenadas.getIndexCampo() == null) {
                reporte.getCategorias().get(coordenadas.getIndexCategoria()).getSecciones().remove(coordenadas.getIndexSeccion().intValue());
            }
            if (coordenadas.getIndexCategoria() != null && coordenadas.getIndexSeccion() != null && coordenadas.getIndexCampo() != null) {
                reporte.getCategorias().get(coordenadas.getIndexCategoria()).getSecciones().get(coordenadas.getIndexSeccion()).getCampos().remove(coordenadas.getIndexCampo().intValue());
            }
            return reporteRepository.save(reporte);
        } else {
            throw new RuntimeException("Reporte with id " + id + " not found");
        }
    }

    public void asociarEvidencia(String idReporte, Evidencia evidencia) {
        Optional<Reporte> reporteOptional = reporteRepository.findById(idReporte);
        if (reporteOptional.isPresent()) {
            Reporte reporte = reporteOptional.get();
            if (reporte.getEvidencias() == null) {
                reporte.setEvidencias(new ArrayList<>());
                reporte.getEvidencias().add(evidencia);
                reporteRepository.save(reporte);
                return;
            }
            reporte.getEvidencias().add(evidencia);
            reporteRepository.save(reporte);
        } else {
            throw new RuntimeException("Reporte con id " + idReporte + " no encontrado");
        }
    }

    public Reporte alternarAutorizacionCampo(String id, CoordenadasReporteDTO coordenadas) {
        Optional<Reporte> reporteOptional = reporteRepository.findById(id);
        reporteOptional.ifPresentOrElse(
                reporte -> autorizarCampo(reporte, coordenadas),
                () -> {
                    throw new RuntimeException("Reporte con id " + id + " no encontrado");
                }
        );
        return reporteOptional.orElse(null);
    }

    private void autorizarCampo(Reporte reporte, CoordenadasReporteDTO coordenadas) {
        if (coordenadas.getIndexCategoria() != null && coordenadas.getIndexSeccion() != null && coordenadas.getIndexCampo() != null) {
            Campo campo = reporte.getCategorias().get(coordenadas.getIndexCategoria()).getSecciones().get(coordenadas.getIndexSeccion()).getCampos().get(coordenadas.getIndexCampo());
            if (campo.getAutorizacion() == null) {
                // Campo no tiene inicializado el campo autorizacion, por lo que se deja como true, ya que primero se autoriza antes de desautorizar
                campo.setAutorizacion(true);
            } else {
                // Si el campo ya tiene inicializado ese campo, se alterna el valor
                campo.alternarAutorizacion();
            }
            reporteRepository.save(reporte);
        }
    }
}