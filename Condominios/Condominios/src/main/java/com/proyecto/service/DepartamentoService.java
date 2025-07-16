package com.proyecto.service;

import java.util.List;

import com.proyecto.entity.Departamento;
import com.proyecto.entity.Edificio;
import com.proyecto.entity.Propietario;

public interface DepartamentoService {

	List<Departamento> findAllDepartamento();
    Departamento saveDepartamento(Departamento departamento);
    void guardaryRecalcular(Departamento departamento);
    void eliminaryRecalcular(int id);
    void recalcularAlicuotas(Edificio edificio);
    void deleteDepartamento(int id);
    Departamento updateDepartamento(int id, Departamento detalle);
    boolean existsByNumeroAndEdificio(String numero,Edificio edificio);
    Departamento findById(Integer id);
}
