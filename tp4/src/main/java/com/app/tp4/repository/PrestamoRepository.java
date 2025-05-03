package com.app.tp4.repository;

import com.app.tp4.model.Libro;
import com.app.tp4.model.Prestamo;
import com.app.tp4.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface PrestamoRepository {
    Prestamo save(Prestamo prestamo);
    Prestamo update(Prestamo prestamo);
    Optional<Prestamo> findById(Long id);
    Optional<Prestamo> findByLibro(Libro libro);
    Optional<Prestamo> findByUsuario(Usuario usuario);
    List<Prestamo> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
