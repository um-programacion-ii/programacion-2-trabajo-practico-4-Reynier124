package com.app.tp4;

import com.app.tp4.enumeraciones.EstadoUsuario;
import com.app.tp4.model.Usuario;
import com.app.tp4.repository.UsuarioRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class UsuarioRepositoryTest {
    private UsuarioRepositoryImpl usuarioRepository;
    private Usuario usuario1;
    private Usuario usuario2;

    @BeforeEach
    void setUp() {
        usuarioRepository = new UsuarioRepositoryImpl();

        usuario1 = new Usuario();
        usuario1.setNombre("Carlos");
        usuario1.setEstado(EstadoUsuario.ACTIVO);

        usuario2 = new Usuario(2L, "Ana", "Gomez", EstadoUsuario.SUSPENDIDO);
    }

    @Test
    void saveTest() {
        Usuario saved = usuarioRepository.save(usuario1);

        assertNotNull(saved.getId());
        assertEquals("Carlos", saved.getNombre());
    }

    @Test
    void findByIdReturnsCorrectUsuarioTest() {
        Usuario saved = usuarioRepository.save(usuario1);

        Optional<Usuario> result = usuarioRepository.findById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals("Carlos", result.get().getNombre());
    }

    @Test
    void findByIdReturnsNull() {
        usuarioRepository.save(usuario1);

        Optional<Usuario> result = usuarioRepository.findById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void findByNombreReturnsCorrectUsuarioTest() {
        usuarioRepository.save(usuario1);

        Optional<Usuario> found = usuarioRepository.findByNombre("Carlos");

        assertTrue(found.isPresent());
        assertEquals("Carlos", found.get().getNombre());
    }

    @Test
    void findByNombreReturnsNull() {
        usuarioRepository.save(usuario1);

        Optional<Usuario> found = usuarioRepository.findByNombre("NoExiste");

        assertFalse(found.isPresent());
    }

    @Test
    void findAllReturnsAllUsuariosTest() {
        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);

        List<Usuario> all = usuarioRepository.findAll();

        assertEquals(2, all.size());
    }

    @Test
    void deleteByIdRemovesUsuarioTest() {
        Usuario saved = usuarioRepository.save(usuario1);
        Long id = saved.getId();

        usuarioRepository.deleteById(id);

        assertFalse(usuarioRepository.findById(id).isPresent());
    }

    @Test
    void existsByIdTest() {
        Usuario saved = usuarioRepository.save(usuario1);
        assertTrue(usuarioRepository.existsById(saved.getId()));

        usuarioRepository.deleteById(saved.getId());
        assertFalse(usuarioRepository.existsById(saved.getId()));
    }

    @Test
    void updateReplacesUsuarioTest() {
        Usuario saved = usuarioRepository.save(usuario1);
        Long id = saved.getId();

        assertEquals("Carlos", saved.getNombre());
        usuario1.setNombre("Carlos Modificado");

        usuarioRepository.update(usuario1);

        Optional<Usuario> result = usuarioRepository.findById(id);

        assertTrue(result.isPresent());
        assertEquals("Carlos Modificado", result.get().getNombre());
    }
}
