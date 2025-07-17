package com.proyecto.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
public class AlicuotaServiceImpl implements AlicuotaService {

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
		double totalGastos = gastoRepo.findByMesAndAnio(mes, anio).stream().mapToDouble(GastoComunal::getMonto).sum();

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

	@Override
	public List<Alicuota> findAlicuotasCanceladasByDepartamentos(List<Departamento> departamentos) {
		return alicuotaRepo.findByDepartamentoInAndPagoEstado(departamentos, "PAGADO");
	}

	@Override
	public List<Alicuota> findAlicuotasByDepartamentos(List<Departamento> departamentos) {
		return alicuotaRepo.findByDepartamentoIn(departamentos);
	}

	@Override
	public Map<String, Long> getAlicuotasCountByStateForDepartamentos(List<Departamento> departamentos) {
		List<Object[]> results = alicuotaRepo.countAlicuotasByStateForDepartamentos(departamentos);
		Map<String, Long> countMap = new HashMap<>();
		// Inicializar con 0 para asegurar que todos los estados estén presentes
		countMap.put("PAGADO", 0L);
		countMap.put("PENDIENTE", 0L);
		countMap.put("VENCIDO", 0L);

		for (Object[] result : results) {
			String estado = (String) result[0];
			Long count = (Long) result[1];
			countMap.put(estado, count);
		}
		return countMap;
	}

	@Override
	public Map<String, Double> getAlicuotasSumValueByStateForDepartamentos(List<Departamento> departamentos) {
		List<Object[]> results = alicuotaRepo.sumAlicuotasValueByStateForDepartamentos(departamentos);
		Map<String, Double> sumMap = new HashMap<>();
		// Inicializar con 0.0 para asegurar que todos los estados estén presentes
		sumMap.put("PAGADO", 0.0);
		sumMap.put("PENDIENTE", 0.0);
		sumMap.put("VENCIDO", 0.0);

		for (Object[] result : results) {
			String estado = (String) result[0];
			Double sum = (Double) result[1];
			sumMap.put(estado, sum);
		}
		return sumMap;
	}

	@Override
	public Optional<Alicuota> findAlicuotaById(Integer id) {
		return alicuotaRepo.findById(id);
	}

}
