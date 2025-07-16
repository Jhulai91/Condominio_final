package com.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.entity.Propietario;
import com.proyecto.entity.Usuario;

public interface PropietarioRepository extends JpaRepository<Propietario, 	Long>{

	 @Query("SELECT p FROM Propietario p WHERE p.usuario IS NOT NULL")
	 List<Propietario> findAllConUsuario();
	 Propietario findByCedula(String cedula);
}
