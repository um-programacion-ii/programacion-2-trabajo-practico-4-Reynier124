package com.app.tp4;

import com.app.tp4.enumeraciones.EstadoLibro;
import com.app.tp4.enumeraciones.EstadoUsuario;
import com.app.tp4.model.Libro;
import com.app.tp4.model.Prestamo;
import com.app.tp4.model.Usuario;
import com.app.tp4.repository.PrestamoRepository;
import com.app.tp4.services.PrestamoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PrestamoServiceTest {
    @Mock
    private PrestamoRepository prestamoRepository;

    @InjectMocks
    private PrestamoServiceImpl prestamoService;

    private Prestamo prestamo;
    private Usuario usuario;
    private Libro libro;
    private Long id;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        id = 1L;
        usuario = new Usuario(1L, "Marcos", "mano@gmail.com", EstadoUsuario.ACTIVO);
        libro = new Libro(1L, "123-456", "Libro de Prueba", "Autor", EstadoLibro.DISPONIBLE);
        prestamo = new Prestamo(1L, libro, usuario, LocalDate.now(), LocalDate.now().plusDays(7));
    }

    @Test
    public void guardarPrestamo() {
        when(prestamoRepository.save(prestamo)).thenReturn(prestamo);

        Prestamo resultado = prestamoService.save(prestamo);

        assertNotNull(resultado);
        assertEquals(prestamo.getId(), resultado.getId());
        verify(prestamoRepository).save(prestamo);
    }

    @Test
    public void actualizarPrestamo() {
        when(prestamoRepository.update(prestamo)).thenReturn(prestamo);

        Prestamo resultado = prestamoService.update(prestamo);

        assertNotNull(resultado);
        verify(prestamoRepository).update(prestamo);
    }

    @Test
    public void cuandoBuscarPorIdExiste() {
        when(prestamoRepository.findById(id)).thenReturn(Optional.of(prestamo));

        Prestamo resultado = prestamoService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(prestamoRepository).findById(id);
    }

    @Test
    public void cuandoBuscarPorIdNoExiste() {
        when(prestamoRepository.findById(id)).thenReturn(Optional.empty());

        Prestamo resultado = prestamoService.buscarPorId(id);

        assertNull(resultado);
        verify(prestamoRepository).findById(id);
    }

    @Test
    public void cuandoBuscarPorUsuarioExiste() {
        when(prestamoRepository.findByUsuario(usuario)).thenReturn(Optional.of(prestamo));

        Prestamo resultado = prestamoService.buscarPorUsuario(usuario);

        assertNotNull(resultado);
        assertEquals(usuario, resultado.getUsuario());
        verify(prestamoRepository).findByUsuario(usuario);
    }

    @Test
    public void cuandoBuscarPorUsuarioNoExiste() {
        when(prestamoRepository.findByUsuario(usuario)).thenReturn(Optional.empty());

        Prestamo resultado = prestamoService.buscarPorUsuario(usuario);

        assertNull(resultado);
        verify(prestamoRepository).findByUsuario(usuario);
    }

    @Test
    public void cuandoBuscarPorLibroExiste() {
        when(prestamoRepository.findByLibro(libro)).thenReturn(Optional.of(prestamo));

        Prestamo resultado = prestamoService.buscarPorLibro(libro);

        assertNotNull(resultado);
        assertEquals(libro, resultado.getLibro());
        verify(prestamoRepository).findByLibro(libro);
    }

    @Test
    public void cuandoBuscarPorLibroNoExiste() {
        when(prestamoRepository.findByLibro(libro)).thenReturn(Optional.empty());

        Prestamo resultado = prestamoService.buscarPorLibro(libro);

        assertNull(resultado);
        verify(prestamoRepository).findByLibro(libro);
    }

    @Test
    public void buscarTodos() {
        List<Prestamo> prestamos = Arrays.asList(prestamo);
        when(prestamoRepository.findAll()).thenReturn(prestamos);

        List<Prestamo> resultado = prestamoService.buscarTodos();

        assertEquals(1, resultado.size());
        verify(prestamoRepository).findAll();
    }

    @Test
    public void cuandoEliminarPrestamo() {
        prestamoService.delete(id);

        verify(prestamoRepository).deleteById(id);
    }

    @Test
    public void cuandoExistePrestamoPorId() {
        when(prestamoRepository.existsById(id)).thenReturn(true);

        boolean existe = prestamoService.existe(id);

        assertTrue(existe);
        verify(prestamoRepository).existsById(id);
    }
}
