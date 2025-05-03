package com.app.tp4.controllers;

import com.app.tp4.model.Libro;
import com.app.tp4.services.LibroService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {
    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public List<Libro> obtenerLibros() {
        return libroService.buscarTodos();
    }

    @GetMapping("/{id}")
    public Libro obtenerPorId(@PathVariable Long id) {
        return libroService.buscarPorId(id);
    }

    @GetMapping("/isbn/{isbn}")
    public Libro obtenerPorIsbn(@PathVariable String isbn) {
        return libroService.buscarPorIsbn(isbn);
    }

    @PostMapping
    public Libro crearLibro(@RequestBody Libro libro){
        return libroService.save(libro);
    }

    @PutMapping
    public Libro actualizarLibro(@RequestBody Libro libro){
        return libroService.update(libro);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarLibro(@PathVariable Long id) {
        libroService.delete(id);
    }
}
