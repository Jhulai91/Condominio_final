package com.proyecto.service.impl;

import com.proyecto.entity.Pago;
import com.proyecto.repository.PagoRepository;
import com.proyecto.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;

    @Autowired
    public PagoServiceImpl(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    @Override
    public List<Pago> findAllPagos() {
        return pagoRepository.findAll();
    }

    @Override
    public Optional<Pago> findPagoById(Integer id) {
        return pagoRepository.findById(id);
    }

    @Override
    public Pago savePago(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    public void deletePago(Integer id) {
        pagoRepository.deleteById(id);
    }

    @Override
    public Pago updatePago(Integer id, Pago pagoDetails) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado para este id :: " + id)); // Manejo de excepción básico

        pago.setFechaPago(pagoDetails.getFechaPago());
        pago.setNumeroComprobante(pagoDetails.getNumeroComprobante());
        pago.setObservaciones(pagoDetails.getObservaciones());
        // El estado y la alícuota NO se actualizan desde aquí si el propietario no los edita
        // pago.setEstado(pagoDetails.getEstado()); // Solo si un admin lo actualiza
        // pago.setAlicuota(pagoDetails.getAlicuota()); // Solo si se permite cambiar la alícuota
        // pago.setComprobantePdf(pagoDetails.getComprobantePdf()); // Si se permite actualizar el PDF

        return pagoRepository.save(pago);
    }

    @Override
    public Optional<Pago> findByNumeroComprobante(String numeroComprobante) {
        return pagoRepository.findByNumeroComprobante(numeroComprobante);
    }

	@Override
	public long contarPagados() {
		// TODO Auto-generated method stub
		 return pagoRepository.countByEstado("PAGADO");
	}

	@Override
	public long contarTodosConPago() {
		// TODO Auto-generated method stub
		return pagoRepository.count();
	}
}