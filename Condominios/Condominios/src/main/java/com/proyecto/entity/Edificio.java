package com.proyecto.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "edificios")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Edificio {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;

	    @Column(nullable = false)
	    private String nombre;

	    @Column(nullable = false)
	    private String direccion;

	    @Column(nullable = false)
	    private int numeroDepartamentos;
	    
	    @OneToMany(mappedBy = "edificio", cascade = CascadeType.ALL, orphanRemoval = true)
	    @JsonIgnore
	    @ToString.Exclude
	    private List<Departamento> departamentos;
	    
	    @OneToMany(mappedBy = "edificio", cascade = CascadeType.ALL, orphanRemoval = true)
	    @JsonIgnore // evita recursividad infinita
	    @ToString.Exclude // evita loop en toString
	    private List<GastoComunal> gastos;
	    // Relaciones (opcional)
	    // Por ejemplo, un edificio podría tener muchos propietarios o departamentos
	    // @OneToMany(mappedBy = "edificio")
	    // private List<Departamento> departamentos;
	
	
}
