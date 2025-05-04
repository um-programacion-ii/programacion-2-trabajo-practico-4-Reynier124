package com.app.tp4.controllers;

import com.app.tp4.model.Libro;
import com.app.tp4.model.Prestamo;
import com.app.tp4.model.Usuario;
import com.app.tp4.services.PrestamoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {
    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @GetMapping
    public List<Prestamo> obtenerPrestamos() {
        return prestamoService.buscarTodos();
    }

    @GetMapping("/{id}")
    public Prestamo obtenerPorId(@PathVariable Long id) {
        Prestamo prestamo = prestamoService.buscarPorId(id);
        if (prestamo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Prestamo no encontrado");
        }
        return prestamo;
    }

    @GetMapping("/usuario")
    public Prestamo obtenerPorUsuario(@RequestBody Usuario usuario) {
        Prestamo prestamo = prestamoService.buscarPorUsuario(usuario);
        if (prestamo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Prestamo no encontrado");
        }
        return prestamo;
    }

    @GetMapping("/libro")
    public Prestamo obtenerPorLibro(@RequestBody Libro libro) {
        Prestamo prestamo = prestamoService.buscarPorLibro(libro);
        if (prestamo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Prestamo no encontrado");
        }
        return prestamo;
    }

    @PostMapping
    public Prestamo crearPrestamo(@RequestBody Prestamo prestamo){
        return prestamoService.save(prestamo);
    }

    @PutMapping
    public Prestamo actualizarPrestamo(@RequestBody Prestamo prestamo){
        return prestamoService.update(prestamo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarPrestamo(@PathVariable Long id) {
        prestamoService.delete(id);
    }
}
