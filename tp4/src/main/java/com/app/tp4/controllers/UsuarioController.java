package com.app.tp4.controllers;

import com.app.tp4.model.Usuario;
import com.app.tp4.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> obtenerUsuarios() {
        return usuarioService.buscarTodos();
    }

    @GetMapping("/{id}")
    public Usuario obtenerPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return usuario;
    }

    @GetMapping("/nombre/{nombre}")
    public Usuario obtenerPorNombre(@PathVariable String nombre) {
        Usuario usuario = usuarioService.buscarPorNombre(nombre);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return usuario;
    }

    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario libro){
        return usuarioService.save(libro);
    }

    @PutMapping
    public Usuario actualizarUsuario(@RequestBody Usuario libro){
        return usuarioService.update(libro);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.delete(id);
    }
}
