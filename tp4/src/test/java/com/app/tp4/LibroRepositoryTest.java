package com.app.tp4;

import com.app.tp4.enumeraciones.EstadoLibro;
import com.app.tp4.model.Libro;
import com.app.tp4.repository.LibroRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class LibroRepositoryTest {
    private LibroRepositoryImpl libroRepository;
    private Libro libro1;
    private Libro libro2;

    @BeforeEach
    void setUp() {
        libroRepository = new LibroRepositoryImpl();
        libro1 = new Libro();
        libro1.setTitulo("Libro 1");
        libro1.setIsbn("123");
        libro1.setAutor("Martin Fowler");
        libro1.setEstado(EstadoLibro.DISPONIBLE);
        libro2 = new Libro(2L, "Libro 2", "124", "Marta", EstadoLibro.DISPONIBLE);
    }

    @Test
    void saveTest() {
        Libro saved = libroRepository.save(libro1);

        assertNotNull(saved.getId());
        assertEquals("Libro 1", saved.getTitulo());
    }

    @Test
    void findByIdReturnsCorrectLibroTest() {
        Libro saved = libroRepository.save(libro1);

        Optional<Libro> result = libroRepository.findById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals("123", result.get().getIsbn());
    }

    @Test
    void findByIdReturnsNull(){
        Libro saved = libroRepository.save(libro1);

        Optional<Libro> result = libroRepository.findById(2L);

        assertFalse(result.isPresent());
    }

    @Test
    void findByIsbnReturnsCorrectLibroTest() {
        libroRepository.save(libro1);

        Optional<Libro> found = libroRepository.findByIsbn("123");

        assertTrue(found.isPresent());
        assertEquals("Libro 1", found.get().getTitulo());
    }

    @Test
    void findByIsbnReturnsNull(){
        libroRepository.save(libro1);

        Optional<Libro> found = libroRepository.findByIsbn(null);

        assertFalse(found.isPresent());
    }

    @Test
    void findAllReturnsAllLibrosTest() {
        libroRepository.save(libro1);
        libroRepository.save(libro2);

        List<Libro> all = libroRepository.findAll();

        assertEquals(2, all.size());
    }

    @Test
    void deleteByIdRemovesLibroTest() {
        Libro saved = libroRepository.save(libro1);
        Long id = saved.getId();

        libroRepository.deleteById(id);

        assertFalse(libroRepository.findById(id).isPresent());
    }

    @Test
    void existsByIdTest() {
        Libro saved = libroRepository.save(libro1);
        assertTrue(libroRepository.existsById(saved.getId()));

        libroRepository.deleteById(saved.getId());
        assertFalse(libroRepository.existsById(saved.getId()));
    }

    @Test
    void updateReplacesLibroTest() {
        Libro saved = libroRepository.save(libro1);
        Long id = saved.getId();

        assertEquals("Libro 1", saved.getTitulo());
        libro1.setTitulo("Libro 2");

        libroRepository.update(libro1);

        Optional<Libro> result = libroRepository.findById(id);

        assertTrue(result.isPresent());
        assertEquals("Libro 2", result.get().getTitulo());
    }
}
