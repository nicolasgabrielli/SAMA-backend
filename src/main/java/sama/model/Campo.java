package sama.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import sama.entity.Evidencia;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Campo {
    // Información del campo
    private String titulo;
    private String tipo;
    private Object contenido;
    boolean porcentaje;
    private List<Campo> subCampos;
    private List<Evidencia> evidencias;

    // Autorización
    private Boolean autorizado;
    private String nombreAutorizador;
    private String correoAutorizador;
    private Date fechaAutorizacion;

    /**
     * Constructor por defecto de {@code Campo}.
     * <p>
     * Inicializa una nueva instancia de {@code Campo} con una lista vacía de {@code subCampos}.
     * Esta implementación asegura que cada objeto {@code Campo} tenga su propia lista de subcampos
     * desde el momento de su creación, evitando así la necesidad de verificar si {@code subCampos}
     * es {@code null} en otras partes del código.
     * </p>
     */
    public Campo() {
        this.subCampos = new ArrayList<>();
    }

    /**
     * Constructor que crea una nueva instancia de {@code Campo} basada en otro objeto {@code Campo} existente.
     * <p>
     * Este constructor copia los valores de los atributos del objeto {@code Campo} proporcionado al nuevo objeto.
     * Esto incluye el título, tipo, contenido, subcampos y evidencias del campo. Es útil para crear una copia
     * de un objeto {@code Campo} existente con los mismos valores de atributo.
     * </p>
     *
     * @param nuevoCampo El objeto {@code Campo} del cual se copiarán los valores.
     */
    public Campo(Campo nuevoCampo) {
        this.titulo = nuevoCampo.getTitulo();
        this.tipo = nuevoCampo.getTipo();
        this.contenido = nuevoCampo.getContenido();
        this.subCampos = nuevoCampo.getSubCampos();
        this.evidencias = nuevoCampo.getEvidencias();
        this.porcentaje = nuevoCampo.isPorcentaje();
    }

    /**
     * Actualiza los atributos de este objeto {@code Campo} con los valores de otro objeto {@code Campo}.
     * <p>
     * Este método toma un objeto {@code Campo} como argumento y actualiza los atributos de este objeto
     * con los valores correspondientes del objeto argumento. Los atributos actualizados incluyen el título,
     * tipo, contenido, subcampos y evidencias. Esto permite modificar fácilmente los atributos de un
     * objeto {@code Campo} existente sin necesidad de establecer cada campo manualmente.
     * </p>
     *
     * @param nuevoCampo El objeto {@code Campo} del cual se copiarán los valores.
     */
    public void actualizar(Campo nuevoCampo) {
        this.titulo = nuevoCampo.getTitulo();
        this.tipo = nuevoCampo.getTipo();
        this.contenido = nuevoCampo.getContenido();
        this.subCampos = nuevoCampo.getSubCampos();
        this.evidencias = nuevoCampo.getEvidencias();
        this.porcentaje = nuevoCampo.isPorcentaje();
    }

    /**
     * Crea y devuelve una copia superficial de este objeto {@code Campo}, con el contenido limpiado.
     * <p>
     * Este método realiza una copia superficial del objeto {@code Campo} actual. Todos los campos primitivos
     * y referencias son copiados directamente, excepto el campo {@code contenido}, que se establece como una
     * cadena vacía (""). Para los {@code subCampos}, se realiza una copia superficial recursiva, asegurando
     * que cada subcampo también tenga su contenido limpiado.
     * </p>
     *
     * @return Una nueva instancia de {@code Campo} que es una copia superficial del objeto actual,
     * con el campo {@code contenido} establecido como una cadena vacía y los {@code subCampos}
     * también clonados y limpiados recursivamente.
     */

    public Campo clonarYLimpiar() {
        Campo clone = new Campo();
        clone.setTitulo(this.titulo);
        clone.setTipo(this.tipo);

        if ("tabla".equalsIgnoreCase(this.tipo) && this.contenido instanceof String) {
            try (StringReader reader = new StringReader((String) this.contenido);
                 StringWriter writer = new StringWriter();
                 CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

                // Leer el contenido CSV
                List<CSVRecord> records = CSVFormat.DEFAULT.parse(reader).getRecords();
                if (!records.isEmpty()) {
                    // Escribir solo la primera fila (encabezados)
                    csvPrinter.printRecord(records.get(0));
                }

                clone.setContenido(writer.toString());
            } catch (IOException e) {
                e.printStackTrace();
                clone.setContenido("");
            }
        } else {
            clone.setContenido("");
        }

        if (this.subCampos != null) {
            List<Campo> subCamposClone = new ArrayList<>();
            for (Campo subCampo : this.subCampos) {
                subCamposClone.add(subCampo.clonarYLimpiar());
            }
            clone.setSubCampos(subCamposClone);
        }
        return clone;
    }

    /**
     * Cambia el estado de autorización de este campo.
     * <p>
     * Este método invierte el valor actual de la propiedad {@code autorizacion}.
     * Si la autorización estaba previamente habilitada (true), la deshabilita (false),
     * y viceversa. Es útil para alternar rápidamente el estado de autorización de un campo
     * sin necesidad de conocer su estado actual.
     * </p>
     */
    public void alternarAutorizacion(String nombreAutorizador, String correoAutorizador) {
        if (this.autorizado == null || !this.autorizado) {
            this.autorizar(nombreAutorizador, correoAutorizador);
        } else {
            this.desautorizar();
        }
    }

    public void autorizar(String nombreAutorizador, String correoAutorizador) {
        this.autorizado = true;
        this.nombreAutorizador = nombreAutorizador;
        this.correoAutorizador = correoAutorizador;
        this.fechaAutorizacion = new Date();
    }

    public void desautorizar() {
        this.autorizado = false;
        this.nombreAutorizador = null;
        this.correoAutorizador = null;
        this.fechaAutorizacion = null;
    }

    public void eliminarEvidencia(String idEvidencia) {
        if (this.evidencias != null) {
            this.evidencias.removeIf(evidencia -> idEvidencia.equals(evidencia.getId()));
        }
    }
}