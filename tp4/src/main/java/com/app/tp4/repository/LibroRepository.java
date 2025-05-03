package com.app.tp4.repository;

import com.app.tp4.model.Libro;

import java.util.List;
import java.util.Optional;

public interface LibroRepository {
    Libro save(Libro libro);
    Libro update(Libro libro);
    Optional<Libro> findById(Long id);
    Optional<Libro> findByIsbn(String isbn);
    List<Libro> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
