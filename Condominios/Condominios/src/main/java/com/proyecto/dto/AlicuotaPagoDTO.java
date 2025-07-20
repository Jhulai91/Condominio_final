package com.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
public class AlicuotaPagoDTO {
	
	 private String numeroDepartamento;
	    private int mes;
	    private int anio;
	    private double valorCalculado;
	    private String estadoPago;
	    private String fechaPago;
	    private String numeroComprobante;
	    private String propietario;  // Nombre completo del propietario

	    public AlicuotaPagoDTO(String numeroDepartamento, int mes, int anio, double valorCalculado,
	                           String estadoPago, String fechaPago, String numeroComprobante, String propietario) {
	        this.numeroDepartamento = numeroDepartamento;
	        this.mes = mes;
	        this.anio = anio;
	        this.valorCalculado = valorCalculado;
	        this.estadoPago = estadoPago;
	        this.fechaPago = fechaPago;
	        this.numeroComprobante = numeroComprobante;
	        this.propietario = propietario;
	    }

	    // Getters y setters (puedes generar con Lombok o IDE)

	    public String getNumeroDepartamento() {
	        return numeroDepartamento;
	    }

	    public void setNumeroDepartamento(String numeroDepartamento) {
	        this.numeroDepartamento = numeroDepartamento;
	    }

	    public int getMes() {
	        return mes;
	    }

	    public void setMes(int mes) {
	        this.mes = mes;
	    }

	    public int getAnio() {
	        return anio;
	    }

	    public void setAnio(int anio) {
	        this.anio = anio;
	    }

	    public double getValorCalculado() {
	        return valorCalculado;
	    }

	    public void setValorCalculado(double valorCalculado) {
	        this.valorCalculado = valorCalculado;
	    }

	    public String getEstadoPago() {
	        return estadoPago;
	    }

	    public void setEstadoPago(String estadoPago) {
	        this.estadoPago = estadoPago;
	    }

	    public String getFechaPago() {
	        return fechaPago;
	    }

	    public void setFechaPago(String fechaPago) {
	        this.fechaPago = fechaPago;
	    }

	    public String getNumeroComprobante() {
	        return numeroComprobante;
	    }

	    public void setNumeroComprobante(String numeroComprobante) {
	        this.numeroComprobante = numeroComprobante;
	    }

	    public String getPropietario() {
	        return propietario;
	    }

	    public void setPropietario(String propietario) {
	        this.propietario = propietario;
	    }
}
