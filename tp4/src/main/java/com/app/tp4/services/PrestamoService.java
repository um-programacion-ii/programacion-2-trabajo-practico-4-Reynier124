package com.app.tp4.services;

import com.app.tp4.model.Libro;
import com.app.tp4.model.Prestamo;
import com.app.tp4.model.Usuario;

import java.util.List;

public interface PrestamoService {
    Prestamo save(Prestamo prestamo);
    Prestamo update(Prestamo prestamo);
    Prestamo buscarPorId(Long id);
    Prestamo buscarPorUsuario(Usuario usuario);
    Prestamo buscarPorLibro(Libro libro);
    List<Prestamo> buscarTodos();
    void delete(Long id);
    boolean existe(Long id);
}
