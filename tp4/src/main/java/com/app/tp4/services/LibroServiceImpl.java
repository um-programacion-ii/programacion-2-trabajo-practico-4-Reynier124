package com.app.tp4.services;

import com.app.tp4.model.Libro;
import com.app.tp4.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroServiceImpl implements LibroService {
    private LibroRepository libroRepository;

    public LibroServiceImpl(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }


    @Override
    public Libro save(Libro libro) {
        return libroRepository.save(libro);
    }

    @Override
    public Libro buscarPorId(Long id) {
        return libroRepository.findById(id).orElse(null);
    }

    @Override
    public Libro buscarPorIsbn(String isbn) {
        return libroRepository.findByIsbn(isbn).orElse(null);
    }

    @Override
    public List<Libro> buscarTodos() {
        return libroRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        libroRepository.deleteById(id);
    }

    @Override
    public boolean existe(Long id) {
        return libroRepository.existsById(id);
    }

    @Override
    public Libro update(Libro libro) {
        return libroRepository.update(libro);
    }
}
