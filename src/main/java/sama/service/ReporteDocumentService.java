package sama.service;

import org.apache.poi.xwpf.usermodel.*;
import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sama.entity.Reporte;
import sama.model.Campo;
import sama.model.Categoria;
import sama.model.Seccion;
import sama.repository.ReporteRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ReporteDocumentService {

    @Autowired
    private ReporteRepository reporteRepository;

    public byte[] generarWord(String idReporte) throws IOException {
        Reporte reporte = reporteRepository.findById(idReporte)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
        XWPFDocument document = new XWPFDocument();

        agregarTitulo(document, reporte.getTitulo());

        List<Categoria> categorias = reporte.getCategorias();
        for (Categoria categoria : categorias) {
            agregarCategoria(document, categoria);
        }

        addSummaryTable(document, reporte);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.write(outputStream);
        return outputStream.toByteArray();
    }

    private void agregarTitulo(XWPFDocument document, String titleText) {
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = title.createRun();
        run.setText(titleText);
        run.setBold(true);
        run.setFontSize(18);
    }

    private void agregarCategoria(XWPFDocument document, Categoria categoria) {
        XWPFParagraph categoryTitle = document.createParagraph();
        categoryTitle.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun categoryRun = categoryTitle.createRun();
        categoryRun.setText(categoria.getTitulo());
        categoryRun.setBold(true);
        categoryRun.setFontSize(16);

        List<Seccion> secciones = categoria.getSecciones();
        for (Seccion seccion : secciones) {
            agregarSeccion(document, seccion);
        }
    }

    private void agregarSeccion(XWPFDocument document, Seccion section) {
        XWPFParagraph sectionTitle = document.createParagraph();
        sectionTitle.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun sectionRun = sectionTitle.createRun();
        sectionRun.setText(section.getTitulo());
        sectionRun.setBold(true);
        sectionRun.setFontSize(14);

        List<Campo> campos = section.getCampos();
        for (Campo campo : campos) {
            agregarCampo(document, campo);
        }
    }

    private void agregarCampo(XWPFDocument document, Campo campo) {
        XWPFParagraph campoParagraph = document.createParagraph();
        if (campo.getTitulo() != null) {
            XWPFRun tituloRun = campoParagraph.createRun();
            tituloRun.setText(campo.getTitulo());
            tituloRun.setBold(true);
            tituloRun.setFontSize(13);
        }
        if (campo.getContenido() != null) {
            XWPFRun contenidoRun = campoParagraph.createRun();
            contenidoRun.addBreak();
            if (!campo.getTipo().equals("tabla")) {
                contenidoRun.setText(campo.getContenido().toString());
                contenidoRun.setFontSize(11);
            } else {
                // TODO: Implementar tabla
            }
        }
        if (campo.getSubCampos() != null) {
            for (Campo subCampo : campo.getSubCampos()) {
                agregarSubCampo(document, subCampo);
            }
        }
    }

    private void agregarSubCampo(XWPFDocument document, Campo campo) {
        XWPFParagraph campoParagraph = document.createParagraph();
        if (campo.getTitulo() != null) {
            XWPFRun tituloRun = campoParagraph.createRun();
            tituloRun.setText(campo.getTitulo());
            tituloRun.setBold(true);
            tituloRun.setFontSize(12);
        }
        if (campo.getContenido() != null) {
            XWPFRun contenidoRun = campoParagraph.createRun();
            contenidoRun.addBreak();
            if (!campo.getTipo().equals("tabla")) {
                contenidoRun.setText(campo.getContenido().toString());
                contenidoRun.setFontSize(11);
            } else {
                // TODO: Implementar tabla
            }
        }
    }

    private void addSummaryTable(XWPFDocument document, Reporte reporte) {
        XWPFParagraph summaryTitle = document.createParagraph();
        summaryTitle.setStyle("Heading1");
        summaryTitle.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun titleRun = summaryTitle.createRun();
        titleRun.setText("Tabla de indicadores");
        titleRun.setBold(true);
        titleRun.setFontSize(14);

        // Crear la tabla
        XWPFTable summaryTable = document.createTable();

        // Establecer alineación centrada para todas las celdas
        summaryTable.setRowBandSize(15);
        summaryTable.setWidth("auto"); // Ajustar al tamaño del contenido
        summaryTable.setCellMargins(200, 100, 200, 100);

        // Crear fila de encabezado
        XWPFTableRow headerRow = summaryTable.getRow(0);

        // Crear las celdas necesarias
        for (int i = 0; i < 3; i++) {
            headerRow.addNewTableCell();
        }

        // Establecer el texto y el estilo de los encabezados
        for (int i = 0; i < 4; i++) {
            XWPFTableCell cell = headerRow.getCell(i);
            XWPFParagraph paragraph = cell.getParagraphs().get(0);
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.createRun();
            run.setBold(true); // Establecer negrita
            switch (i) {
                case 0:
                    run.setText("Descripción");
                    break;
                case 1:
                    run.setText("Código");
                    break;
                case 2:
                    run.setText("Capitulo");
                    break;
                case 3:
                    run.setText("Página");
                    break;
            }
        }

        int indexCategoria = 1;
        for (Categoria category : reporte.getCategorias()) {
            for (Seccion section : category.getSecciones()) {
                XWPFTableRow row = summaryTable.createRow();
                XWPFTableCell cell1 = row.getCell(0);
                XWPFTableCell cell2 = row.getCell(1);
                XWPFTableCell cell3 = row.getCell(2);

                XWPFParagraph p1 = cell1.getParagraphs().get(0);
                XWPFParagraph p2 = cell2.getParagraphs().get(0);
                XWPFParagraph p3 = cell3.getParagraphs().get(0);

                p1.setAlignment(ParagraphAlignment.LEFT);
                p2.setAlignment(ParagraphAlignment.CENTER);
                p3.setAlignment(ParagraphAlignment.CENTER);

                XWPFRun r1 = p1.createRun();
                XWPFRun r2 = p2.createRun();
                XWPFRun r3 = p3.createRun();

                r1.setText(section.getTitulo());
                r2.setText("-");
                r3.setText(Integer.toString(indexCategoria));
            }
            indexCategoria++;
        }
    }

    public byte[] generarPdf(String idReporte) throws Exception {
        // Generamos el documento en Word
        byte[] wordBytes = generarWord(idReporte);

        // Convertimos el documento Word a PDF
        ByteArrayInputStream wordInputStream = new ByteArrayInputStream(wordBytes);
        Document document = new Document(wordInputStream);

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        document.save(pdfOutputStream, SaveFormat.PDF);
        return pdfOutputStream.toByteArray();
    }
}