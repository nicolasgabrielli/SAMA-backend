package sama.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sama.Entities.Usuario;
import sama.Repositories.UsuarioRepository;

@ContextConfiguration(classes = {UsuarioService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UsuarioServiceTest {
    @MockBean
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Method under test: {@link UsuarioService#obtenerUsuarios()}
     */
    @Test
    void testObtenerUsuarios() {
        // Arrange
        ArrayList<Usuario> usuarioList = new ArrayList<>();
        when(usuarioRepository.findAll()).thenReturn(usuarioList);

        // Act
        List<Usuario> actualObtenerUsuariosResult = usuarioService.obtenerUsuarios();

        // Assert
        verify(usuarioRepository).findAll();
        assertTrue(actualObtenerUsuariosResult.isEmpty());
        assertSame(usuarioList, actualObtenerUsuariosResult);
    }

    /**
     * Method under test: {@link UsuarioService#save(Usuario)}
     */
    @Test
    void testSave() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setContrasenia("Contrasenia");
        usuario.setCorreo("Correo");
        usuario.setEmpresas(new ArrayList<>());
        usuario.setId("42");
        usuario.setNombre("Nombre");
        usuario.setRol("Rol");
        when(usuarioRepository.save(Mockito.<Usuario>any())).thenReturn(usuario);

        Usuario usuario2 = new Usuario();
        usuario2.setContrasenia("Contrasenia");
        usuario2.setCorreo("Correo");
        usuario2.setEmpresas(new ArrayList<>());
        usuario2.setId("42");
        usuario2.setNombre("Nombre");
        usuario2.setRol("Rol");

        // Act
        Usuario actualSaveResult = usuarioService.save(usuario2);

        // Assert
        verify(usuarioRepository).save(isA(Usuario.class));
        assertSame(usuario, actualSaveResult);
    }

    /**
     * Method under test: {@link UsuarioService#deleteById(String)}
     */
    @Test
    void testDeleteById() {
        // Arrange
        doNothing().when(usuarioRepository).deleteById(Mockito.<String>any());
        when(usuarioRepository.existsById(Mockito.<String>any())).thenReturn(true);

        // Act
        int actualDeleteByIdResult = usuarioService.deleteById("42");

        // Assert
        verify(usuarioRepository).deleteById("42");
        verify(usuarioRepository).existsById("42");
        assertEquals(1, actualDeleteByIdResult);
    }

    /**
     * Method under test: {@link UsuarioService#deleteById(String)}
     */
    @Test
    void testDeleteById2() {
        // Arrange
        when(usuarioRepository.existsById(Mockito.<String>any())).thenReturn(false);

        // Act
        int actualDeleteByIdResult = usuarioService.deleteById("42");

        // Assert
        verify(usuarioRepository).existsById("42");
        assertEquals(0, actualDeleteByIdResult);
    }

    /**
     * Method under test: {@link UsuarioService#obtenerUsuarioPorId(String)}
     */
    @Test
    void testObtenerUsuarioPorId() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setContrasenia("Contrasenia");
        usuario.setCorreo("Correo");
        usuario.setEmpresas(new ArrayList<>());
        usuario.setId("42");
        usuario.setNombre("Nombre");
        usuario.setRol("Rol");
        Optional<Usuario> ofResult = Optional.of(usuario);
        when(usuarioRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        Usuario actualObtenerUsuarioPorIdResult = usuarioService.obtenerUsuarioPorId("42");

        // Assert
        verify(usuarioRepository).findById("42");
        assertSame(usuario, actualObtenerUsuarioPorIdResult);
    }
}
