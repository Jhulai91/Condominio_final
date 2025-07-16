package com.proyecto.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.entity.Edificio;
import com.proyecto.entity.Propietario;
import com.proyecto.repository.EdificioRepository;
import com.proyecto.repository.UsuarioRepository;
import com.proyecto.service.EdificioService;

@Service
@Transactional
public class EdificioServiceImpl implements EdificioService{

	@Autowired
    private EdificioRepository edificioRepository;
	
	
	@Override
	public List<Edificio> findAllEdificio() {
		// TODO Auto-generated method stub
		return edificioRepository.findAll();
	}

	@Override
	public Edificio saveEdificio(Edificio edificio) {
		// TODO Auto-generated method stub
		return edificioRepository.save(edificio);
	}

	@Override
	public void deleteEdificio(int id) {
		// TODO Auto-generated method stub
		edificioRepository.deleteById(id);
	}

	@Override
	public Edificio updateEdificio(int id, Edificio detalle) {
		// TODO Auto-generated method stub
		  Edificio edificio = edificioRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Edificio no encoentrado :: " + id));
	        
	        return edificioRepository.save(edificio);
	}

	@Override
	public Edificio findById(Integer id) {
		// TODO Auto-generated method stub
		  return edificioRepository.findById(id)
			        .orElseThrow(() -> new RuntimeException("Edificio no encontrado con id: " + id));
	}

}
