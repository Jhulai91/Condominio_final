package com.proyecto.service;

import java.util.List;

import com.proyecto.entity.Propietario;
import com.proyecto.entity.Usuario;

public interface UsuarioService {
	List<Usuario> findAllUsuario();
	List<Usuario> findAllPropietario();
    Usuario saveUsuario(Usuario usuario);
    void deleteUsuario(int id);
    Usuario updateUsuario(int id, Usuario usuario);
}
