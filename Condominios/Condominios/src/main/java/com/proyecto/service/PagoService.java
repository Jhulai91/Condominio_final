package com.proyecto.service;

import com.proyecto.entity.Pago;
import java.util.List;
import java.util.Optional;

public interface PagoService {
    List<Pago> findAllPagos();
    Optional<Pago> findPagoById(Integer id);
    Pago savePago(Pago pago);
    void deletePago(Integer id);
    Pago updatePago(Integer id, Pago pagoDetails); // Método para actualizar si es necesario
    Optional<Pago> findByNumeroComprobante(String numeroComprobante); // Método para buscar por comprobante
}