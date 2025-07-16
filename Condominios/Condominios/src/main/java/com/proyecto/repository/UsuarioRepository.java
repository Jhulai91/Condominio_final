package com.proyecto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.proyecto.entity.Usuario;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	Usuario findByEmail(String email);
	
	@Query("SELECT u FROM Usuario u JOIN FETCH u.propietario WHERE u.rol = 'PROPIETARIO'")
	List<Usuario> findAllPropietarios();

 //Usuario findByEmail(String email);
}
