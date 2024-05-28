package sama.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sama.dto.EncabezadoReporteDTO;
import sama.dto.InfoActualizacionDTO;
import sama.dto.InfoPresetDTO;
import sama.entity.Reporte;
import sama.model.Campo;
import sama.model.Categoria;
import sama.service.ReporteService;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {ReporteController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ReporteControllerTest {
    @Autowired
    private ReporteController reporteController;

    @MockBean
    private ReporteService reporteService;

    /**
     * Method under test: {@link ReporteController#deleteReport(String)}
     */
    @Test
    void testDeleteReport() throws Exception {
        // Arrange
        when(reporteService.delete(Mockito.any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/reporte/eliminar/{id}", "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(reporteController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Reporte eliminado"));
    }

    /**
     * Method under test: {@link ReporteController#deleteReport(String)}
     */
    @Test
    void testDeleteReport2() throws Exception {
        // Arrange
        when(reporteService.delete(Mockito.any())).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/reporte/eliminar/{id}", "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(reporteController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Error al eliminar reporte"));
    }

    /**
     * Method under test: {@link ReporteController#obtenerReporte(String)}
     */
    @Test
    void testObtenerReporte() throws Exception {
        // Arrange
        Reporte reporte = new Reporte();
        reporte.setAnio(1);
        reporte.setCategorias(new ArrayList<>());
        reporte.setEmpresaId("42");
        reporte.setEstado("Estado");
        reporte.setFechaCreacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte.setFechaModificacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte.setId("42");
        reporte.setTitulo("Titulo");
        when(reporteService.findById(Mockito.any())).thenReturn(reporte);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/reporte/por-id/{id}", "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(reporteController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":\"42\",\"anio\":1,\"categorias\":[],\"estado\":\"Estado\",\"empresaId\":\"42\"}"));
    }

    /**
     * Method under test: {@link ReporteController#obtenerReporte(String)}
     */
    @Test
    void testObtenerReporte2() throws Exception {
        // Arrange
        when(reporteService.findById(Mockito.any())).thenReturn(new Reporte());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/reporte/por-id/{id}", "");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(reporteController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test:
     * {@link ReporteController#obtenerReportesPorEmpresa(String)}
     */
    @Test
    void testObtenerReportesPorEmpresa() throws Exception {
        // Arrange
        when(reporteService.findAll(Mockito.any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/reporte/por-empresa/{empresaId}",
                "42");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(reporteController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test:
     * {@link ReporteController#obtenerReportesPorEmpresa(String)}
     */
    @Test
    void testObtenerReportesPorEmpresa2() throws Exception {
        // Arrange
        ArrayList<EncabezadoReporteDTO> encabezadoReporteDTOList = new ArrayList<>();
        ArrayList<Categoria> categorias = new ArrayList<>();
        Date fechaCreacion = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        encabezadoReporteDTOList.add(new EncabezadoReporteDTO(new Reporte(1, categorias, fechaCreacion,
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "Estado", "42")));
        when(reporteService.findAll(Mockito.any())).thenReturn(encabezadoReporteDTOList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/reporte/por-empresa/{empresaId}",
                "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(reporteController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":null,\"titulo\":null,\"anio\":1,\"estado\":\"Estado\",\"fechaCreacion\":\"1969-12-31\",\"fechaModificacion"
                                        + "\":\"1969-12-31\"}]"));
    }

    /**
     * Method under test: {@link ReporteController#crearReport(String, Reporte)}
     */
    @Test
    void testCrearReport() throws Exception {
        // Arrange
        Reporte reporte = new Reporte();
        reporte.setAnio(1);
        reporte.setCategorias(new ArrayList<>());
        reporte.setEmpresaId("42");
        reporte.setEstado("Estado");
        reporte.setFechaCreacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte.setFechaModificacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte.setId("42");
        reporte.setTitulo("Titulo");
        when(reporteService.save(Mockito.any(), Mockito.any())).thenReturn(reporte);

        Reporte reporte2 = new Reporte();
        reporte2.setAnio(1);
        reporte2.setCategorias(new ArrayList<>());
        reporte2.setEmpresaId("42");
        reporte2.setEstado("Estado");
        reporte2.setFechaCreacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte2
                .setFechaModificacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte2.setId("42");
        reporte2.setTitulo("Titulo");
        String content = (new ObjectMapper()).writeValueAsString(reporte2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/reporte/crear/{companyId}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(reporteController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Reporte creado"));
    }



    /**
     * Method under test:
     * {@link ReporteController#updateReport(String, InfoActualizacionDTO)}
     */
    @Test
    void testUpdateReport() throws Exception {
        // Arrange
        Reporte reporte = new Reporte();
        reporte.setAnio(1);
        reporte.setCategorias(new ArrayList<>());
        reporte.setEmpresaId("42");
        reporte.setEstado("Estado");
        reporte.setFechaCreacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte.setFechaModificacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte.setId("42");
        reporte.setTitulo("Titulo");
        when(reporteService.update(Mockito.any(), Mockito.any())).thenReturn(reporte);

        InfoActualizacionDTO infoActualizacionDTO = new InfoActualizacionDTO();
        infoActualizacionDTO.setIndexCampo(1);
        infoActualizacionDTO.setIndexCategoria(1);
        infoActualizacionDTO.setIndexSeccion(1);
        infoActualizacionDTO.setNuevoCampo(new Campo());
        infoActualizacionDTO.setNuevoTituloCategoria("Nuevo Titulo Categoria");
        infoActualizacionDTO.setNuevoTituloSeccion("Nuevo Titulo Seccion");
        String content = (new ObjectMapper()).writeValueAsString(infoActualizacionDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/reporte/actualizar/{id}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(reporteController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Reporte actualizado"));
    }

    /**
     * Method under test:
     * {@link ReporteController#updateReport(String, InfoActualizacionDTO)}
     */
    @Test
    void testUpdateReport2() throws Exception {
        // Arrange
        Reporte reporte = new Reporte();
        reporte.setAnio(1);
        reporte.setCategorias(new ArrayList<>());
        reporte.setEmpresaId("42");
        reporte.setEstado("Estado");
        reporte.setFechaCreacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte.setFechaModificacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte.setId("42");
        reporte.setTitulo("Titulo");
        when(reporteService.update(Mockito.any(), Mockito.any())).thenReturn(reporte);

        InfoActualizacionDTO infoActualizacionDTO = new InfoActualizacionDTO();
        infoActualizacionDTO.setIndexCampo(1);
        infoActualizacionDTO.setIndexCategoria(1);
        infoActualizacionDTO.setIndexSeccion(1);
        infoActualizacionDTO.setNuevoCampo(null);
        infoActualizacionDTO.setNuevoTituloCategoria("Nuevo Titulo Categoria");
        infoActualizacionDTO.setNuevoTituloSeccion("Nuevo Titulo Seccion");
        String content = (new ObjectMapper()).writeValueAsString(infoActualizacionDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/reporte/actualizar/{id}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(reporteController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Reporte actualizado"));
    }

    /**
     * Method under test: {@link ReporteController#crearPreset(InfoPresetDTO)}
     */
    @Test
    void testCrearPreset() throws Exception {
        // Arrange
        doNothing().when(reporteService).crearPreset(Mockito.any());

        InfoPresetDTO infoPresetDTO = new InfoPresetDTO();
        infoPresetDTO.setId("42");
        infoPresetDTO.setNombre("Nombre");
        String content = (new ObjectMapper()).writeValueAsString(infoPresetDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/reporte/preset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(reporteController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Preset creado"));
    }

    /**
     * Method under test: {@link ReporteController#obtenerPreset(String)}
     */
    @Test
    void testObtenerPreset() throws Exception {
        // Arrange
        Reporte reporte = new Reporte();
        reporte.setAnio(1);
        reporte.setCategorias(new ArrayList<>());
        reporte.setEmpresaId("42");
        reporte.setEstado("Estado");
        reporte.setFechaCreacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte.setFechaModificacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte.setId("42");
        reporte.setTitulo("Titulo");
        when(reporteService.findById(Mockito.any())).thenReturn(reporte);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/reporte/preset/{id}", "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(reporteController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":\"42\",\"anio\":1,\"categorias\":[],\"estado\":\"Estado\",\"empresaId\":\"42\"}"));
    }

    /**
     * Method under test: {@link ReporteController#obtenerPreset(String)}
     */
    @Test
    void testObtenerPreset2() throws Exception {
        // Arrange
        when(reporteService.findById(Mockito.any())).thenReturn(new Reporte());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/reporte/preset/{id}", "");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(reporteController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link ReporteController#obtenerPresets()}
     */
    @Test
    void testObtenerPresets() throws Exception {
        // Arrange
        when(reporteService.findAllPresets()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/reporte/preset");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(reporteController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link ReporteController#obtenerPresets()}
     */
    @Test
    void testObtenerPresets2() throws Exception {
        // Arrange
        ArrayList<EncabezadoReporteDTO> encabezadoReporteDTOList = new ArrayList<>();
        ArrayList<Categoria> categorias = new ArrayList<>();
        Date fechaCreacion = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        encabezadoReporteDTOList.add(new EncabezadoReporteDTO(new Reporte(1, categorias, fechaCreacion,
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "Estado", "42")));
        when(reporteService.findAllPresets()).thenReturn(encabezadoReporteDTOList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/reporte/preset");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(reporteController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":null,\"titulo\":null,\"anio\":1,\"estado\":\"Estado\",\"fechaCreacion\":\"1969-12-31\",\"fechaModificacion"
                                        + "\":\"1969-12-31\"}]"));
    }
}
