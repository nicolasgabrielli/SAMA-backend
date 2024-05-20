package sama.Controllers;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sama.DTO.ListadoEmpresaDTO;
import sama.Entities.Empresa;
import sama.Services.EmpresaService;

@ContextConfiguration(classes = {EmpresaController.class})
@ExtendWith(SpringExtension.class)
class EmpresaControllerTest {
    @Autowired
    private EmpresaController empresaController;

    @MockBean
    private EmpresaService empresaService;

    /**
     * Method under test: {@link EmpresaController#actualizarEmpresa(Empresa)}
     */
    @Test
    void testActualizarEmpresa() throws Exception {
        // Arrange
        Empresa empresa = new Empresa();
        empresa.setDomicilioContacto("alice.liddell@example.org");
        empresa.setDomicilioEmpresa("Domicilio Empresa");
        empresa.setEmail("jane.doe@example.org");
        empresa.setId("42");
        empresa.setNombre("Nombre");
        empresa.setPaginaWeb("Pagina Web");
        empresa.setRut("Rut");
        empresa.setTelefono("Telefono");
        empresa.setTipoSociedad("Tipo Sociedad");
        when(empresaService.save(Mockito.<Empresa>any())).thenReturn(empresa);

        Empresa empresa2 = new Empresa();
        empresa2.setDomicilioContacto("alice.liddell@example.org");
        empresa2.setDomicilioEmpresa("Domicilio Empresa");
        empresa2.setEmail("jane.doe@example.org");
        empresa2.setId("42");
        empresa2.setNombre("Nombre");
        empresa2.setPaginaWeb("Pagina Web");
        empresa2.setRut("Rut");
        empresa2.setTelefono("Telefono");
        empresa2.setTipoSociedad("Tipo Sociedad");
        String content = (new ObjectMapper()).writeValueAsString(empresa2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/empresa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(empresaController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Empresa actualizada"));
    }

    /**
     * Method under test: {@link EmpresaController#eliminarEmpresa(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testEliminarEmpresa() throws Exception {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange
        // TODO: Populate arranged inputs
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/empresa/{id}", "42");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(empresaController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link EmpresaController#guardarEmpresa(Empresa)}
     */
    @Test
    void testGuardarEmpresa() throws Exception {
        // Arrange
        Empresa empresa = new Empresa();
        empresa.setDomicilioContacto("alice.liddell@example.org");
        empresa.setDomicilioEmpresa("Domicilio Empresa");
        empresa.setEmail("jane.doe@example.org");
        empresa.setId("42");
        empresa.setNombre("Nombre");
        empresa.setPaginaWeb("Pagina Web");
        empresa.setRut("Rut");
        empresa.setTelefono("Telefono");
        empresa.setTipoSociedad("Tipo Sociedad");
        when(empresaService.save(Mockito.<Empresa>any())).thenReturn(empresa);

        Empresa empresa2 = new Empresa();
        empresa2.setDomicilioContacto("alice.liddell@example.org");
        empresa2.setDomicilioEmpresa("Domicilio Empresa");
        empresa2.setEmail("jane.doe@example.org");
        empresa2.setId("42");
        empresa2.setNombre("Nombre");
        empresa2.setPaginaWeb("Pagina Web");
        empresa2.setRut("Rut");
        empresa2.setTelefono("Telefono");
        empresa2.setTipoSociedad("Tipo Sociedad");
        String content = (new ObjectMapper()).writeValueAsString(empresa2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/empresa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(empresaController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Empresa guardada"));
    }

    /**
     * Method under test: {@link EmpresaController#obtenerEmpresa(String)}
     */
    @Test
    void testObtenerEmpresa() throws Exception {
        // Arrange
        Empresa empresa = new Empresa();
        empresa.setDomicilioContacto("alice.liddell@example.org");
        empresa.setDomicilioEmpresa("Domicilio Empresa");
        empresa.setEmail("jane.doe@example.org");
        empresa.setId("42");
        empresa.setNombre("Nombre");
        empresa.setPaginaWeb("Pagina Web");
        empresa.setRut("Rut");
        empresa.setTelefono("Telefono");
        empresa.setTipoSociedad("Tipo Sociedad");
        when(empresaService.findById(Mockito.<String>any())).thenReturn(empresa);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/empresa/{id}", "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(empresaController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":\"42\",\"nombre\":\"Nombre\",\"tipoSociedad\":\"Tipo Sociedad\",\"rut\":\"Rut\",\"domicilioEmpresa\":\"Domicilio"
                                        + " Empresa\",\"paginaWeb\":\"Pagina Web\",\"email\":\"jane.doe@example.org\",\"domicilioContacto\":\"alice.liddell"
                                        + "@example.org\",\"telefono\":\"Telefono\"}"));
    }

    /**
     * Method under test: {@link EmpresaController#obtenerEmpresas()}
     */
    @Test
    void testObtenerEmpresas() throws Exception {
        // Arrange
        when(empresaService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/empresa");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(empresaController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link EmpresaController#obtenerEmpresas()}
     */
    @Test
    void testObtenerEmpresas2() throws Exception {
        // Arrange
        Empresa empresa = new Empresa();
        empresa.setDomicilioContacto("alice.liddell@example.org");
        empresa.setDomicilioEmpresa("Domicilio Empresa");
        empresa.setEmail("jane.doe@example.org");
        empresa.setId("42");
        empresa.setNombre("Nombre");
        empresa.setPaginaWeb("Pagina Web");
        empresa.setRut("Rut");
        empresa.setTelefono("Telefono");
        empresa.setTipoSociedad("Tipo Sociedad");

        ArrayList<Empresa> empresaList = new ArrayList<>();
        empresaList.add(empresa);
        when(empresaService.findAll()).thenReturn(empresaList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/empresa");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(empresaController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":\"42\",\"nombre\":\"Nombre\",\"tipoSociedad\":\"Tipo Sociedad\",\"rut\":\"Rut\",\"domicilioEmpresa\":\"Domicilio"
                                        + " Empresa\",\"paginaWeb\":\"Pagina Web\",\"email\":\"jane.doe@example.org\",\"domicilioContacto\":\"alice.liddell"
                                        + "@example.org\",\"telefono\":\"Telefono\"}]"));
    }

    /**
     * Method under test: {@link EmpresaController#obtenerNombresEmpresas()}
     */
    @Test
    void testObtenerNombresEmpresas() throws Exception {
        // Arrange
        when(empresaService.obtenerNombres()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/empresa/nombres");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(empresaController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link EmpresaController#obtenerNombresEmpresas()}
     */
    @Test
    void testObtenerNombresEmpresas2() throws Exception {
        // Arrange
        ArrayList<ListadoEmpresaDTO> listadoEmpresaDTOList = new ArrayList<>();
        listadoEmpresaDTOList.add(new ListadoEmpresaDTO("42", "Nombre"));
        when(empresaService.obtenerNombres()).thenReturn(listadoEmpresaDTOList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/empresa/nombres");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(empresaController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[{\"nombre\":\"Nombre\",\"id\":\"42\"}]"));
    }
}
