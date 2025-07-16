package com.proyecto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tipo_gasto")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class TipoGasto {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;

	    @Column(nullable = false, unique = true)
	    private String nombre;
	    @Column(nullable = false, unique = true)
	    private String descripcion;
	    @Column(nullable = false)
	    private String estado;
	    
	    
}
