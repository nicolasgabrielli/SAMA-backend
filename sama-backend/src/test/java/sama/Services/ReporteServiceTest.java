package sama.Services;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
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
import sama.Dto.EncabezadoReporteDTO;
import sama.Dto.InfoPresetDTO;
import sama.Dto.NuevoContenidoDTO;
import sama.Entities.Reporte;
import sama.Models.Campo;
import sama.Models.Categoria;
import sama.Repositories.ReporteRepository;

@ContextConfiguration(classes = {ReporteService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ReporteServiceTest {
    @MockBean
    private EmpresaService empresaService;

    @MockBean
    private ReporteRepository reporteRepository;

    @Autowired
    private ReporteService reporteService;

    /**
     * Method under test: {@link ReporteService#findAll(String)}
     */
    @Test
    void testFindAll() {
        // Arrange
        when(reporteRepository.findAllByEmpresaId(Mockito.<String>any())).thenReturn(new ArrayList<>());

        // Act
        List<EncabezadoReporteDTO> actualFindAllResult = reporteService.findAll("42");

        // Assert
        verify(reporteRepository).findAllByEmpresaId("42");
        assertTrue(actualFindAllResult.isEmpty());
    }

    /**
     * Method under test: {@link ReporteService#findAll(String)}
     */
    @Test
    void testFindAll2() {
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

        ArrayList<Reporte> reporteList = new ArrayList<>();
        reporteList.add(reporte);
        when(reporteRepository.findAllByEmpresaId(Mockito.<String>any())).thenReturn(reporteList);

        // Act
        List<EncabezadoReporteDTO> actualFindAllResult = reporteService.findAll("42");

        // Assert
        verify(reporteRepository).findAllByEmpresaId("42");
        assertEquals(1, actualFindAllResult.size());
    }

    /**
     * Method under test: {@link ReporteService#findAll(String)}
     */
    @Test
    void testFindAll3() {
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

        Reporte reporte2 = new Reporte();
        reporte2.setAnio(0);
        reporte2.setCategorias(new ArrayList<>());
        reporte2.setEmpresaId("Empresa Id");
        reporte2.setEstado("42");
        reporte2.setFechaCreacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte2
                .setFechaModificacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte2.setId("Id");
        reporte2.setTitulo("42");

        ArrayList<Reporte> reporteList = new ArrayList<>();
        reporteList.add(reporte2);
        reporteList.add(reporte);
        when(reporteRepository.findAllByEmpresaId(Mockito.<String>any())).thenReturn(reporteList);

        // Act
        List<EncabezadoReporteDTO> actualFindAllResult = reporteService.findAll("42");

        // Assert
        verify(reporteRepository).findAllByEmpresaId("42");
        assertEquals(2, actualFindAllResult.size());
    }

    /**
     * Method under test: {@link ReporteService#save(Reporte, String)}
     */
    @Test
    void testSave() {
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
        when(reporteRepository.save(Mockito.<Reporte>any())).thenReturn(reporte);

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

        // Act
        Reporte actualSaveResult = reporteService.save(reporte2, "42");

        // Assert
        verify(reporteRepository).save(isA(Reporte.class));
        assertEquals("42", reporte2.getEmpresaId());
        assertEquals("Pendiente", reporte2.getEstado());
        assertSame(reporte, actualSaveResult);
        Date expectedFechaCreacion = reporte2.getFechaModificacion();
        assertSame(expectedFechaCreacion, reporte2.getFechaCreacion());
    }

    /**
     * Method under test: {@link ReporteService#findById(String)}
     */
    @Test
    void testFindById() {
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
        Optional<Reporte> ofResult = Optional.of(reporte);
        when(reporteRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        Reporte actualFindByIdResult = reporteService.findById("42");

        // Assert
        verify(reporteRepository).findById("42");
        assertSame(reporte, actualFindByIdResult);
    }

    /**
     * Method under test: {@link ReporteService#update(String, NuevoContenidoDTO)}
     */
    @Test
    void testUpdate() {
        // Arrange
        Optional<Reporte> emptyResult = Optional.empty();
        when(reporteRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);

        NuevoContenidoDTO contenidoNuevo = new NuevoContenidoDTO();
        contenidoNuevo.setIndexCampo(1);
        contenidoNuevo.setIndexCategoria(1);
        contenidoNuevo.setIndexSeccion(1);
        contenidoNuevo.setNuevoCampo(new Campo());
        contenidoNuevo.setNuevoTituloCategoria("Nuevo Titulo Categoria");
        contenidoNuevo.setNuevoTituloSeccion("Nuevo Titulo Seccion");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> reporteService.update("42", contenidoNuevo));
        verify(reporteRepository).findById("42");
    }

    /**
     * Method under test: {@link ReporteService#update(String, NuevoContenidoDTO)}
     */
    @Test
    void testUpdate2() {
        // Arrange
        Reporte reporte = mock(Reporte.class);
        doNothing().when(reporte).setAnio(Mockito.<Integer>any());
        doNothing().when(reporte).setCategorias(Mockito.<List<Categoria>>any());
        doNothing().when(reporte).setEmpresaId(Mockito.<String>any());
        doNothing().when(reporte).setEstado(Mockito.<String>any());
        doNothing().when(reporte).setFechaCreacion(Mockito.<Date>any());
        doNothing().when(reporte).setFechaModificacion(Mockito.<Date>any());
        doNothing().when(reporte).setId(Mockito.<String>any());
        doNothing().when(reporte).setTitulo(Mockito.<String>any());
        reporte.setAnio(1);
        reporte.setCategorias(new ArrayList<>());
        reporte.setEmpresaId("42");
        reporte.setEstado("Estado");
        reporte.setFechaCreacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte.setFechaModificacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte.setId("42");
        reporte.setTitulo("Titulo");
        Optional<Reporte> ofResult = Optional.of(reporte);

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
        when(reporteRepository.save(Mockito.<Reporte>any())).thenReturn(reporte2);
        when(reporteRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        NuevoContenidoDTO contenidoNuevo = new NuevoContenidoDTO();
        contenidoNuevo.setIndexCampo(1);
        contenidoNuevo.setIndexCategoria(null);
        contenidoNuevo.setIndexSeccion(1);
        contenidoNuevo.setNuevoCampo(new Campo());
        contenidoNuevo.setNuevoTituloCategoria("Nuevo Titulo Categoria");
        contenidoNuevo.setNuevoTituloSeccion("Nuevo Titulo Seccion");

        // Act
        Reporte actualUpdateResult = reporteService.update("42", contenidoNuevo);

        // Assert
        verify(reporteRepository).findById("42");
        verify(reporteRepository).save(isA(Reporte.class));
        verify(reporte).setAnio(1);
        verify(reporte).setCategorias(isA(List.class));
        verify(reporte).setEmpresaId("42");
        verify(reporte).setEstado("Estado");
        verify(reporte).setFechaCreacion(isA(Date.class));
        verify(reporte).setFechaModificacion(isA(Date.class));
        verify(reporte).setId("42");
        verify(reporte).setTitulo("Titulo");
        assertSame(reporte2, actualUpdateResult);
    }

    /**
     * Method under test: {@link ReporteService#update(String, NuevoContenidoDTO)}
     */
    @Test
    void testUpdate3() {
        // Arrange
        Reporte reporte = mock(Reporte.class);
        doNothing().when(reporte).setAnio(Mockito.<Integer>any());
        doNothing().when(reporte).setCategorias(Mockito.<List<Categoria>>any());
        doNothing().when(reporte).setEmpresaId(Mockito.<String>any());
        doNothing().when(reporte).setEstado(Mockito.<String>any());
        doNothing().when(reporte).setFechaCreacion(Mockito.<Date>any());
        doNothing().when(reporte).setFechaModificacion(Mockito.<Date>any());
        doNothing().when(reporte).setId(Mockito.<String>any());
        doNothing().when(reporte).setTitulo(Mockito.<String>any());
        reporte.setAnio(1);
        reporte.setCategorias(new ArrayList<>());
        reporte.setEmpresaId("42");
        reporte.setEstado("Estado");
        reporte.setFechaCreacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte.setFechaModificacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte.setId("42");
        reporte.setTitulo("Titulo");
        Optional<Reporte> ofResult = Optional.of(reporte);
        when(reporteRepository.save(Mockito.<Reporte>any())).thenThrow(new RuntimeException("foo"));
        when(reporteRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        NuevoContenidoDTO contenidoNuevo = new NuevoContenidoDTO();
        contenidoNuevo.setIndexCampo(1);
        contenidoNuevo.setIndexCategoria(null);
        contenidoNuevo.setIndexSeccion(1);
        contenidoNuevo.setNuevoCampo(new Campo());
        contenidoNuevo.setNuevoTituloCategoria("Nuevo Titulo Categoria");
        contenidoNuevo.setNuevoTituloSeccion("Nuevo Titulo Seccion");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> reporteService.update("42", contenidoNuevo));
        verify(reporteRepository).findById("42");
        verify(reporteRepository).save(isA(Reporte.class));
        verify(reporte).setAnio(eq(1));
        verify(reporte).setCategorias(isA(List.class));
        verify(reporte).setEmpresaId("42");
        verify(reporte).setEstado("Estado");
        verify(reporte).setFechaCreacion(isA(Date.class));
        verify(reporte).setFechaModificacion(isA(Date.class));
        verify(reporte).setId("42");
        verify(reporte).setTitulo("Titulo");
    }

    /**
     * Method under test: {@link ReporteService#delete(String)}
     */
    @Test
    void testDelete() {
        // Arrange
        doNothing().when(reporteRepository).deleteById(Mockito.<String>any());

        // Act
        boolean actualDeleteResult = reporteService.delete("42");

        // Assert
        verify(reporteRepository).deleteById("42");
        assertTrue(actualDeleteResult);
    }

    /**
     * Method under test: {@link ReporteService#crearPreset(InfoPresetDTO)}
     */
    @Test
    void testCrearPreset() {
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
        Optional<Reporte> ofResult = Optional.of(reporte);

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
        when(reporteRepository.save(Mockito.<Reporte>any())).thenReturn(reporte2);
        when(reporteRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        InfoPresetDTO infoPresetDTO = new InfoPresetDTO();
        infoPresetDTO.setId("42");
        infoPresetDTO.setNombre("Nombre");

        // Act
        reporteService.crearPreset(infoPresetDTO);

        // Assert
        verify(reporteRepository).findById("42");
        verify(reporteRepository).save(isA(Reporte.class));
    }

    /**
     * Method under test: {@link ReporteService#crearPreset(InfoPresetDTO)}
     */
    @Test
    void testCrearPreset2() {
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
        Optional<Reporte> ofResult = Optional.of(reporte);
        when(reporteRepository.save(Mockito.<Reporte>any())).thenThrow(new RuntimeException("Preset"));
        when(reporteRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        InfoPresetDTO infoPresetDTO = new InfoPresetDTO();
        infoPresetDTO.setId("42");
        infoPresetDTO.setNombre("Nombre");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> reporteService.crearPreset(infoPresetDTO));
        verify(reporteRepository).findById("42");
        verify(reporteRepository).save(isA(Reporte.class));
    }

    /**
     * Method under test: {@link ReporteService#crearPreset(InfoPresetDTO)}
     */
    @Test
    void testCrearPreset3() {
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
        Reporte reporte2 = mock(Reporte.class);
        when(reporte2.clonarYLimpiar()).thenReturn(reporte);
        doNothing().when(reporte2).setAnio(Mockito.<Integer>any());
        doNothing().when(reporte2).setCategorias(Mockito.<List<Categoria>>any());
        doNothing().when(reporte2).setEmpresaId(Mockito.<String>any());
        doNothing().when(reporte2).setEstado(Mockito.<String>any());
        doNothing().when(reporte2).setFechaCreacion(Mockito.<Date>any());
        doNothing().when(reporte2).setFechaModificacion(Mockito.<Date>any());
        doNothing().when(reporte2).setId(Mockito.<String>any());
        doNothing().when(reporte2).setTitulo(Mockito.<String>any());
        reporte2.setAnio(1);
        reporte2.setCategorias(new ArrayList<>());
        reporte2.setEmpresaId("42");
        reporte2.setEstado("Estado");
        reporte2.setFechaCreacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte2
                .setFechaModificacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte2.setId("42");
        reporte2.setTitulo("Titulo");
        Optional<Reporte> ofResult = Optional.of(reporte2);

        Reporte reporte3 = new Reporte();
        reporte3.setAnio(1);
        reporte3.setCategorias(new ArrayList<>());
        reporte3.setEmpresaId("42");
        reporte3.setEstado("Estado");
        reporte3.setFechaCreacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte3
                .setFechaModificacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte3.setId("42");
        reporte3.setTitulo("Titulo");
        when(reporteRepository.save(Mockito.<Reporte>any())).thenReturn(reporte3);
        when(reporteRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        InfoPresetDTO infoPresetDTO = new InfoPresetDTO();
        infoPresetDTO.setId("42");
        infoPresetDTO.setNombre("Nombre");

        // Act
        reporteService.crearPreset(infoPresetDTO);

        // Assert
        verify(reporteRepository).findById("42");
        verify(reporteRepository).save(isA(Reporte.class));
        verify(reporte2).clonarYLimpiar();
        verify(reporte2).setAnio(1);
        verify(reporte2).setCategorias(isA(List.class));
        verify(reporte2).setEmpresaId("42");
        verify(reporte2).setEstado("Estado");
        verify(reporte2).setFechaCreacion(isA(Date.class));
        verify(reporte2).setFechaModificacion(isA(Date.class));
        verify(reporte2).setId("42");
        verify(reporte2).setTitulo("Titulo");
    }

    /**
     * Method under test: {@link ReporteService#crearPreset(InfoPresetDTO)}
     */
    @Test
    void testCrearPreset4() {
        // Arrange
        Reporte reporte = mock(Reporte.class);
        doNothing().when(reporte).setAnio(Mockito.<Integer>any());
        doNothing().when(reporte).setCategorias(Mockito.<List<Categoria>>any());
        doNothing().when(reporte).setEmpresaId(Mockito.<String>any());
        doNothing().when(reporte).setEstado(Mockito.<String>any());
        doNothing().when(reporte).setFechaCreacion(Mockito.<Date>any());
        doNothing().when(reporte).setFechaModificacion(Mockito.<Date>any());
        doNothing().when(reporte).setId(Mockito.<String>any());
        doNothing().when(reporte).setTitulo(Mockito.<String>any());
        reporte.setAnio(1);
        reporte.setCategorias(new ArrayList<>());
        reporte.setEmpresaId("42");
        reporte.setEstado("Estado");
        reporte.setFechaCreacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte.setFechaModificacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte.setId("42");
        reporte.setTitulo("Titulo");
        Reporte reporte2 = mock(Reporte.class);
        when(reporte2.clonarYLimpiar()).thenReturn(reporte);
        doNothing().when(reporte2).setAnio(Mockito.<Integer>any());
        doNothing().when(reporte2).setCategorias(Mockito.<List<Categoria>>any());
        doNothing().when(reporte2).setEmpresaId(Mockito.<String>any());
        doNothing().when(reporte2).setEstado(Mockito.<String>any());
        doNothing().when(reporte2).setFechaCreacion(Mockito.<Date>any());
        doNothing().when(reporte2).setFechaModificacion(Mockito.<Date>any());
        doNothing().when(reporte2).setId(Mockito.<String>any());
        doNothing().when(reporte2).setTitulo(Mockito.<String>any());
        reporte2.setAnio(1);
        reporte2.setCategorias(new ArrayList<>());
        reporte2.setEmpresaId("42");
        reporte2.setEstado("Estado");
        reporte2.setFechaCreacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte2
                .setFechaModificacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte2.setId("42");
        reporte2.setTitulo("Titulo");
        Optional<Reporte> ofResult = Optional.of(reporte2);

        Reporte reporte3 = new Reporte();
        reporte3.setAnio(1);
        reporte3.setCategorias(new ArrayList<>());
        reporte3.setEmpresaId("42");
        reporte3.setEstado("Estado");
        reporte3.setFechaCreacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte3
                .setFechaModificacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte3.setId("42");
        reporte3.setTitulo("Titulo");
        when(reporteRepository.save(Mockito.<Reporte>any())).thenReturn(reporte3);
        when(reporteRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        InfoPresetDTO infoPresetDTO = new InfoPresetDTO();
        infoPresetDTO.setId("42");
        infoPresetDTO.setNombre("Nombre");

        // Act
        reporteService.crearPreset(infoPresetDTO);

        // Assert that nothing has changed
        verify(reporteRepository).findById("42");
        verify(reporteRepository).save(isA(Reporte.class));
        verify(reporte2).clonarYLimpiar();
        verify(reporte2).setAnio(1);
        verify(reporte).setAnio(1);
        verify(reporte2).setCategorias(isA(List.class));
        verify(reporte).setCategorias(isA(List.class));
        verify(reporte2).setEmpresaId("42");
        verify(reporte).setEmpresaId("42");
        verify(reporte, atLeast(1)).setEstado(Mockito.<String>any());
        verify(reporte2).setEstado("Estado");
        verify(reporte2).setFechaCreacion(isA(Date.class));
        verify(reporte, atLeast(1)).setFechaCreacion(isA(Date.class));
        verify(reporte2).setFechaModificacion(isA(Date.class));
        verify(reporte, atLeast(1)).setFechaModificacion(Mockito.<Date>any());
        verify(reporte2).setId("42");
        verify(reporte).setId("42");
        verify(reporte, atLeast(1)).setTitulo(Mockito.<String>any());
        verify(reporte2).setTitulo("Titulo");
        assertEquals("42", infoPresetDTO.getId());
        assertEquals("Nombre", infoPresetDTO.getNombre());
    }

    /**
     * Method under test: {@link ReporteService#findAllPresets()}
     */
    @Test
    void testFindAllPresets() {
        // Arrange
        when(reporteRepository.findAllByEstado(Mockito.<String>any())).thenReturn(new ArrayList<>());

        // Act
        List<EncabezadoReporteDTO> actualFindAllPresetsResult = reporteService.findAllPresets();

        // Assert
        verify(reporteRepository).findAllByEstado("Preset");
        assertTrue(actualFindAllPresetsResult.isEmpty());
    }

    /**
     * Method under test: {@link ReporteService#findAllPresets()}
     */
    @Test
    void testFindAllPresets2() {
        // Arrange
        Reporte reporte = new Reporte();
        reporte.setAnio(1);
        reporte.setCategorias(new ArrayList<>());
        reporte.setEmpresaId("42");
        reporte.setEstado("Preset");
        reporte.setFechaCreacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte.setFechaModificacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte.setId("42");
        reporte.setTitulo("Preset");

        ArrayList<Reporte> reporteList = new ArrayList<>();
        reporteList.add(reporte);
        when(reporteRepository.findAllByEstado(Mockito.<String>any())).thenReturn(reporteList);

        // Act
        List<EncabezadoReporteDTO> actualFindAllPresetsResult = reporteService.findAllPresets();

        // Assert
        verify(reporteRepository).findAllByEstado("Preset");
        assertEquals(1, actualFindAllPresetsResult.size());
    }

    /**
     * Method under test: {@link ReporteService#findAllPresets()}
     */
    @Test
    void testFindAllPresets3() {
        // Arrange
        when(reporteRepository.findAllByEstado(Mockito.<String>any())).thenThrow(new RuntimeException("Preset"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> reporteService.findAllPresets());
        verify(reporteRepository).findAllByEstado("Preset");
    }

    /**
     * Method under test: {@link ReporteService#findAllPresets()}
     */
    @Test
    void testFindAllPresets4() {
        // Arrange
        Reporte reporte = mock(Reporte.class);
        when(reporte.getFechaModificacion())
                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        when(reporte.getAnio()).thenReturn(1);
        when(reporte.getEstado()).thenReturn("Estado");
        when(reporte.getId()).thenReturn("42");
        when(reporte.getTitulo()).thenReturn("Titulo");
        when(reporte.getFechaCreacion())
                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        doNothing().when(reporte).setAnio(Mockito.<Integer>any());
        doNothing().when(reporte).setCategorias(Mockito.<List<Categoria>>any());
        doNothing().when(reporte).setEmpresaId(Mockito.<String>any());
        doNothing().when(reporte).setEstado(Mockito.<String>any());
        doNothing().when(reporte).setFechaCreacion(Mockito.<Date>any());
        doNothing().when(reporte).setFechaModificacion(Mockito.<Date>any());
        doNothing().when(reporte).setId(Mockito.<String>any());
        doNothing().when(reporte).setTitulo(Mockito.<String>any());
        reporte.setAnio(1);
        reporte.setCategorias(new ArrayList<>());
        reporte.setEmpresaId("42");
        reporte.setEstado("Preset");
        reporte.setFechaCreacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte.setFechaModificacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte.setId("42");
        reporte.setTitulo("Preset");

        ArrayList<Reporte> reporteList = new ArrayList<>();
        reporteList.add(reporte);
        when(reporteRepository.findAllByEstado(Mockito.<String>any())).thenReturn(reporteList);

        // Act
        List<EncabezadoReporteDTO> actualFindAllPresetsResult = reporteService.findAllPresets();

        // Assert
        verify(reporte).getAnio();
        verify(reporte).getEstado();
        verify(reporte).getFechaCreacion();
        verify(reporte).getFechaModificacion();
        verify(reporte).getId();
        verify(reporte).getTitulo();
        verify(reporte).setAnio(1);
        verify(reporte).setCategorias(isA(List.class));
        verify(reporte).setEmpresaId("42");
        verify(reporte).setEstado("Preset");
        verify(reporte).setFechaCreacion(isA(Date.class));
        verify(reporte).setFechaModificacion(isA(Date.class));
        verify(reporte).setId("42");
        verify(reporte).setTitulo("Preset");
        verify(reporteRepository).findAllByEstado("Preset");
        assertEquals(1, actualFindAllPresetsResult.size());
    }

    /**
     * Method under test: {@link ReporteService#findAllPresets()}
     */
    @Test
    void testFindAllPresets5() {
        // Arrange
        Reporte reporte = mock(Reporte.class);
        when(reporte.getFechaModificacion()).thenThrow(new RuntimeException("Preset"));
        when(reporte.getAnio()).thenReturn(1);
        when(reporte.getEstado()).thenReturn("Estado");
        when(reporte.getId()).thenReturn("42");
        when(reporte.getTitulo()).thenReturn("Titulo");
        when(reporte.getFechaCreacion())
                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        doNothing().when(reporte).setAnio(Mockito.<Integer>any());
        doNothing().when(reporte).setCategorias(Mockito.<List<Categoria>>any());
        doNothing().when(reporte).setEmpresaId(Mockito.<String>any());
        doNothing().when(reporte).setEstado(Mockito.<String>any());
        doNothing().when(reporte).setFechaCreacion(Mockito.<Date>any());
        doNothing().when(reporte).setFechaModificacion(Mockito.<Date>any());
        doNothing().when(reporte).setId(Mockito.<String>any());
        doNothing().when(reporte).setTitulo(Mockito.<String>any());
        reporte.setAnio(1);
        reporte.setCategorias(new ArrayList<>());
        reporte.setEmpresaId("42");
        reporte.setEstado("Preset");
        reporte.setFechaCreacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte.setFechaModificacion(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reporte.setId("42");
        reporte.setTitulo("Preset");

        ArrayList<Reporte> reporteList = new ArrayList<>();
        reporteList.add(reporte);
        when(reporteRepository.findAllByEstado(Mockito.<String>any())).thenReturn(reporteList);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> reporteService.findAllPresets());
        verify(reporte).getAnio();
        verify(reporte).getEstado();
        verify(reporte).getFechaCreacion();
        verify(reporte).getFechaModificacion();
        verify(reporte).getId();
        verify(reporte).getTitulo();
        verify(reporte).setAnio(eq(1));
        verify(reporte).setCategorias(isA(List.class));
        verify(reporte).setEmpresaId("42");
        verify(reporte).setEstado("Preset");
        verify(reporte).setFechaCreacion(isA(Date.class));
        verify(reporte).setFechaModificacion(isA(Date.class));
        verify(reporte).setId("42");
        verify(reporte).setTitulo("Preset");
        verify(reporteRepository).findAllByEstado("Preset");
    }
}
