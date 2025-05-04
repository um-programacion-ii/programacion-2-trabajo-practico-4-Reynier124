package com.app.tp4;

import com.app.tp4.controllers.PrestamoController;
import com.app.tp4.enumeraciones.EstadoLibro;
import com.app.tp4.enumeraciones.EstadoUsuario;
import com.app.tp4.model.Libro;
import com.app.tp4.model.Prestamo;
import com.app.tp4.model.Usuario;
import com.app.tp4.services.PrestamoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PrestamoController.class)
public class PrestamoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrestamoService prestamoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Prestamo prestamo;
    private Usuario usuario;
    private Libro libro;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(1L, "Juan Pérez", "pepe@gmail.com", EstadoUsuario.ACTIVO);
        libro = new Libro(1L, "123456", "Clean Code", "Robert C. Martin", EstadoLibro.DISPONIBLE);
        prestamo = new Prestamo(1L, libro, usuario, LocalDate.now(), null);
    }

    @Test
    void obtenerTodosLosPrestamosTest() throws Exception {
        when(prestamoService.buscarTodos()).thenReturn(Arrays.asList(prestamo));

        mockMvc.perform(get("/api/prestamos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].usuario.nombre").value("Juan Pérez"));
    }

    @Test
    void obtenerPrestamoPorIdTest() throws Exception {
        when(prestamoService.buscarPorId(1L)).thenReturn(prestamo);

        mockMvc.perform(get("/api/prestamos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.libro.titulo").value("Clean Code"));
    }

    @Test
    void obtenerPrestamoPorIdNoExisteTest() throws Exception {
        when(prestamoService.buscarPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/api/prestamos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerPrestamoPorUsuarioTest() throws Exception {
        when(prestamoService.buscarPorUsuario(any(Usuario.class))).thenReturn(prestamo);

        mockMvc.perform(get("/api/prestamos/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuario.nombre").value("Juan Pérez"));
    }

    @Test
    void obtenerPrestamoPorUsuarioNoExisteTest() throws Exception {
        when(prestamoService.buscarPorUsuario(any(Usuario.class))).thenReturn(null);

        mockMvc.perform(get("/api/prestamos/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Usuario())))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerPrestamoPorLibroTest() throws Exception {
        when(prestamoService.buscarPorLibro(any(Libro.class))).thenReturn(prestamo);

        mockMvc.perform(get("/api/prestamos/libro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(libro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.libro.titulo").value("Clean Code"));
    }

    @Test
    void obtenerPrestamoPorLibroNoExisteTest() throws Exception {
        when(prestamoService.buscarPorLibro(any(Libro.class))).thenReturn(null);

        mockMvc.perform(get("/api/prestamos/libro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Libro())))
                .andExpect(status().isNotFound());
    }

    @Test
    void crearPrestamoTest() throws Exception {
        when(prestamoService.save(any(Prestamo.class))).thenReturn(prestamo);

        mockMvc.perform(post("/api/prestamos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prestamo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void actualizarPrestamoTest() throws Exception {
        prestamo.setFechaDevolucion(LocalDate.now());
        when(prestamoService.update(any(Prestamo.class))).thenReturn(prestamo);

        mockMvc.perform(put("/api/prestamos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prestamo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fechaDevolucion").exists());
    }

    @Test
    void eliminarPrestamoTest() throws Exception {
        doNothing().when(prestamoService).delete(1L);

        mockMvc.perform(delete("/api/prestamos/1"))
                .andExpect(status().isNoContent());
    }
}
