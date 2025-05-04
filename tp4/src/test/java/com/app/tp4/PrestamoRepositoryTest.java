package com.app.tp4;

import com.app.tp4.enumeraciones.EstadoLibro;
import com.app.tp4.enumeraciones.EstadoUsuario;
import com.app.tp4.model.Libro;
import com.app.tp4.model.Prestamo;
import com.app.tp4.model.Usuario;
import com.app.tp4.repository.PrestamoRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class PrestamoRepositoryTest {
    private PrestamoRepositoryImpl prestamoRepository;
    private Prestamo prestamo;
    private Usuario usuario;
    private Libro libro;

    @BeforeEach
    void setUp() {
        prestamoRepository = new PrestamoRepositoryImpl();

        usuario = new Usuario();
        usuario.setNombre("Lucía");
        usuario.setEstado(EstadoUsuario.ACTIVO);

        libro = new Libro();
        libro.setTitulo("Clean Code");
        libro.setAutor("Robert C. Martin");
        libro.setIsbn("987654321");
        libro.setEstado(EstadoLibro.DISPONIBLE);

        prestamo = new Prestamo();
        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);
        prestamo.setFechaPrestamo(LocalDate.now());
    }

    @Test
    void saveTest() {
        Prestamo saved = prestamoRepository.save(prestamo);

        assertNotNull(saved.getId());
        assertEquals("Lucía", saved.getUsuario().getNombre());
    }

    @Test
    void findByIdReturnsCorrectPrestamoTest() {
        Prestamo saved = prestamoRepository.save(prestamo);

        Optional<Prestamo> result = prestamoRepository.findById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals("Clean Code", result.get().getLibro().getTitulo());
    }

    @Test
    void findByIdReturnsNull() {
        prestamoRepository.save(prestamo);

        Optional<Prestamo> result = prestamoRepository.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void findByLibroReturnsCorrectPrestamoTest() {
        prestamoRepository.save(prestamo);

        Optional<Prestamo> result = prestamoRepository.findByLibro(libro);

        assertTrue(result.isPresent());
        assertEquals("Lucía", result.get().getUsuario().getNombre());
    }

    @Test
    void findByLibroReturnsNull() {
        prestamoRepository.save(prestamo);

        Libro otroLibro = new Libro();
        otroLibro.setId(2L);
        otroLibro.setTitulo("Otro libro");
        otroLibro.setIsbn("0000");

        Optional<Prestamo> result = prestamoRepository.findByLibro(otroLibro);

        assertFalse(result.isPresent());
    }

    @Test
    void findByUsuarioReturnsCorrectPrestamoTest() {
        prestamoRepository.save(prestamo);

        Optional<Prestamo> result = prestamoRepository.findByUsuario(usuario);

        assertTrue(result.isPresent());
        assertEquals("Clean Code", result.get().getLibro().getTitulo());
    }

    @Test
    void findByUsuarioReturnsNull() {
        prestamoRepository.save(prestamo);

        Usuario otroUsuario = new Usuario();
        otroUsuario.setId(2L);
        otroUsuario.setNombre("Pedro");

        Optional<Prestamo> result = prestamoRepository.findByUsuario(otroUsuario);

        assertFalse(result.isPresent());
    }

    @Test
    void findAllReturnsAllPrestamosTest() {
        prestamoRepository.save(prestamo);
        Prestamo otro = new Prestamo();
        otro.setLibro(libro);
        otro.setUsuario(usuario);
        otro.setFechaPrestamo(LocalDate.now());

        prestamoRepository.save(otro);

        List<Prestamo> prestamos = prestamoRepository.findAll();

        assertEquals(2, prestamos.size());
    }

    @Test
    void deleteByIdRemovesPrestamoTest() {
        Prestamo saved = prestamoRepository.save(prestamo);
        Long id = saved.getId();

        prestamoRepository.deleteById(id);

        assertFalse(prestamoRepository.findById(id).isPresent());
    }

    @Test
    void existsByIdTest() {
        Prestamo saved = prestamoRepository.save(prestamo);

        assertTrue(prestamoRepository.existsById(saved.getId()));

        prestamoRepository.deleteById(saved.getId());

        assertFalse(prestamoRepository.existsById(saved.getId()));
    }

    @Test
    void updateReplacesPrestamoTest() {
        Prestamo saved = prestamoRepository.save(prestamo);
        Long id = saved.getId();

        prestamo.setFechaDevolucion(LocalDate.now().plusDays(7));
        prestamoRepository.update(prestamo);

        Optional<Prestamo> updated = prestamoRepository.findById(id);

        assertTrue(updated.isPresent());
        assertEquals(LocalDate.now().plusDays(7), updated.get().getFechaDevolucion());
    }
}
