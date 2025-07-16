package com.proyecto.entity;



import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "propietarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Propietario {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	   @EqualsAndHashCode.Include
    private Long id;
	 @Column(nullable = false,unique = true)
    private String cedula;
	 @Column(nullable = false)
    private String telefono;
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    /*
    @OneToMany(mappedBy = "propietario")
    private List<Departamento> departamentos;
    */
}
