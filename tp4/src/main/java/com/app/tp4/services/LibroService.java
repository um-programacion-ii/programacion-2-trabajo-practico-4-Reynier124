package com.app.tp4.services;

import com.app.tp4.model.Libro;

import java.util.List;

public interface LibroService {
    Libro save(Libro libro);
    Libro buscarPorId(Long id);
    Libro buscarPorIsbn(String isbn);
    List<Libro> buscarTodos();
    void delete(Long id);
    boolean existe(Long id);
    Libro update(Libro libro);

}
