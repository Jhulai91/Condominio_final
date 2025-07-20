package com.proyecto.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    
    // NUEVO MÉTODO: Para obtener alícuotas canceladas por lista de departamentos
    List<Alicuota> findAlicuotasCanceladasByDepartamentos(List<Departamento> departamentos);

    // Opcional: Si quieres obtener todas las alícuotas de una lista de departamentos (pagadas o no)
    List<Alicuota> findAlicuotasByDepartamentos(List<Departamento> departamentos);

    // NUEVO MÉTODO: Para obtener el conteo de alícuotas por estado para los departamentos de un propietario
    Map<String, Long> getAlicuotasCountByStateForDepartamentos(List<Departamento> departamentos);

    // Opcional: Si quieres el valor total por estado
    Map<String, Double> getAlicuotasSumValueByStateForDepartamentos(List<Departamento> departamentos);   
    
    Double obtenerTotalAlicuotas();
    Optional<Alicuota> findAlicuotaById(Integer id); // Asegúrate de tener este método
    
    long contarAlicuotasMesActual();
}
