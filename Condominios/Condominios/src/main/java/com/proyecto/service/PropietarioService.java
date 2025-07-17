package com.proyecto.service;

import java.util.List;
import java.util.Optional;

import com.proyecto.entity.Propietario;


public interface PropietarioService {
	List<Propietario> findAllPropieatrio();
    Propietario savePropietario(Propietario propietario);
    void deletePropietario(int id);
    Propietario updatePropietario(int id, Propietario detalle);
   // Propietario calcularNotaFinal(Propietario propietario);
    Propietario findById(Long id);
    
    Optional<Propietario> obtenerPropietarioPorEmailUsuario(String emailUsuario);
    
}
