package com.proyecto.entity;

import java.time.LocalDate;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alicuotas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alicuota {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Nonnull
    private int mes;
	@Nonnull
    private int anio;
	@Nonnull
    private double valorCalculado;

    @ManyToOne
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    @OneToOne(mappedBy = "alicuota", cascade = CascadeType.ALL)
    private Pago pago;

}
