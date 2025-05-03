package com.app.tp4.services;

import com.app.tp4.model.Usuario;
import com.app.tp4.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre).orElse(null);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public boolean existe(Long id) {
        return usuarioRepository.existsById(id);
    }

    @Override
    public Usuario update(Usuario usuario) {
        return usuarioRepository.update(usuario);
    }
}
