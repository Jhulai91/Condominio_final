package com.proyecto.service;

import java.util.List;

import com.proyecto.entity.Alicuota;
import com.proyecto.entity.Departamento;
import com.proyecto.entity.Edificio;
import com.proyecto.entity.GastoComunal;

public interface AlicuotaService {
	List<Alicuota> findAllAlicuota();
    Alicuota saveAlicuota(Alicuota alicuota);
    List<Alicuota> listarPorDepartamento(Departamento departamento);
    //List<GastoComunal> listarPorEdificio(Edificio edificio);
    //List<GastoComunal> listarPorEdificioYFecha(Edificio edificio, int mes, int anio);
    Alicuota findById(Integer id);
    void deleteAlicuota(int id);
    Alicuota updateAlicuota(int id, Alicuota detalle);
    //boolean gastoExiste(GastoComunal gasto);
    List<Alicuota> calcularAlicuotasPorMesYAnio(int mes, int anio);
}
