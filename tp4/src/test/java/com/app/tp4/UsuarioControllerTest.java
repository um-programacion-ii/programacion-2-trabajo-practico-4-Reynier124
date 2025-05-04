package com.app.tp4;

import com.app.tp4.controllers.UsuarioController;
import com.app.tp4.enumeraciones.EstadoUsuario;
import com.app.tp4.model.Usuario;
import com.app.tp4.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(1L, "Juan Pérez", "pepe@gmail.com", EstadoUsuario.ACTIVO);
    }

    @Test
    void obtenerTodosLosUsuariosTest() throws Exception {
        when(usuarioService.buscarTodos()).thenReturn(Arrays.asList(usuario));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan Pérez"));
    }

    @Test
    void obtenerUsuarioPorIdTest() throws Exception {
        when(usuarioService.buscarPorId(1L)).thenReturn(usuario);

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"));
    }

    @Test
    void obtenerUsuarioPorIdNoExisteTest() throws Exception {
        when(usuarioService.buscarPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/api/usuarios/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerUsuarioPorNombreTest() throws Exception {
        when(usuarioService.buscarPorNombre("Juan Pérez")).thenReturn(usuario);

        mockMvc.perform(get("/api/usuarios/nombre/Juan Pérez"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void obtenerUsuarioPorNombreNoExisteTest() throws Exception {
        when(usuarioService.buscarPorNombre("Inexistente")).thenReturn(null);

        mockMvc.perform(get("/api/usuarios/nombre/Inexistente"))
                .andExpect(status().isNotFound());
    }

    @Test
    void crearUsuarioTest() throws Exception {
        when(usuarioService.save(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void actualizarUsuarioTest() throws Exception {
        usuario.setNombre("Juan Actualizado");
        when(usuarioService.update(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(put("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Actualizado"));
    }

    @Test
    void eliminarUsuarioTest() throws Exception {
        doNothing().when(usuarioService).delete(1L);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());
    }
}
