package com.app.tp4;

import com.app.tp4.enumeraciones.EstadoLibro;
import com.app.tp4.enumeraciones.EstadoUsuario;
import com.app.tp4.model.Libro;
import com.app.tp4.model.Usuario;
import com.app.tp4.repository.LibroRepository;
import com.app.tp4.repository.UsuarioRepository;
import com.app.tp4.services.LibroServiceImpl;
import com.app.tp4.services.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UsuarioServiceTest {
    private Usuario usuario;
    @Mock
    private UsuarioRepository usuarioRepository;
    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        usuario = new Usuario(1L, "Marcos", "mano@gmail.com", EstadoUsuario.ACTIVO);
    }

    @Test
    void guardarUsuario() {
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.save(usuario);

        assertNotNull(resultado);
        assertEquals(usuario.getNombre(), resultado.getNombre());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void ActualizarUsuario() {
        when(usuarioRepository.update(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.update(usuario);

        assertNotNull(resultado);
        assertEquals(usuario.getId(), resultado.getId());
        verify(usuarioRepository).update(usuario);
    }

    @Test
    void EliminarUsuario() {
        Long id = 1L;

        usuarioService.delete(id);

        verify(usuarioRepository).deleteById(id);
    }

    @Test
    void cuandoExisteUsuarioPorId() {
        Long id = 1L;
        when(usuarioRepository.existsById(id)).thenReturn(true);

        boolean existe = usuarioService.existe(id);

        assertTrue(existe);
        verify(usuarioRepository).existsById(id);
    }

    @Test
    void cuandoBuscarPorIdExiste() {
        Long id = 1L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(usuarioRepository).findById(id);
    }

    @Test
    void cuandoBuscarPorIdNoExiste() {
        Long id = 1L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        Usuario resultado = usuarioService.buscarPorId(id);

        assertNull(resultado);
        verify(usuarioRepository).findById(id);
    }

    @Test
    void cuandoBuscarPorNombreExiste(){
        String nombre = "Marcos";
        when(usuarioRepository.findByNombre(nombre)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarPorNombre(nombre);

        assertNotNull(resultado);
        assertEquals(nombre, resultado.getNombre());
        verify(usuarioRepository).findByNombre(nombre);
    }

    @Test
    void cuandoBuscarPorNombreNoExiste() {
        String nombre = "Marcos";
        when(usuarioRepository.findByNombre(nombre)).thenReturn(Optional.empty());

        Usuario resultado = usuarioService.buscarPorNombre(nombre);

        assertNull(resultado);
        verify(usuarioRepository).findByNombre(nombre);
    }

}
