package com.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.proyecto.dto.GastoMensualDTO;
import com.proyecto.entity.Usuario;
import com.proyecto.repository.GastoComunalRepository;
import com.proyecto.repository.UsuarioRepository;
import com.proyecto.service.AlicuotaService;
import com.proyecto.service.DepartamentoService;
import com.proyecto.service.GastoComunalService;
import com.proyecto.service.PropietarioService;


@Controller
public class AdminController {
	
	 @Autowired
	    private GastoComunalService gastoService;
	 @Autowired
	    private DepartamentoService departamentoService;
	 @Autowired
	    private PropietarioService propietarioService;
	 @Autowired
	    private GastoComunalRepository gastoRepo;

	 @Autowired
	    private AlicuotaService alicuotaService;
	 @Autowired
	 private final UsuarioRepository usuarioRepo;
	 
	 public AdminController(UsuarioRepository usuarioRepo) {
	        this.usuarioRepo = usuarioRepo;
	    }
	
	  @ModelAttribute
	    public void agregarUsuarioAutenticado(Model model, @AuthenticationPrincipal UserDetails userDetails) {
	        if (userDetails != null) {
	            Usuario usuario = usuarioRepo.findByEmail(userDetails.getUsername());
	            model.addAttribute("usuarioAutenticado", usuario);
	        }
	    }
	 
	 
	
	 @GetMapping("/admin/home")
	    public String adminHome(Model model) {
		 
		 List<GastoMensualDTO> gastosMensuales = gastoRepo.obtenerGastosPorMes();

		 List<String> etiquetas = gastosMensuales.stream()
				    .map(GastoMensualDTO::getMesNombre)
				    .toList();

		    List<Double> valores = gastosMensuales.stream()
		        .map(GastoMensualDTO::getTotal)
		        .toList();

		    model.addAttribute("etiquetasGastos", etiquetas);
		    model.addAttribute("valoresGastos", valores);
		 
		 Double totalAliquotas = alicuotaService.obtenerTotalAlicuotas();
		 model.addAttribute("totalAliquotas", totalAliquotas);
		 
		 Double total = gastoService.obtenerTotalGastos();
		 model.addAttribute("totalGastos", total);
		 
		    long totalDepartamentos = departamentoService.contarDepartamentos();
		    long totalPropietarios = propietarioService.contarPropietarios();
		    model.addAttribute("totalDepartamentos", totalDepartamentos);
		    model.addAttribute("totalPropietarios", totalPropietarios);
		 
	     return "admin-home";
	    }
}
