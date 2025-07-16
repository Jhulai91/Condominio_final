package com.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.entity.Alicuota;
import com.proyecto.entity.Departamento;


public interface AlicuotaRepository extends JpaRepository<Alicuota, Integer>{
	 List<Alicuota> findByDepartamento(Departamento departamento);
	 boolean existsByMesAndAnio(int mes, int anio);
}
