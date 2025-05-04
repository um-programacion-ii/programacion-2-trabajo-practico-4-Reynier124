package com.app.tp4;

import com.app.tp4.enumeraciones.EstadoLibro;
import com.app.tp4.model.Libro;
import com.app.tp4.repository.LibroRepository;
import com.app.tp4.services.LibroService;
import com.app.tp4.services.LibroServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class LibroServiceTest {
    private Libro libro;
    @Mock
    private LibroRepository libroRepository;
    @InjectMocks
    private LibroServiceImpl libroService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        libro = new Libro(1L, "978-3-16-148410-0", "Test Book", "Test Author", EstadoLibro.DISPONIBLE);

    }

    @Test
    void guardarLibro() {
        when(libroRepository.save(libro)).thenReturn(libro);

        Libro resultado = libroService.save(libro);

        assertNotNull(resultado);
        assertEquals(libro.getTitulo(), resultado.getTitulo());
        verify(libroRepository).save(libro);
    }

    @Test
    void ActualizarLibro() {
        when(libroRepository.update(libro)).thenReturn(libro);

        Libro resultado = libroService.update(libro);

        assertNotNull(resultado);
        assertEquals(libro.getId(), resultado.getId());
        verify(libroRepository).update(libro);
    }

    @Test
    void EliminarLibro() {
        Long id = 1L;

        libroService.delete(id);

        verify(libroRepository).deleteById(id);
    }

    @Test
    void cuandoExisteLibroPorId() {
        Long id = 1L;
        when(libroRepository.existsById(id)).thenReturn(true);

        boolean existe = libroService.existe(id);

        assertTrue(existe);
        verify(libroRepository).existsById(id);
    }

    @Test
    void cuandoBuscarPorIdExiste() {
        Long id = 1L;
        when(libroRepository.findById(id)).thenReturn(Optional.of(libro));

        Libro resultado = libroService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(libroRepository).findById(id);
    }

    @Test
    void cuandoBuscarPorIdNoExiste() {
        Long id = 1L;
        when(libroRepository.findById(id)).thenReturn(Optional.empty());

        Libro resultado = libroService.buscarPorId(id);

        assertNull(resultado);
        verify(libroRepository).findById(id);
    }

    @Test
    void cuandoBuscarLibroPorIsbnExiste(){
        String isbn = "978-3-16-148410-0";
        when(libroRepository.findByIsbn(isbn)).thenReturn(Optional.of(libro));

        Libro resultado = libroService.buscarPorIsbn(isbn);

        assertNotNull(resultado);
        assertEquals(isbn, resultado.getIsbn());
        verify(libroRepository).findByIsbn(isbn);
    }

    @Test
    void cuandoBuscarPorIsbnNoExiste() {
        String isbn = "978-3-16-148410-1";
        when(libroRepository.findByIsbn(isbn)).thenReturn(Optional.empty());

        Libro resultado = libroService.buscarPorIsbn(isbn);

        assertNull(resultado);
        verify(libroRepository).findByIsbn(isbn);

    }
}
