package com.proyecto.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyecto.dto.AlicuotaPagoDTO;
import com.proyecto.entity.Alicuota;
import com.proyecto.entity.GastoComunal;
import com.proyecto.entity.Usuario;
import com.proyecto.repository.AlicuotaRepository;
import com.proyecto.repository.GastoComunalRepository;
import com.proyecto.repository.UsuarioRepository;
import com.proyecto.service.AlicuotaService;
import com.proyecto.service.GastoComunalService;

@Controller
public class AlicuotaController {
	@Autowired
	private AlicuotaService alicuotaService;
	@Autowired
	private AlicuotaRepository alicuotaRepository;
	@Autowired
	private GastoComunalRepository gastoComunalRepo;
	@Autowired
	private GastoComunalService gastoComunalService;
	 @Autowired
	    private UsuarioRepository usuarioRepo;
	@GetMapping("/alicuotas/lista")
	public String mostrarFormulario(Model model,  Principal principal) {
		List<Usuario> usuarios = usuarioRepo.findAll();
	    
	        String email = principal.getName(); 
	        Usuario usuario = usuarioRepo.findByEmail(email);
	        model.addAttribute("usuarioAutenticado", usuario);
	    return "alicuotas_lista"; 
	}
	
	@GetMapping("/alicuotas/generar")
	public String verGastosDelMes(@RequestParam int mes, @RequestParam int anio, Model model, Principal principal) {
		
		List<Usuario> usuarios = usuarioRepo.findAll();
	     
	        String email = principal.getName(); 
	        Usuario usuario = usuarioRepo.findByEmail(email);
	        model.addAttribute("usuarioAutenticado", usuario);
		
		
	    List<GastoComunal> gastos = gastoComunalService.findByMesAndAnio(mes, anio);
	    double total = gastos.stream().mapToDouble(GastoComunal::getMonto).sum();
	    double totalGeneral = gastoComunalRepo.obtenerTotalGastos();
	    model.addAttribute("totalGastos", total);
	    model.addAttribute("mes", mes);
	    model.addAttribute("anio", anio);
	   

	    return "alicuotas_lista";
	}
	
	@GetMapping("/alicuotas/reporte")
	public String listarPagosConAlicuotas(Model model, Principal principal) {
		

        String email = principal.getName(); 
        Usuario usuario = usuarioRepo.findByEmail(email);
        model.addAttribute("usuarioAutenticado", usuario);
		 List<AlicuotaPagoDTO> alicuotas = alicuotaRepository.listarAlicuotasConPagos();
		 model.addAttribute("alicuotas", alicuotas);
	    return "alicuotas_reporte";
	}
	
	
	
	@PostMapping("/alicuotas/calcular")
	public String calcularAlicuotas(@RequestParam("mes") int mes,
	                                @RequestParam("anio") int anio,
	                                Model model, Principal principal) {
		
		List<Usuario> usuarios = usuarioRepo.findAll();
	     
	        String email = principal.getName(); 
	        Usuario usuario = usuarioRepo.findByEmail(email);
	        model.addAttribute("usuarioAutenticado", usuario);
		
	        try {
	    List<Alicuota> alicuotas = alicuotaService.calcularAlicuotasPorMesYAnio(mes, anio);
	    model.addAttribute("alicuotas", alicuotas);
	   
	    return "alicuotas_lista";
	        } catch (RuntimeException e) {
	            model.addAttribute("error", e.getMessage());
	            model.addAttribute("mes", mes);
	            model.addAttribute("anio", anio);
	            return "alicuotas_lista"; 
	        }
}
	
	
}