package sama.controllers;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

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
import sama.entities.Usuario;
import sama.services.UsuarioService;

@ContextConfiguration(classes = {UsuarioController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UsuarioControllerTest {
    @Autowired
    private UsuarioController usuarioController;

    @MockBean
    private UsuarioService usuarioService;

    /**
     * Method under test: {@link UsuarioController#obtenerUsuarios()}
     */
    @Test
    void testObtenerUsuarios() throws Exception {
        // Arrange
        when(usuarioService.obtenerUsuarios()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/usuario");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(usuarioController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link UsuarioController#obtenerUsuarios()}
     */
    @Test
    void testObtenerUsuarios2() throws Exception {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setContrasenia("Contrasenia");
        usuario.setCorreo("Correo");
        usuario.setEmpresas(new ArrayList<>());
        usuario.setId("42");
        usuario.setNombre("Nombre");
        usuario.setRol("Rol");

        ArrayList<Usuario> usuarioList = new ArrayList<>();
        usuarioList.add(usuario);
        when(usuarioService.obtenerUsuarios()).thenReturn(usuarioList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/usuario");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(usuarioController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":\"42\",\"nombre\":\"Nombre\",\"contrasenia\":\"Contrasenia\",\"correo\":\"Correo\",\"rol\":\"Rol\",\"empresas"
                                        + "\":[]}]"));
    }

    /**
     * Method under test: {@link UsuarioController#deleteUser(String)}
     */
    @Test
    void testDeleteUser() throws Exception {
        // Arrange
        when(usuarioService.deleteById(Mockito.<String>any())).thenReturn(1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/usuario/{id}", "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(usuarioController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Usuario eliminado"));
    }

    /**
     * Method under test: {@link UsuarioController#deleteUser(String)}
     */
    @Test
    void testDeleteUser2() throws Exception {
        // Arrange
        when(usuarioService.deleteById(Mockito.<String>any())).thenReturn(0);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/usuario/{id}", "");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(usuarioController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    /**
     * Method under test: {@link UsuarioController#obtenerUsuarioPorId(String)}
     */
    @Test
    void testObtenerUsuarioPorId() throws Exception {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setContrasenia("Contrasenia");
        usuario.setCorreo("Correo");
        usuario.setEmpresas(new ArrayList<>());
        usuario.setId("42");
        usuario.setNombre("Nombre");
        usuario.setRol("Rol");


        when(usuarioService.obtenerUsuarioPorId(Mockito.<String>any())).thenReturn(usuario);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/usuario/{id}", "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(usuarioController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":\"42\",\"nombre\":\"Nombre\",\"contrasenia\":\"Contrasenia\",\"correo\":\"Correo\",\"rol\":\"Rol\","
                                        + "\"empresas\":[]}"));
    }

    /**
     * Method under test: {@link UsuarioController#obtenerUsuarioPorId(String)}
     */
    @Test
    void testObtenerUsuarioPorId2() throws Exception {
        // Arrange
        when(usuarioService.obtenerUsuarioPorId(Mockito.<String>any())).thenReturn(new Usuario());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/usuario/{id}", "");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(usuarioController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    /**
     * Method under test: {@link UsuarioController#saveUser(Usuario)}
     */
    @Test
    void testSaveUser() throws Exception {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setContrasenia("Contrasenia");
        usuario.setCorreo("Correo");
        usuario.setEmpresas(new ArrayList<>());
        usuario.setId("42");
        usuario.setNombre("Nombre");
        usuario.setRol("Rol");
        when(usuarioService.save(Mockito.<Usuario>any())).thenReturn(usuario);

        Usuario usuario2 = new Usuario();
        usuario2.setContrasenia("Contrasenia");
        usuario2.setCorreo("Correo");
        usuario2.setEmpresas(new ArrayList<>());
        usuario2.setId("42");
        usuario2.setNombre("Nombre");
        usuario2.setRol("Rol");
        String content = (new ObjectMapper()).writeValueAsString(usuario2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(usuarioController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Usuario guardado"));
    }

    /**
     * Method under test: {@link UsuarioController#saveUser(Usuario)}
     */
    @Test
    void testSaveUser2() throws Exception {
        // Arrange
        String content = (new ObjectMapper()).writeValueAsString(new Usuario());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(usuarioController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Error al guardar usuario"));
    }

    /**
     * Method under test: {@link UsuarioController#updateUser(Usuario)}
     */
    @Test
    void testUpdateUser() throws Exception {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setContrasenia("Contrasenia");
        usuario.setCorreo("Correo");
        usuario.setEmpresas(new ArrayList<>());
        usuario.setId("42");
        usuario.setNombre("Nombre");
        usuario.setRol("Rol");
        when(usuarioService.save(Mockito.<Usuario>any())).thenReturn(usuario);

        Usuario usuario2 = new Usuario();
        usuario2.setContrasenia("Contrasenia");
        usuario2.setCorreo("Correo");
        usuario2.setEmpresas(new ArrayList<>());
        usuario2.setId("42");
        usuario2.setNombre("Nombre");
        usuario2.setRol("Rol");
        String content = (new ObjectMapper()).writeValueAsString(usuario2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(usuarioController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Usuario actualizado"));
    }

    /**
     * Method under test: {@link UsuarioController#updateUser(Usuario)}
     */
    @Test
    void testUpdateUser2() throws Exception {
        // Arrange
        String content = (new ObjectMapper()).writeValueAsString(new Usuario());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(usuarioController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Error al actualizar usuario"));
    }

}
