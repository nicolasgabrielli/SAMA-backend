package sama.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sama.dto.ListadoEmpresaDTO;
import sama.entity.Empresa;
import sama.repository.EmpresaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {EmpresaService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class EmpresaServiceTest {
    @MockBean
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaService empresaService;

    /**
     * Method under test: {@link EmpresaService#findAll()}
     */
    @Test
    void testFindAll() {
        // Arrange
        ArrayList<Empresa> empresaList = new ArrayList<>();
        when(empresaRepository.findAll()).thenReturn(empresaList);

        // Act
        List<Empresa> actualFindAllResult = empresaService.findAll();

        // Assert
        verify(empresaRepository).findAll();
        assertTrue(actualFindAllResult.isEmpty());
        assertSame(empresaList, actualFindAllResult);
    }

    /**
     * Method under test: {@link EmpresaService#save(Empresa)}
     */
    @Test
    void testSave() {
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
        when(empresaRepository.save(Mockito.any())).thenReturn(empresa);

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

        // Act
        Empresa actualSaveResult = empresaService.save(empresa2);

        // Assert
        verify(empresaRepository).save(isA(Empresa.class));
        assertSame(empresa, actualSaveResult);
    }

    /**
     * Method under test: {@link EmpresaService#deleteById(String)}
     */
    @Test
    void testDeleteById() {
        // Arrange
        doNothing().when(empresaRepository).deleteById(Mockito.any());

        // Act
        empresaService.deleteById("42");

        // Assert that nothing has changed
        verify(empresaRepository).deleteById("42");
        assertTrue(empresaService.findAll().isEmpty());
    }

    /**
     * Method under test: {@link EmpresaService#obtenerNombres()}
     */
    @Test
    void testObtenerNombres() {
        // Arrange
        when(empresaRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<ListadoEmpresaDTO> actualObtenerNombresResult = empresaService.obtenerNombres();

        // Assert
        verify(empresaRepository).findAll();
        assertTrue(actualObtenerNombresResult.isEmpty());
    }

    /**
     * Method under test: {@link EmpresaService#obtenerNombres()}
     */
    @Test
    void testObtenerNombres2() {
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
        when(empresaRepository.findAll()).thenReturn(empresaList);

        // Act
        List<ListadoEmpresaDTO> actualObtenerNombresResult = empresaService.obtenerNombres();

        // Assert
        verify(empresaRepository).findAll();
        assertEquals(1, actualObtenerNombresResult.size());
        ListadoEmpresaDTO getResult = actualObtenerNombresResult.get(0);
        assertEquals("42", getResult.getId());
        assertEquals("Nombre", getResult.getNombre());
    }

    /**
     * Method under test: {@link EmpresaService#findById(String)}
     */
    @Test
    void testFindById() {
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
        Optional<Empresa> ofResult = Optional.of(empresa);
        when(empresaRepository.findById(Mockito.any())).thenReturn(ofResult);

        // Act
        Empresa actualFindByIdResult = empresaService.findById("42");

        // Assert
        verify(empresaRepository).findById("42");
        assertSame(empresa, actualFindByIdResult);
    }
}
