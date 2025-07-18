package com.proyecto.repository;

import com.proyecto.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {

    Optional<Pago> findByNumeroComprobante(String numeroComprobante);
}