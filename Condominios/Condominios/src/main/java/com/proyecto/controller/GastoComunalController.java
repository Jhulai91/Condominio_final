package com.proyecto.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.proyecto.entity.Departamento;
import com.proyecto.entity.GastoComunal;
import com.proyecto.entity.TipoGasto;
import com.proyecto.entity.Usuario;
import com.proyecto.repository.EdificioRepository;
import com.proyecto.repository.TipoGastoRepository;
import com.proyecto.repository.UsuarioRepository;
import com.proyecto.service.GastoComunalService;

@Controller
public class GastoComunalController {
	 @Autowired
	    private GastoComunalService gastoService;

	    @Autowired
	    private EdificioRepository edificioRepo;

	    @Autowired
	    private UsuarioRepository usuarioRepo;
	    @Autowired
	    private TipoGastoRepository tipoGastoRepository;
	    
	    
	    @GetMapping("/gastos/lista")
		public String listarGastos(Model model,  Principal principal) {
			List<Usuario> usuarios = usuarioRepo.findAll();
		    
		        String email = principal.getName(); 
		        Usuario usuario = usuarioRepo.findByEmail(email);
		        model.addAttribute("usuarioAutenticado", usuario);
		        Double total = gastoService.obtenerTotalGastos();
		        model.addAttribute("totalGastos", total);
			
		    List<GastoComunal> gastocomunal = gastoService.findAllGasto(); 
		    model.addAttribute("gastocomunal", gastocomunal);
		    return "gastos_lista";
		}
	    
	    
	    
	    
	    @GetMapping("/gastos/nuevo")
	    public String mostrarFormulario(Model model, Principal principal) {
	    	
	    
	        model.addAttribute("gasto", new GastoComunal());
	        model.addAttribute("edificios", edificioRepo.findAll());
	        model.addAttribute("tiposGasto", tipoGastoRepository.findAll());
	      
	        String email = principal.getName();
	        Usuario usuario = usuarioRepo.findByEmail(email);
	      //  model.addAttribute("usuarioAutenticado", usuario);
	        model.addAttribute("usuarioAutenticado", usuarioRepo.findByEmail(email));
	        model.addAttribute("tiposGasto", tipoGastoRepository.findAll());

	        return "gastos_ingreso";
	    }
	    @PostMapping("/gastos/guardar")
	    public String guardar(@ModelAttribute GastoComunal gasto,Model model, Principal principal) {
	        
	    	
	        String email = principal.getName();
	        Usuario usuario = usuarioRepo.findByEmail(email);
	      //  model.addAttribute("usuarioAutenticado", usuario);
	        model.addAttribute("usuarioAutenticado", usuarioRepo.findByEmail(email));
	    	
	    	
	    	gasto.setMes(gasto.getFecha().getMonthValue());
	        gasto.setAnio(gasto.getFecha().getYear());

	        if (gasto.getTipoGasto() != null && gasto.getTipoGasto().getId() != null) {
	            TipoGasto tipo = tipoGastoRepository.findById(gasto.getTipoGasto().getId())
	                .orElseThrow(() -> new RuntimeException("Tipo de gasto no encontrado"));
	            gasto.setTipoGasto(tipo);
	            gasto.setDescripcion(tipo.getNombre());
	        } else {
	            model.addAttribute("error", "Debe seleccionar un tipo de gasto válido.");
	            model.addAttribute("gasto", gasto);
	            model.addAttribute("edificios", edificioRepo.findAll());
	            model.addAttribute("tiposGasto", tipoGastoRepository.findAll());
	            return "gastos_ingreso";
	        }
	        boolean esDuplicado = gastoService.gastoExiste(gasto);
	        if (esDuplicado) {
	            model.addAttribute("error", "Ya existe un gasto para ese edificio, mes, año y tipo.");
	            model.addAttribute("gasto", gasto);
	            model.addAttribute("edificios", edificioRepo.findAll());
	            model.addAttribute("tiposGasto", tipoGastoRepository.findAll());
	            return "gastos_ingreso";
	        }

	        gastoService.saveGasto(gasto);
	        return "redirect:/gastos/lista";

	       
	    
	    }

	    @GetMapping("/gastos/editar/{id}")
	    public String editarGasto(@PathVariable Integer id, Model model, Principal principal) {
	    	 GastoComunal gasto = gastoService.findById(id);
	         model.addAttribute("gasto", gasto);
	         model.addAttribute("edificios", edificioRepo.findAll());
	         model.addAttribute("tiposGasto", tipoGastoRepository.findAll());

	         String email = principal.getName();
	         model.addAttribute("usuarioAutenticado", usuarioRepo.findByEmail(email));

	         return "gastos_actualiza";
	    }
	    
	    @PostMapping("gastos/actualizar")
	    public String actualizarGasto(@ModelAttribute GastoComunal gasto) {
	        gasto.setMes(gasto.getFecha().getMonthValue());
	        gasto.setAnio(gasto.getFecha().getYear());

	        TipoGasto tipo = tipoGastoRepository.findById(gasto.getTipoGasto().getId())
	            .orElseThrow(() -> new RuntimeException("Tipo de gasto no encontrado"));
	        gasto.setTipoGasto(tipo);
	        gasto.setDescripcion(tipo.getNombre());

	        gastoService.updateGasto(gasto.getId(), gasto);
	        return "redirect:/gastos/lista";
	    }
	    @GetMapping("/gastos/eliminar/{id}")
	    public String eliminarGasto(@PathVariable Integer id) {
	        gastoService.deleteGasto(id);
	        return "redirect:/gastos/lista";
	    }

}
