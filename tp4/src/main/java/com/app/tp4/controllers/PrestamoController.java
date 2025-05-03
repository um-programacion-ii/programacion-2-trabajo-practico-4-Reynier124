package com.app.tp4.controllers;

import com.app.tp4.model.Libro;
import com.app.tp4.model.Prestamo;
import com.app.tp4.model.Usuario;
import com.app.tp4.services.PrestamoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        return prestamoService.buscarPorId(id);
    }

    @GetMapping("/usuario")
    public Prestamo obtenerPorUsuario(@RequestBody Usuario usuario) {
        return prestamoService.buscarPorUsuario(usuario);
    }

    @GetMapping("/libro")
    public Prestamo obtenerPorLibro(@RequestBody Libro libro) {
        return prestamoService.buscarPorLibro(libro);
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
