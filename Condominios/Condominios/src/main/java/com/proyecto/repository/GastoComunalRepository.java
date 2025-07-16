package com.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.entity.Edificio;
import com.proyecto.entity.GastoComunal;

public interface GastoComunalRepository extends JpaRepository<GastoComunal, Integer>  {
	List<GastoComunal> findByEdificio(Edificio edificio);
    List<GastoComunal> findByEdificioAndMesAndAnio(Edificio edificio, int mes, int anio);
    boolean existsByEdificioIdAndMesAndAnioAndTipoGastoId(Integer edificioId, int mes, int anio, Integer tipoGastoId);
    List<GastoComunal> findByMesAndAnio(int mes, int anio);
    @Query("SELECT SUM(g.monto) FROM GastoComunal g")
    Double obtenerTotalGastos();
}
