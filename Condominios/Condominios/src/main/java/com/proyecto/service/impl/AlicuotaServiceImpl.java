package com.proyecto.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.entity.Alicuota;
import com.proyecto.entity.Departamento;
import com.proyecto.entity.GastoComunal;
import com.proyecto.repository.AlicuotaRepository;
import com.proyecto.repository.DepartamentoRepository;
import com.proyecto.repository.GastoComunalRepository;
import com.proyecto.service.AlicuotaService;
@Service
@Transactional
public class AlicuotaServiceImpl implements AlicuotaService{

	
	@Autowired
    private AlicuotaRepository alicuotaRepo;
	@Autowired
	private DepartamentoRepository departamentoRepo;
	@Autowired
	private GastoComunalRepository gastoRepo;
	@Override
	public List<Alicuota> findAllAlicuota() {
		// TODO Auto-generated method stub
		return alicuotaRepo.findAll();
	}

	@Override
	public Alicuota saveAlicuota(Alicuota alicuota) {
		// TODO Auto-generated method stub
		return alicuotaRepo.save(alicuota);
	}

	@Override
	public Alicuota findById(Integer id) {
		// TODO Auto-generated method stub
		  return alicuotaRepo.findById(id).orElseThrow(() -> new RuntimeException("Alicuota no encontrada"));
	}

	@Override
	public void deleteAlicuota(int id) {
		// TODO Auto-generated method stub
		 alicuotaRepo.deleteById(id);
	}

	@Override
	public Alicuota updateAlicuota(int id, Alicuota detalle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Alicuota> listarPorDepartamento(Departamento departamento) {
		// TODO Auto-generated method stub
		return alicuotaRepo.findByDepartamento(departamento);
	}

	@Override
	public List<Alicuota> calcularAlicuotasPorMesYAnio(int mes, int anio) {
		// TODO Auto-generated method stub
		
		if (alicuotaRepo.existsByMesAndAnio(mes, anio)) {
	        throw new RuntimeException("Las alícuotas de ese mes ya han sido generadas.");
	    }
		double totalGastos = gastoRepo.findByMesAndAnio(mes, anio)
                .stream()
                .mapToDouble(GastoComunal::getMonto)
                .sum();

List<Departamento> departamentos = departamentoRepo.findAll();
List<Alicuota> alicuotas = new ArrayList<>();

for (Departamento dep : departamentos) {
double porcentaje = dep.getPorcentajeAlicuota();
double valor = (porcentaje / 100.0) * totalGastos;

Alicuota a = new Alicuota();
a.setMes(mes);
a.setAnio(anio);
a.setValorCalculado(valor);
a.setDepartamento(dep);

alicuotaRepo.save(a);
alicuotas.add(a);
}

return alicuotas;
	}

}
