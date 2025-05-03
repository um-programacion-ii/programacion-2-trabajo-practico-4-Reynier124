package com.app.tp4.repository;

import com.app.tp4.model.Usuario;

import java.util.*;

public class UsuarioRepositoryImpl implements UsuarioRepository {
    private final Map<Long, Usuario> usuarios = new HashMap<>();
    private Long nextId = 1L;

    @Override
    public Usuario save(Usuario usuario) {
        if (usuario.getId() == null){
            usuario.setId(nextId++);
        }
        usuarios.put(usuario.getId(), usuario);
        return usuario;
    }

    @Override
    public Usuario update(Usuario usuario) {
        deleteById(usuario.getId());
        usuarios.put(usuario.getId(), usuario);
        return usuario;
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return Optional.ofNullable(usuarios.get(id));
    }

    @Override
    public Optional<Usuario> findByNombre(String nombre) {
        return usuarios.values().stream()
                .filter(usuario -> usuario.getNombre().equals(nombre))
                .findFirst();
    }

    @Override
    public List<Usuario> findAll() {
        return new ArrayList<Usuario>(usuarios.values());
    }

    @Override
    public void deleteById(Long id) {
        usuarios.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return usuarios.containsKey(id);
    }
}
