package sama.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sama.dto.CoordenadasReporteDTO;
import sama.dto.EncabezadoReporteDTO;
import sama.dto.InfoActualizacionDTO;
import sama.dto.InfoPresetDTO;
import sama.entity.Evidencia;
import sama.entity.Reporte;
import sama.entity.Usuario;
import sama.model.Campo;
import sama.model.Categoria;
import sama.model.Seccion;
import sama.repository.ReporteRepository;
import sama.repository.UsuarioRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final ReporteRepository reporteRepository;
    private final UsuarioRepository usuarioRepository;

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
            campo.setAutorizado(false);
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

    public Reporte autorizacionCampo(String id, CoordenadasReporteDTO coordenadas, String idUsuario) {
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte con id " + id + " no encontrado"));
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario con id " + idUsuario + " no encontrado"));

        alternarAutorizacionCampo(reporte, coordenadas, usuario);

        reporte.setEstado(isAuthorized(reporte) ? "Autorizado" : "Pendiente");
        return reporteRepository.save(reporte);
    }

    private void alternarAutorizacionCampo(Reporte reporte, CoordenadasReporteDTO coordenadas, Usuario usuario) {
        Integer indexCategoria = coordenadas.getIndexCategoria();
        Integer indexSeccion = coordenadas.getIndexSeccion();
        Integer indexCampo = coordenadas.getIndexCampo();

        if (indexCategoria != null && indexSeccion != null && indexCampo != null) {
            Campo campo = reporte.getCategorias().get(indexCategoria).getSecciones().get(indexSeccion).getCampos().get(indexCampo);
            if (campo.getAutorizado() == null) {
                campo.autorizar(usuario.getNombre(), usuario.getCorreo());
            } else {
                campo.alternarAutorizacion(usuario.getNombre(), usuario.getCorreo());
            }
        }
    }

    public Reporte autorizarTodosLosCampos(String idReporte, String idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).get();
        Reporte reporte = reporteRepository.findById(idReporte).get();
        for (Categoria categoria : reporte.getCategorias()) {
            for (Seccion seccion : categoria.getSecciones()) {
                for (Campo campo : seccion.getCampos()) {
                    if (campo.getAutorizado() == null || !campo.getAutorizado()) {
                        campo.autorizar(usuario.getNombre(), usuario.getCorreo());
                    }
                }
            }
        }
        reporte.setEstado("Autorizado");
        return reporteRepository.save(reporte);
    }

    public Reporte reescribirReporte(String id, Reporte nuevoReporte) {
        Optional<Reporte> reporteOptional = reporteRepository.findById(id);
        if (reporteOptional.isPresent()) {
            Reporte reporte = reporteOptional.get();
            reporte.setTitulo(Optional.ofNullable(nuevoReporte.getTitulo()).orElse(reporte.getTitulo()));
            reporte.setCategorias(Optional.ofNullable(nuevoReporte.getCategorias()).orElse(reporte.getCategorias()));
            reporte.setEvidencias(Optional.ofNullable(nuevoReporte.getEvidencias()).orElse(reporte.getEvidencias()));
            reporte.setEstado(Optional.ofNullable(nuevoReporte.getEstado()).orElse(reporte.getEstado()));
            reporte.setFechaCreacion(Optional.ofNullable(nuevoReporte.getFechaCreacion()).orElse(reporte.getFechaCreacion()));
            reporte.setFechaModificacion(Optional.ofNullable(nuevoReporte.getFechaModificacion()).orElse(reporte.getFechaModificacion()));
            reporte.setEmpresaId(Optional.ofNullable(nuevoReporte.getEmpresaId()).orElse(reporte.getEmpresaId()));
            return reporteRepository.save(reporte);
        } else {
            throw new RuntimeException("Reporte con id " + id + " no encontrado");
        }
    }

    private boolean isAuthorized(Reporte reporte) {
        for (Categoria categoria : reporte.getCategorias()) {
            for (Seccion seccion : categoria.getSecciones()) {
                for (Campo campo : seccion.getCampos()) {
                    if (campo.getAutorizado() == null || !campo.getAutorizado()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}