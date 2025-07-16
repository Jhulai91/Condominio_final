package com.proyecto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.entity.Departamento;
import com.proyecto.entity.Edificio;

public interface EdificioRepository extends JpaRepository<Edificio, Integer> {
	Optional<Edificio> findById(Long id);
}
