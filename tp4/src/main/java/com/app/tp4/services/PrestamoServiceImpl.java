package com.app.tp4.services;

import com.app.tp4.model.Libro;
import com.app.tp4.model.Prestamo;
import com.app.tp4.model.Usuario;
import com.app.tp4.repository.PrestamoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestamoServiceImpl implements PrestamoService {
    private PrestamoRepository prestamoRepository;

    public PrestamoServiceImpl(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    @Override
    public Prestamo save(Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }

    @Override
    public Prestamo update(Prestamo prestamo) {
        return prestamoRepository.update(prestamo);
    }

    @Override
    public Prestamo buscarPorId(Long id) {
        return prestamoRepository.findById(id).orElse(null);
    }

    @Override
    public Prestamo buscarPorUsuario(Usuario usuario) {
        return prestamoRepository.findByUsuario(usuario).orElse(null);
    }

    @Override
    public Prestamo buscarPorLibro(Libro libro) {
        return prestamoRepository.findByLibro(libro).orElse(null);
    }

    @Override
    public List<Prestamo> buscarTodos() {
        return prestamoRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        prestamoRepository.deleteById(id);
    }

    @Override
    public boolean existe(Long id) {
        return prestamoRepository.existsById(id);
    }
}
