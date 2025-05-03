package com.app.tp4.model;

import com.app.tp4.enumeraciones.EstadoUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private long id;
    private String nombre;
    private String email;
    private EstadoUsuario estado;
}
