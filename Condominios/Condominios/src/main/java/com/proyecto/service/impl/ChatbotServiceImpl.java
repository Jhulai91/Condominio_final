package com.proyecto.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.service.AlicuotaService;
import com.proyecto.service.ChatbotService;
import com.proyecto.service.DepartamentoService;
import com.proyecto.service.GastoComunalService;
import com.proyecto.service.PagoService;
import com.proyecto.service.PropietarioService;

@Service
@Transactional
public class ChatbotServiceImpl implements ChatbotService {

	  	@Autowired
	    private PagoService pagoService;

	    @Autowired
	    private GastoComunalService gastoService;

	    @Autowired
	    private PropietarioService propietarioService;

	    @Autowired
	    private DepartamentoService departamentoService;

	    @Autowired
	    private AlicuotaService alicuotaService;
	
	@Override
	public String responder(String pregunta) {
		// TODO Auto-generated method stub
		String lower = pregunta.toLowerCase();

	    if (lower.contains("alicuotas pagadas")) {
	        long pagadas = pagoService.contarPagados();
	        return "Se han pagado " + pagadas + " alícuotas hasta la fecha.";
	    }

	    if (lower.contains("alicuotas mes") || lower.contains("alicuotas generadas")) {
	        long totalMes = alicuotaService.contarAlicuotasMesActual();
	        return "Se han generado " + totalMes + " alícuotas en el mes actual.";
	    }

	    if (lower.contains("gastos") && lower.contains("total")) {
	        Double totalGastos = gastoService.obtenerTotalGastos();
	        return "El total de gastos comunales es $" + String.format("%.2f", totalGastos);
	    }

	    if (lower.contains("departamentos")) {
	        long totalDep = departamentoService.contarDepartamentos();
	        return "Hay " + totalDep + " departamentos registrados en el sistema.";
	    }

	    if (lower.contains("propietarios")) {
	        long totalProp = propietarioService.contarPropietarios();
	        return "Hay " + totalProp + " propietarios registrados.";
	    }

	    // 🔁 NUEVAS RESPUESTAS CON REDIRECCIÓN HTML
	    if (lower.contains("como") && lower.contains("pago")) {
	        return "Para aprobar o gestionar pagos, haz clic <a href='/pagos/lista'>aquí</a>.";
	    }

	    if (lower.contains("como") && (lower.contains("registrar") || lower.contains("registro")) && lower.contains("gasto")) {
	        return "Para registrar un gasto comunal, haz clic <a href='/gastos/lista'>aquí</a>.";
	    }

	    if (lower.contains("como") && (lower.contains("agregar") || lower.contains("crear")) && lower.contains("departamento")) {
	        return "Para agregar un nuevo departamento, visita <a href='/departamento/lista'>esta página</a>.";
	    }

	    if (lower.contains("ayuda") || lower.contains("opciones")) {
	        return "Puedes preguntar cosas como:<br>" +
	               "- ¿Cuántas alícuotas han sido pagadas?<br>" +
	               "- ¿Cuál es el total de gastos?<br>" +
	               "- ¿Cómo apruebo un pago?<br>" +
	               "- ¿Cómo registro un gasto?<br>" +
	               "- ¿Cómo agrego un departamento?<br>";
	    }

	    return "Lo siento, no entendí tu pregunta. Prueba con preguntas como:<br>" +
	           "- ¿Cómo registro un gasto?<br>" +
	           "- ¿Cómo apruebo un pago?<br>" +
	           "- ¿Cuántos departamentos hay?<br>" +
	           "- ¿Cuántos propietarios hay?<br>";
	}
	

}
