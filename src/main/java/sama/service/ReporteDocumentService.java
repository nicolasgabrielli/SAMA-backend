package sama.service;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
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
            XWPFRun  tituloRun = campoParagraph.createRun();
            tituloRun.setText(campo.getTitulo());
            tituloRun.setBold(true);
            tituloRun.setFontSize(13);
        }
        if (campo.getContenido() != null) {
            XWPFRun contenidoRun = campoParagraph.createRun();
            contenidoRun.addBreak();
            if (!campo.getTipo().equals("tabla")) {
                System.out.println("Entre aqui");
                contenidoRun.setText(campo.getContenido().toString());
                contenidoRun.setFontSize(11);
            }else {
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
            XWPFRun  tituloRun = campoParagraph.createRun();
            tituloRun.setText(campo.getTitulo());
            tituloRun.setBold(true);
            tituloRun.setFontSize(12);
        }
        if (campo.getContenido() != null) {
            XWPFRun contenidoRun = campoParagraph.createRun();
            contenidoRun.addBreak();
            if(!campo.getTipo().equals("tabla")){
                contenidoRun.setText(campo.getContenido().toString());
                contenidoRun.setFontSize(11);
            }
            else {
                // TODO: Implementar tabla
            }
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