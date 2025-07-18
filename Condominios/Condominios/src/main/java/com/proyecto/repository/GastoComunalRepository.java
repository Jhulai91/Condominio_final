package com.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.dto.GastoMensualDTO;
import com.proyecto.entity.Edificio;
import com.proyecto.entity.GastoComunal;

public interface GastoComunalRepository extends JpaRepository<GastoComunal, Integer>  {
	List<GastoComunal> findByEdificio(Edificio edificio);
    List<GastoComunal> findByEdificioAndMesAndAnio(Edificio edificio, int mes, int anio);
    boolean existsByEdificioIdAndMesAndAnioAndTipoGastoId(Integer edificioId, int mes, int anio, Integer tipoGastoId);
    List<GastoComunal> findByMesAndAnio(int mes, int anio);
    @Query("SELECT SUM(g.monto) FROM GastoComunal g")
    Double obtenerTotalGastos();
    @Query("SELECT new com.proyecto.dto.GastoMensualDTO(MONTH(g.fecha), YEAR(g.fecha), SUM(g.monto)) " +
    	       "FROM GastoComunal g GROUP BY YEAR(g.fecha), MONTH(g.fecha) ORDER BY YEAR(g.fecha), MONTH(g.fecha)")
    	List<GastoMensualDTO> obtenerGastosPorMes();
}
