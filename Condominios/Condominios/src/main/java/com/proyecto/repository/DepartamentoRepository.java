package com.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.entity.Departamento;
import com.proyecto.entity.Edificio;
import com.proyecto.entity.Propietario;


public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {
	List<Departamento> findByEdificio(Edificio edificio);
	boolean existsByNumeroAndEdificio(String numero, Edificio edificio);
	
	List<Departamento> findByPropietario(Propietario propietario);
	 
}
