package sama.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Service
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;

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
            reporte.getEvidencias().add(evidencia);
            reporteRepository.save(reporte);
        } else {
            throw new RuntimeException("Reporte con id " + idReporte + " no encontrado");
        }
    }

    // Intento generar PDF

    public byte[] generarPDF(String idReporte) throws IOException {
        Optional<Reporte> reporteOptional = reporteRepository.findById(idReporte);
        if (reporteOptional.isPresent()) {
            Reporte reporte = reporteOptional.get();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (Document document = new Document()) {
                PdfWriter.getInstance(document, baos);
                document.open();
                // Agregar el contenido del reporte al documento
                agregarReporteContent(document, reporte);
                //Agregar cuadro resumen
                agregarCuadroResumen(document, reporte);

                document.close();

                // Ahora que el documento está cerrado, agregar los números de página
                addPageNumbers(baos);

            } catch (DocumentException e) {
                throw new IOException(e);
            }
            return baos.toByteArray();
        } else {
            throw new RuntimeException("Reporte with id " + idReporte + " not found");
        }
    }

    private void agregarCuadroResumen(Document document, Reporte reporte) {
        //Pendiente
    }

    private void agregarReporteContent(Document document, Reporte reporte) throws DocumentException {
        Font reporteFont = new Font(Font.HELVETICA, 24, Font.BOLD);
        Paragraph tituloReporte = new Paragraph(reporte.getTitulo(), reporteFont);
        tituloReporte.setAlignment(Element.ALIGN_CENTER);
        document.add(tituloReporte);

        for (Categoria categoria : reporte.getCategorias()) {
            agregarCategoriaContent(document, categoria);
        }
    }

    private void agregarCategoriaContent(Document document, Categoria categoria) throws DocumentException {
        Font categoriaFont = new Font(Font.HELVETICA, 16, Font.BOLD);
        Paragraph tituloCategoria = new Paragraph(categoria.getTitulo(), categoriaFont);
        tituloCategoria.setSpacingAfter(5f);
        document.add(tituloCategoria);

        for (Seccion seccion : categoria.getSecciones()) {
            agregarSeccionContent(document, seccion);
        }
    }

    private void agregarSeccionContent(Document document, Seccion seccion) throws DocumentException {
        Font seccionFont = new Font(Font.HELVETICA, 14, Font.BOLD);
        Paragraph tituloSeccion = new Paragraph(seccion.getTitulo(), seccionFont);
        tituloSeccion.setSpacingAfter(5f);
        tituloSeccion.setSpacingBefore(5f);
        document.add(tituloSeccion);

        for (Campo campo : seccion.getCampos()) {
            agregarCampoContent(document, campo);
        }
    }

    private void agregarCampoContent(Document document, Campo campo) throws DocumentException {
        Font tiuloCampoFont = new Font(Font.HELVETICA, 13, Font.BOLD);
        Font contenidoCampoFont = new Font(Font.HELVETICA, 11, Font.NORMAL);
        if (!campo.getTipo().equals("tabla")) {
            Paragraph tituloCampo = new Paragraph(campo.getTitulo(), tiuloCampoFont);
            tituloCampo.setSpacingAfter(5f);
            tituloCampo.setIndentationLeft(10f);
            document.add(tituloCampo);
            if (campo.getContenido() != null) {
                Paragraph contenidoCampo = new Paragraph(String.valueOf(campo.getContenido()), contenidoCampoFont);
                contenidoCampo.setIndentationLeft(10f);
                document.add(contenidoCampo);
            }
            if (campo.getSubCampos() != null) {
                for (Campo subcampo : campo.getSubCampos()) {
                    agregarSubCampoContent(document, subcampo);
                }
            }
        }
        // Pendiente: agregar tabla
    }

    private void agregarSubCampoContent(Document document, Campo campo) throws DocumentException {
        Font tituloSubCampoFont = new Font(Font.HELVETICA, 12, Font.BOLD);
        Paragraph subcampoTitulo = new Paragraph(campo.getTitulo(), tituloSubCampoFont);
        subcampoTitulo.setIndentationLeft(15f);
        subcampoTitulo.setSpacingAfter(2f);
        document.add(subcampoTitulo);

        if (campo.getContenido() != null) {
            Font contenidoSubcampoFont = new Font(Font.HELVETICA, 11, Font.NORMAL);
            Paragraph subcampoContenido = new Paragraph(String.valueOf(campo.getContenido()), contenidoSubcampoFont);
            subcampoContenido.setIndentationLeft(15f);
            document.add(subcampoContenido);
        }
    }

    private void addPageNumbers(ByteArrayOutputStream baos) throws IOException, DocumentException {
        ByteArrayOutputStream baosWithPageNumbers = new ByteArrayOutputStream();
        PdfReader reader = new PdfReader(baos.toByteArray());
        PdfStamper stamper = new PdfStamper(reader, baosWithPageNumbers);
        int totalPages = reader.getNumberOfPages();

        for (int i = 1; i <= totalPages; i++) {
            PdfContentByte cb = stamper.getOverContent(i);
            Font font = new Font(Font.HELVETICA, 10);
            Phrase pageNumber = new Phrase(String.format("Página %d de %d", i, totalPages), font);
            // Ajustar posición para que esté más cerca de la esquina inferior derecha
            float x = reader.getPageSize(i).getWidth() - 10; // Ajustar este valor según sea necesario
            float y = 10;

            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, pageNumber, x, y, 0);
        }

        stamper.close();
        reader.close();

        // Reemplazar el contenido original con el contenido con números de página
        baos.reset();
        baosWithPageNumbers.writeTo(baos);
    }
}