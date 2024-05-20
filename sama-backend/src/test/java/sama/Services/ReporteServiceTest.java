package sama.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import sama.DTO.EncabezadoReporteDTO;
import sama.Entities.Reporte;
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
        verify(reporteRepository).findAllByEmpresaId(eq("42"));
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
        verify(reporteRepository).findAllByEmpresaId(eq("42"));
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
        verify(reporteRepository).findAllByEmpresaId(eq("42"));
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
        verify(reporteRepository).findById(eq("42"));
        assertSame(reporte, actualFindByIdResult);
    }

    /**
     * Method under test: {@link ReporteService#update(Reporte)}
     */
    @Test
    void testUpdate() {
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
        Reporte actualUpdateResult = reporteService.update(reporte2);

        // Assert
        verify(reporteRepository).save(isA(Reporte.class));
        assertSame(reporte, actualUpdateResult);
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
        verify(reporteRepository).deleteById(eq("42"));
        assertTrue(actualDeleteResult);
    }
}
