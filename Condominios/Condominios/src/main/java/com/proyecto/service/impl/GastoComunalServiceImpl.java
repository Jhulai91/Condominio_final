package com.proyecto.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.entity.Edificio;
import com.proyecto.entity.GastoComunal;
import com.proyecto.repository.GastoComunalRepository;
import com.proyecto.service.DepartamentoService;
import com.proyecto.service.GastoComunalService;

@Service
@Transactional
public class GastoComunalServiceImpl implements GastoComunalService {

	
	 @Autowired
	    private GastoComunalRepository gastoRepo;
	@Override
	public List<GastoComunal> findAllGasto() {
		// TODO Auto-generated method stub
		return gastoRepo.findAll();
	}

	@Override
	public GastoComunal saveGasto(GastoComunal gastocomunal) {
		// TODO Auto-generated method stub
		  return gastoRepo.save(gastocomunal);
	}
	@Override
	public void deleteGasto(int id) {
		// TODO Auto-generated method stub
		gastoRepo.deleteById(id);
	}

	@Override
	public GastoComunal updateGasto(int id, GastoComunal detalle) {
		// TODO Auto-generated method stub
		GastoComunal existente = gastoRepo.findById(id)
		        .orElseThrow(() -> new RuntimeException("Gasto no encontrado"));

		    existente.setFecha(detalle.getFecha());
		    existente.setMes(detalle.getMes());
		    existente.setAnio(detalle.getAnio());
		    existente.setMonto(detalle.getMonto());
		    existente.setTipoGasto(detalle.getTipoGasto());
		    existente.setDescripcion(detalle.getDescripcion());
		    existente.setEdificio(detalle.getEdificio());

		    return gastoRepo.save(existente); // actualiza el registro
	}

	@Override
	public List<GastoComunal> listarPorEdificio(Edificio edificio) {
		// TODO Auto-generated method stub
		 return gastoRepo.findByEdificio(edificio);
	}

	@Override
	public List<GastoComunal> listarPorEdificioYFecha(Edificio edificio, int mes, int anio) {
		// TODO Auto-generated method stub
		 return gastoRepo.findByEdificioAndMesAndAnio(edificio, mes, anio);
	}

	@Override
	public GastoComunal findById(Integer id) {
		// TODO Auto-generated method stub
		 return gastoRepo.findById(id).orElseThrow(() -> new RuntimeException("Gasto no encontrado"));
	}

	@Override
	public boolean gastoExiste(GastoComunal gasto) {
		// TODO Auto-generated method stub
		 return gastoRepo.existsByEdificioIdAndMesAndAnioAndTipoGastoId(
			        gasto.getEdificio().getId(),
			        gasto.getMes(),
			        gasto.getAnio(),
			        gasto.getTipoGasto().getId()
			    );
	}

	@Override
	public List<GastoComunal> findByMesAndAnio(int mes, int anio) {
		// TODO Auto-generated method stub
		 return gastoRepo.findByMesAndAnio(mes, anio);
	}

	@Override
	public Double obtenerTotalGastos() {
		// TODO Auto-generated method stub
		return gastoRepo.obtenerTotalGastos();
	}

}
