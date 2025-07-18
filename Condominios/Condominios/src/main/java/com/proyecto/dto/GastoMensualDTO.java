package com.proyecto.dto;

import java.time.Month;
import java.util.Locale;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GastoMensualDTO {
	 private int mes;
	    private int anio;
	    private double total;
	    
	    public String getMesNombre() {
	        // Devuelve el nombre del mes en español y el año, ej: "Enero 2025"
	        return Month.of(mes)
	            .getDisplayName(java.time.format.TextStyle.FULL, new Locale("es"))
	            + " " + anio;
	    }
}
