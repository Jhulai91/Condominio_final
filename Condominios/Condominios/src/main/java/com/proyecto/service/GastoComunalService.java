package com.proyecto.service;

import java.util.List;

import com.proyecto.entity.Departamento;
import com.proyecto.entity.Edificio;
import com.proyecto.entity.GastoComunal;

public interface GastoComunalService {

	List<GastoComunal> findAllGasto();
    GastoComunal saveGasto(GastoComunal gastocomunal);
    List<GastoComunal> findByMesAndAnio(int mes, int anio);
    List<GastoComunal> listarPorEdificio(Edificio edificio);
    List<GastoComunal> listarPorEdificioYFecha(Edificio edificio, int mes, int anio);
    GastoComunal findById(Integer id);
    void deleteGasto(int id);
    GastoComunal updateGasto(int id, GastoComunal detalle);
    boolean gastoExiste(GastoComunal gasto);
    Double obtenerTotalGastos();
    //boolean existsByNumeroAndEdificio(String numero,Edificio edificio);
    //Departamento findById(Integer id);
	
}
