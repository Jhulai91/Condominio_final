package com.proyecto.entity;
import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private int id;
	 @Column(nullable = false)
	    private String nombre;
	 @Column(nullable = false)
	    private String email;
	 @Column(nullable = false)
	    private String password;
	  @Enumerated(EnumType.STRING)
	  @Column(nullable = false)
	    private Rol rol; // ADMINISTRADOR, PROPIETARIO

	    // Relaciones
	    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
	    private Propietario propietario;
	    
	    // Getters y Setters
	    public Integer getId() { return id; }
	    public void setId(Integer id) { this.id = id; }

	    public String getNombre() { return nombre; }
	    public void setNombre(String nombre) { this.nombre = nombre; }

	    public String getEmail() { return email; }
	    public void setEmail(String email) { this.email = email; }

	    public String getPassword() { return password; }
	    public void setPassword(String password) { this.password = password; }

	    public Rol getRol() { return rol; }
	    public void setRol(Rol rol) { this.rol = rol; }
	
}
