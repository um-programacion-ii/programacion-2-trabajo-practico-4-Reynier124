package com.app.tp4.controllers;

import com.app.tp4.model.Usuario;
import com.app.tp4.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> obtenerLibros() {
        return usuarioService.buscarTodos();
    }

    @GetMapping("/{id}")
    public Usuario obtenerPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @GetMapping("/nombre/{nombre}")
    public Usuario obtenerPorNombre(@PathVariable String nombre) {
        return usuarioService.buscarPorNombre(nombre);
    }

    @PostMapping
    public Usuario crearLibro(@RequestBody Usuario libro){
        return usuarioService.save(libro);
    }

    @PutMapping
    public Usuario actualizarLibro(@RequestBody Usuario libro){
        return usuarioService.update(libro);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarLibro(@PathVariable Long id) {
        usuarioService.delete(id);
    }
}
