package com.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.dto.AlicuotaPagoDTO;
import com.proyecto.entity.Alicuota;
import com.proyecto.entity.Departamento;


public interface AlicuotaRepository extends JpaRepository<Alicuota, Integer>{
	 List<Alicuota> findByDepartamento(Departamento departamento);
	 boolean existsByMesAndAnio(int mes, int anio);
	 
	 // Método para encontrar alícuotas por una lista de departamentos
	 List<Alicuota> findByDepartamentoIn(List<Departamento> departamentos);

	 // Método para encontrar alícuotas por una lista de departamentos y que tengan un pago asociado
	 // y el estado de ese pago sea "PAGADO"
	 List<Alicuota> findByDepartamentoInAndPagoEstado(List<Departamento> departamentos, String estadoPago);
	    
	 // NUEVO MÉTODO: Para contar alícuotas por estado (incluyendo las sin pago)
	 @Query("SELECT CASE WHEN a.pago IS NULL THEN 'PENDIENTE' ELSE a.pago.estado END AS estado, COUNT(a) FROM Alicuota a JOIN a.departamento d WHERE d IN :departamentos GROUP BY estado")
	 List<Object[]> countAlicuotasByStateForDepartamentos(@Param("departamentos") List<Departamento> departamentos);

	 // Opcional: Si quieres la suma de valores por estado
	 @Query("SELECT CASE WHEN a.pago IS NULL THEN 'PENDIENTE' ELSE a.pago.estado END AS estado, SUM(a.valorCalculado) FROM Alicuota a JOIN a.departamento d WHERE d IN :departamentos GROUP BY estado")
	 List<Object[]> sumAlicuotasValueByStateForDepartamentos(@Param("departamentos") List<Departamento> departamentos);
	 
	 @Query("SELECT COALESCE(SUM(a.valorCalculado), 0) FROM Alicuota a")
	 Double obtenerTotalAlicuotas();

	 @Query("SELECT new com.proyecto.dto.AlicuotaPagoDTO(" +
		       "d.numero, a.mes, a.anio, a.valorCalculado, " +
		       "p.estado, CAST(p.fechaPago AS string), p.numeroComprobante, " +
		       "u.nombre) " +
		       "FROM Alicuota a " +
		       "JOIN a.departamento d " +
		       "JOIN d.propietario pr " +
		       "JOIN pr.usuario u " +
		       "LEFT JOIN a.pago p " +
		       "ORDER BY a.anio DESC, a.mes DESC")
		List<AlicuotaPagoDTO> listarAlicuotasConPagos();
	 	
	 @Query("SELECT COUNT(a) FROM Alicuota a WHERE a.mes = :mes AND a.anio = :anio")
	    long contarGeneradasEnMes(@Param("mes") int mes, @Param("anio") int anio);

}
