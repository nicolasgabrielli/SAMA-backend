package sama.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
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

    public Reporte eliminarContenido(String id, CoordenadasReporteDTO coordenadas) {
        Reporte reporte = reporteRepository.findById(id).get();
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
    }

    public void asociarEvidencia(String idReporte, Evidencia evidencia) {
        Reporte reporte = reporteRepository.findById(idReporte).get();
        reporte.getEvidencias().add(evidencia);
        reporteRepository.save(reporte);
    }

    // Método para generar un PDF a partir de un reporte
    // En proceso

//    public byte[] generarPDF(Reporte reporte) throws IOException {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
//        PdfDocument pdfDocument = new PdfDocument(writer);
//        Document document = new Document(pdfDocument);
//
//        // Añadir el título del reporte con estilos
//        agregarTitulo(document, reporte.getTitulo());
//
//        // Añadir las categorías
//        for (Categoria categoria : reporte.getCategorias()) {
//            agregarCategoria(document, categoria);
//        }
//
//        document.close();
//        return byteArrayOutputStream.toByteArray();
//    }
//
//    private void agregarTitulo(Document document, String titulo) {
//        Paragraph title = new Paragraph("Reporte: " + titulo)
//                .setFontSize(20)
//                .setBold()
//                .setTextAlignment(TextAlignment.CENTER)
//                .setMarginBottom(20);
//        document.add(title);
//    }
//
//    private void agregarCategoria(Document document, Categoria categoria) {
//        Paragraph categoriaParagraph = new Paragraph("Categoría: " + categoria.getTitulo())
//                .setFontSize(16)
//                .setBold()
//                .setMarginTop(10)
//                .setMarginBottom(10);
//        document.add(categoriaParagraph);
//
//        // Añadir las secciones de la categoría
//        for (Seccion seccion : categoria.getSecciones()) {
//            agregarSeccion(document, seccion);
//        }
//    }
//
//    private void agregarSeccion(Document document, Seccion seccion) {
//        Paragraph seccionParagraph = new Paragraph("Sección: " + seccion.getTitulo())
//                .setFontSize(14)
//                .setItalic()
//                .setMarginLeft(10)
//                .setMarginBottom(5);
//        document.add(seccionParagraph);
//
//        // Añadir los campos de la sección
//        for (Campo campo : seccion.getCampos()) {
//            agregarCampo(document, campo);
//        }
//    }
//
//    private void agregarCampo(Document document, Campo campo) {
//        Paragraph campoParagraph = new Paragraph("Campo: " + campo.getTitulo())
//                .setFontSize(12)
//                .setBold()
//                .setMarginLeft(20)
//                .setMarginBottom(2);
//        document.add(campoParagraph);
//
//        if ("tabla".equals(campo.getTipo())) {
//            agregarTabla(document, campo.getContenido().toString());
//        } else {
//            document.add(new Paragraph(campo.getContenido().toString())
//                    .setFontSize(12)
//                    .setMarginLeft(25)
//                    .setMarginBottom(5));
//        }
//
//        // Añadir los subcampos del campo (si existen)
//        if (campo.getSubCampos() != null && !campo.getSubCampos().isEmpty()) {
//            for (Campo subcampo : campo.getSubCampos()) {
//                agregarSubcampo(document, subcampo);
//            }
//        }
//    }
//
//    private void agregarSubcampo(Document document, Campo subcampo) {
//        Paragraph subcampoParagraph = new Paragraph("Subcampo: " + subcampo.getTitulo())
//                .setFontSize(10)
//                .setBold()
//                .setMarginLeft(30)
//                .setMarginBottom(2);
//        document.add(subcampoParagraph);
//
//        Paragraph subcampoContenido = new Paragraph(subcampo.getContenido().toString())
//                .setFontSize(10)
//                .setMarginLeft(35)
//                .setMarginBottom(5);
//        document.add(subcampoContenido);
//    }
//
//    private void agregarTabla(Document document, String contenido) {
//        // Depende del formato del contenido; revisar
//        // Dividir el contenido en filas
//        String[] rows = contenido.split("\n");
//        if (rows.length > 0) {
//            // Determinar el número de columnas basándose en la primera fila
//            String[] firstRow = rows[0].split(" ");
//            int numColumns = firstRow.length;
//
//            // Crear la tabla con el número adecuado de columnas
//            Table table = new Table(UnitValue.createPercentArray(numColumns)).useAllAvailableWidth();
//
//            for (String row : rows) {
//                String[] cells = row.split(" ");
//                for (String cell : cells) {
//                    table.addCell(new Cell().add(new Paragraph(cell).setFontSize(12)));
//                }
//            }
//
//            // Añadir la tabla al documento
//            document.add(table);
//        }
//    }
}