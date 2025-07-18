package com.proyecto.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.entity.Propietario;
import com.proyecto.entity.Usuario;
import com.proyecto.repository.PropietarioRepository;
import com.proyecto.repository.UsuarioRepository;
import com.proyecto.service.PropietarioService;
import com.uisrael.gestion_biblioteca.entity.Autor;

@Service
@Transactional
public class PropietarioServiceImpl implements PropietarioService {

	@Autowired
	private PropietarioRepository propietarioRepository;
	
    @Autowired 
    private UsuarioRepository usuarioRepository;

	@Override
	public List<Propietario> findAllPropieatrio() {
		// TODO Auto-generated method stub
		return propietarioRepository.findAll();
	}

	@Override
	public Propietario savePropietario(Propietario propietario) {
		// TODO Auto-generated method stub
		return propietarioRepository.save(propietario);
	}

	@Override
	public void deletePropietario(int id) {
		// TODO Auto-generated method stub
		propietarioRepository.deleteById((long) id);

	}

	@Override
	public Propietario updatePropietario(int id, Propietario detalle) {
		// TODO Auto-generated method stub
		Propietario propietario = propietarioRepository.findById((long) id)
				.orElseThrow(() -> new RuntimeException("Propietario no encontrado con id: " + id));
		// Aquí actualizas los campos que necesites
		propietario.setCedula(detalle.getCedula());
		propietario.setTelefono(detalle.getTelefono());
		// etc...
		return propietarioRepository.save(propietario);
	}

	@Override
	public Propietario findById(Long id) {
		// TODO Auto-generated method stub
		return propietarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Propietario no encontrado con id: " + id));
	}

	@Override
	public Optional<Propietario> obtenerPropietarioPorEmailUsuario(String emailUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario);

        if (usuario != null) {

            return Optional.ofNullable(usuario.getPropietario());
        }
        return Optional.empty(); 
	}

	@Override
	public long contarPropietarios() {
		// TODO Auto-generated method stub
		return propietarioRepository.count();
	}
	
}
