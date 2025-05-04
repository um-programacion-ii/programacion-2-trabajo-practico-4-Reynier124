package com.app.tp4;

import com.app.tp4.controllers.LibroController;
import com.app.tp4.enumeraciones.EstadoLibro;
import com.app.tp4.model.Libro;
import com.app.tp4.services.LibroService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LibroController.class)
public class LibroControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibroService libroService;

    @Autowired
    private ObjectMapper objectMapper;

    private Libro libro;

    @BeforeEach
    void setUp() {
        libro = new Libro(1L, "123456", "Clean Code", "Robert C. Martin", EstadoLibro.DISPONIBLE);
    }

    @Test
    void obtenerTodosLosLibrosTest() throws Exception {
        when(libroService.buscarTodos()).thenReturn(Arrays.asList(libro));

        mockMvc.perform(get("/api/libros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Clean Code"));
    }

    @Test
    void obtenerLibroPorIdTest() throws Exception {
        when(libroService.buscarPorId(1L)).thenReturn(libro);

        mockMvc.perform(get("/api/libros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.autor").value("Robert C. Martin"));
    }

    @Test
    void obtenerLibroPorIdNoExisteTest() throws Exception {
        when(libroService.buscarPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/api/libros/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerLibroPorIsbnTest() throws Exception {
        when(libroService.buscarPorIsbn("123456")).thenReturn(libro);

        mockMvc.perform(get("/api/libros/isbn/123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Clean Code"));
    }

    @Test
    void obtenerLibroPorIsbnNoExisteTest() throws Exception {
        when(libroService.buscarPorIsbn("999")).thenReturn(null);

        mockMvc.perform(get("/api/libros/isbn/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void crearLibroTest() throws Exception {
        when(libroService.save(any(Libro.class))).thenReturn(libro);

        mockMvc.perform(post("/api/libros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(libro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void actualizarLibroTest() throws Exception {
        libro.setTitulo("Clean Architecture");
        when(libroService.update(any(Libro.class))).thenReturn(libro);

        mockMvc.perform(put("/api/libros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(libro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Clean Architecture"));
    }

    @Test
    void eliminarLibroTest() throws Exception {
        doNothing().when(libroService).delete(1L);

        mockMvc.perform(delete("/api/libros/1"))
                .andExpect(status().isNoContent());
    }


}
