package com.app.tp4.services;

import com.app.tp4.model.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario save(Usuario usuario);
    Usuario buscarPorId(Long id);
    Usuario buscarPorNombre(String nombre);
    List<Usuario> buscarTodos();
    void delete(Long id);
    boolean existe(Long id);
    Usuario update(Usuario usuario);
}
