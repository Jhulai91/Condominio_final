package com.proyecto.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.proyecto.entity.Pago;
import com.proyecto.entity.Usuario;
import com.proyecto.repository.PagoRepository;
import com.proyecto.repository.UsuarioRepository;
@Controller
public class PagoController {

	 @Autowired
	    private PagoRepository pagoRepository;
	 @Autowired
	    private UsuarioRepository usuarioRepo;
	 @GetMapping("/pagos/lista")
	    public String listarPagos(Model model, Principal principal) {
		 
		  String email = principal.getName(); 
	        Usuario usuario = usuarioRepo.findByEmail(email);
	        model.addAttribute("usuarioAutenticado", usuario);
	        List<Pago> pagos = pagoRepository.findAll();
	        model.addAttribute("pagos", pagos);
	        return "pagos_lista"; 
	    }
	 
	 @GetMapping("/pagos/aprobar/{id}")
	 public String aprobarPago(@PathVariable Integer id) {
	     pagoRepository.findById(id).ifPresent(pago -> {
	         pago.setEstado("PAGADO");
	         pagoRepository.save(pago);
	     });
	     return "redirect:/pagos/lista"; // redirige a la lista actualizada
	 }
	 
	 
	 @GetMapping("/pagos/pdf/{id}")
	 public ResponseEntity<byte[]> verPdf(@PathVariable Integer id) {
		  Optional<Pago> pagoOpt = pagoRepository.findById(id);
		    if (pagoOpt.isPresent()) {
		        byte[] pdfBytes = pagoOpt.get().getComprobantePdf();
		        System.out.println("PDF tamaño: " + (pdfBytes != null ? pdfBytes.length : "null")); // DEBUG

		        if (pdfBytes != null && pdfBytes.length > 0) {
		            HttpHeaders headers = new HttpHeaders();
		            headers.setContentType(MediaType.APPLICATION_PDF);
		            headers.setContentDisposition(ContentDisposition.inline().filename("comprobante.pdf").build());
		            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
		        }
		    }

		    return ResponseEntity.notFound().build();
	 }
	 
	 
}
