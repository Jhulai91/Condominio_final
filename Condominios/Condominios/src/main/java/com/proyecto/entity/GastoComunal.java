package com.proyecto.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gastos_comunales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GastoComunal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String descripcion;
	@Nonnull
	private double monto;
	@Nonnull
	private LocalDate fecha;
	// Puede agruparse por mes
	@Nonnull
	 private int mes;
	@Nonnull
	 private int anio;
	// RELACIÓN CON EDIFICIO (MUY IMPORTANTE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edificio_id", nullable = false)
    @JsonIgnoreProperties("gastos") 
    private Edificio edificio;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_gasto_id", nullable = false)
    private TipoGasto tipoGasto;

}
