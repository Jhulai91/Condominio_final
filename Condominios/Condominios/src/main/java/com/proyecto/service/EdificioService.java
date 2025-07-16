package com.proyecto.service;

import java.util.List;

import com.proyecto.entity.Edificio;
import com.proyecto.entity.Propietario;

public interface EdificioService {
	List<Edificio> findAllEdificio();
    Edificio saveEdificio(Edificio edificio);
    void deleteEdificio(int id);
    Edificio updateEdificio(int id, Edificio detalle);
    Edificio findById(Integer id);
}
