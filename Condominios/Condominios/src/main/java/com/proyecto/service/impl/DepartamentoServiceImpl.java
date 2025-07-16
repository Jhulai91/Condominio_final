package com.proyecto.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.entity.Departamento;
import com.proyecto.entity.Edificio;
import com.proyecto.entity.Propietario;
import com.proyecto.repository.DepartamentoRepository;
import com.proyecto.repository.PropietarioRepository;
import com.proyecto.service.DepartamentoService;
@Service
@Transactional
public class DepartamentoServiceImpl implements DepartamentoService {

	@Autowired
    private DepartamentoRepository departamentoRepository;
	@Autowired
	private DepartamentoRepository departamentoRepo;
	@Override
	public List<Departamento> findAllDepartamento() {
		// TODO Auto-generated method stub
		return departamentoRepository.findAll();
	}

	
	
	
	
	
	@Override
	public Departamento saveDepartamento(Departamento departamento) {
		// TODO Auto-generated method stub
		 // Obtener todos los departamentos actuales del edificio
	    List<Departamento> departamentosActuales = departamentoRepo.findByEdificio(departamento.getEdificio());

	    // Sumar metros cuadrados existentes
	    double totalMetros = departamentosActuales.stream()
	            .mapToDouble(Departamento::getMetrosCuadrados)
	            .sum();

	    // Sumar el nuevo departamento (que aún no se ha guardado)
	    totalMetros += departamento.getMetrosCuadrados();

	    // Calcular porcentaje para el nuevo
	    double porcentaje = (departamento.getMetrosCuadrados() / totalMetros) * 100;

	    // Asignar al campo
	    departamento.setPorcentajeAlicuota(porcentaje);
		return departamentoRepository.save(departamento);
	}

	@Override
	public void deleteDepartamento(int id) {
		// TODO Auto-generated method stub
		departamentoRepository.deleteById(id);
	}

	@Override
	public Departamento updateDepartamento(int id, Departamento detalle) {
		// TODO Auto-generated method stub
		 Departamento departamento = departamentoRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Departamento no encoentrado :: " + id));
	        
		 // Actualizar los datos
		    departamento.setNumero(detalle.getNumero());
		    departamento.setMetrosCuadrados(detalle.getMetrosCuadrados());
		    departamento.setEdificio(detalle.getEdificio());
		    departamento.setPropietario(detalle.getPropietario());

		    // Guardar primero
		    Departamento actualizado = departamentoRepository.save(departamento);

		    // Recalcular alícuotas del edificio completo
		    recalcularAlicuotas(detalle.getEdificio());

		    return actualizado;
	}






	@Override
	public void guardaryRecalcular(Departamento departamento) {
		// TODO Auto-generated method stub
		 if (existsByNumeroAndEdificio(departamento.getNumero(), departamento.getEdificio())) {
		        throw new RuntimeException("Ya existe un departamento con ese número en el edificio");
		    }
		// Guardar o actualizar el nuevo departamento
	    departamentoRepo.save(departamento);

	    // Recalcular todos los porcentajes del edificio
	    recalcularAlicuotas(departamento.getEdificio());
	}






	@Override
	public void eliminaryRecalcular(int id) {
		// TODO Auto-generated method stub
		Departamento depto = departamentoRepo.findById(id).orElseThrow();
	    Edificio edificio = depto.getEdificio();
	    
	    // Eliminar
	    departamentoRepo.deleteById(id);

	    // Recalcular después de eliminar
	    recalcularAlicuotas(edificio);
	}






	@Override
	public void recalcularAlicuotas(Edificio edificio) {
		// TODO Auto-generated method stub
		 List<Departamento> departamentos = departamentoRepo.findByEdificio(edificio);

		    double totalMetros = departamentos.stream()
		        .mapToDouble(Departamento::getMetrosCuadrados)
		        .sum();

		    for (Departamento d : departamentos) {
		        double porcentaje = (d.getMetrosCuadrados() / totalMetros) * 100;
		        d.setPorcentajeAlicuota(porcentaje);
		    }

		    // Guardar todos
		    departamentoRepo.saveAll(departamentos);
			
	}






	@Override
	public boolean existsByNumeroAndEdificio(String numero, Edificio edificio) {
		// TODO Auto-generated method stub
		return departamentoRepo.existsByNumeroAndEdificio(numero, edificio);
	}






	@Override
	public Departamento findById(Integer id) {
		// TODO Auto-generated method stub
		return departamentoRepo.findById(id).orElse(null);
	}






	
}
