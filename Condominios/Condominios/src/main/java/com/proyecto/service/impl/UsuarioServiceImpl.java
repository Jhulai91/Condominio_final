package com.proyecto.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.entity.Usuario;
import com.proyecto.repository.PropietarioRepository;
import com.proyecto.repository.UsuarioRepository;
import com.proyecto.service.PropietarioService;
import com.proyecto.service.UsuarioService;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService{

	
	@Autowired
    private UsuarioRepository usuarioRepository;
	@Override
	public List<Usuario> findAllUsuario() {
		// TODO Auto-generated method stub
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario saveUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return usuarioRepository.save(usuario);
	}

	@Override
	public void deleteUsuario(int id) {
		// TODO Auto-generated method stub
		usuarioRepository.deleteById(id);
	}

	@Override
	public Usuario updateUsuario(int id, Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> findAllPropietario() {
		// TODO Auto-generated method stub
		return usuarioRepository.findAllPropietarios();
	}

}
