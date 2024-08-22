package sama.service;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import sama.entity.Reporte;
import sama.model.Campo;
import sama.model.Categoria;
import sama.model.Seccion;
import sama.repository.ReporteRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteDocumentService {

    private final ReporteRepository reporteRepository;

    public byte[] generarWord(String idReporte) throws IOException {
        Reporte reporte = reporteRepository.findById(idReporte)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
        XWPFDocument document = new XWPFDocument();

        agregarTitulo(document, reporte.getTitulo());

        List<Categoria> categorias = reporte.getCategorias();
        for (Categoria categoria : categorias) {
            agregarCategoria(document, categoria);
        }

        //addSummaryTable(document, reporte);

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

    private void agregarCategoria(XWPFDocument document, Categoria categoria) throws IOException {
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

    private void agregarSeccion(XWPFDocument document, Seccion section) throws IOException {
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

    private void agregarCampo(XWPFDocument document, Campo campo) throws IOException {
        XWPFParagraph campoParagraph = document.createParagraph();
        agregarTituloYContenido(document, campoParagraph, campo, true);
        if (campo.getSubCampos() != null) {
            for (Campo subCampo : campo.getSubCampos()) {
                agregarSubCampo(document, subCampo);
            }
        }
    }

    private void agregarSubCampo(XWPFDocument document, Campo campo) throws IOException {
        XWPFParagraph campoParagraph = document.createParagraph();
        agregarTituloYContenido(document, campoParagraph, campo, false);
    }

    private void agregarTituloYContenido(XWPFDocument document, XWPFParagraph paragraph, Campo campo, boolean isMainField) throws IOException {

        if(!isMainField){
            paragraph.setIndentationLeft(720); // 0.5 pulgadas; ajustar según sea necesario
        }

        if (campo.getTitulo() != null) {
            XWPFRun tituloRun = paragraph.createRun();
            tituloRun.setText(campo.getTitulo());
            tituloRun.setBold(true);
            tituloRun.setFontSize(isMainField ? 13 : 12); // Tamaño de fuente para el título de campos principales y secundarios
        }
        if (campo.getContenido() != null) {
            XWPFRun contenidoRun = paragraph.createRun();
            contenidoRun.addBreak();
            if (!campo.getTipo().equalsIgnoreCase("tabla")) {
                if (campo.getTipo().equalsIgnoreCase("booleano")){
                    contenidoRun.setText(campo.getContenido().toString().equalsIgnoreCase("true") ? "Sí" : "No");
                } else {
                    contenidoRun.setText(campo.getContenido().toString());
                }
                contenidoRun.setFontSize(11); // Tamaño de fuente para el contenido de campos principales y secundarios
            } else {
                agregarTabla(document, campo.getContenido()); // Agregar tabla si el campo es de tipo tabla
            }
        }
    }

    private void agregarTabla(XWPFDocument document, Object contenido) throws IOException {
        List<String[]> csvData = leerCSV(contenido.toString());
        if (!csvData.isEmpty()) {
            // Se crea una tabla con el número de filas y columnas necesarias
            XWPFTable table = document.createTable(csvData.size(), csvData.get(0).length);
            table.setRowBandSize(15);
            table.setWidth("auto");
            table.setCellMargins(200, 100, 200, 100);

            for (int i = 0; i < csvData.size(); i++) {
                XWPFTableRow tableRow = table.getRow(i);
                String[] row = csvData.get(i);
                for (int j = 0; j < row.length; j++) {
                    // Verificación de celdas nulas y creación de celdas si es necesario
                    XWPFTableCell cell = tableRow.getCell(j);
                    if (cell == null) {  // Verifica si la celda es nula
                        cell = tableRow.createCell();  // Crea la celda si es nula
                    }
                    XWPFParagraph paragraph = cell.getParagraphs().get(0);
                    paragraph.setAlignment(ParagraphAlignment.CENTER);
                    XWPFRun run = paragraph.createRun();
                    run.setText(row[j]);
                    run.setFontFamily("Arial");
                    run.setFontSize(11);
                }
            }
        }
    }

    public List<String[]> leerCSV(String csvContent) throws IOException {
        List<String[]> csvData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new StringReader(csvContent));
             CSVParser parser = new CSVParser(br, CSVFormat.DEFAULT.builder()
                     .setQuote('"')
                     .setIgnoreSurroundingSpaces(true)
                     .build())) {

            for (CSVRecord record : parser) {
                int size = record.size();
                String[] values = new String[size];
                for (int i = 0; i < size; i++) {
                    values[i] = record.get(i);
                }
                csvData.add(values);
            }
        }
        return csvData;
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