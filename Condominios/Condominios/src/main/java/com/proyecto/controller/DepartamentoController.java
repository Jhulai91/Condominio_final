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
import com.proyecto.entity.Edificio;
import com.proyecto.entity.Propietario;
import com.proyecto.entity.Usuario;
import com.proyecto.repository.DepartamentoRepository;
import com.proyecto.repository.EdificioRepository;
import com.proyecto.repository.PropietarioRepository;
import com.proyecto.repository.UsuarioRepository;
import com.proyecto.service.DepartamentoService;
import com.proyecto.service.EdificioService;
import com.proyecto.service.PropietarioService;

@Controller
public class DepartamentoController {

	@Autowired
    private EdificioRepository edificioRepo;
	@Autowired
    private EdificioService edificioService;
	 @Autowired
		private UsuarioRepository usuarioRepo;
	@Autowired
    private PropietarioService propietarioService;
	@Autowired
    private PropietarioRepository propietarioRepo;
	@Autowired
    private DepartamentoService departamentoService;
	@Autowired
    private DepartamentoRepository departamentoRepo;
	
	@GetMapping("/departamento/lista")
	public String listarDepartamentos(Model model,  Principal principal) {
		List<Usuario> usuarios = usuarioRepo.findAll();
	     // Obtener nombre del usuario autenticado
	        String email = principal.getName(); // si usas email como username
	        Usuario usuario = usuarioRepo.findByEmail(email);
	        model.addAttribute("usuarioAutenticado", usuario);
		
		
	    List<Departamento> departamentos = departamentoService.findAllDepartamento(); // O servicio si usas uno
	    model.addAttribute("departamentos", departamentos);
	    return "departamento_lista";
	}
	
	 // Mostrar formulario nuevo autor
    @GetMapping("/departamento/nuevo")
    public String mostrarFormulario(Model model, Principal principal) {
    	
    	List<Propietario> propietarios = propietarioRepo.findAllConUsuario();
    	model.addAttribute("propietarios", propietarios);
    	
    	List<Usuario> usuarios = usuarioRepo.findAll();
	     // Obtener nombre del usuario autenticado
	        String email = principal.getName(); // si usas email como username
	        Usuario usuario = usuarioRepo.findByEmail(email);
	        model.addAttribute("usuarioAutenticado", usuario);
    	
    	
    	model.addAttribute("departamento", new Departamento());
        model.addAttribute("edificios", edificioService.findAllEdificio());
        //model.addAttribute("propietarios", usuarioRepo.findAllPropietarios());
       // model.addAttribute("propietarios", propietarioService.findAllPropieatrio());
        return "departamento_ingreso"; // nombre del html
    }
    
    @PostMapping("/departamento/guardar")
    public String guardarDepartamento(@ModelAttribute Departamento departamento,Model model, Principal principal) {
    	 // Validar número único dentro del edificio
        if (departamentoService.existsByNumeroAndEdificio(departamento.getNumero(), departamento.getEdificio())) {
            model.addAttribute("departamento", departamento);
            model.addAttribute("edificios", edificioService.findAllEdificio());
            model.addAttribute("propietarios", usuarioRepo.findAllPropietarios());

            // Usuario autenticado
            String email = principal.getName();
            Usuario usuario = usuarioRepo.findByEmail(email);
            model.addAttribute("usuarioAutenticado", usuario);

            model.addAttribute("error", "El número de departamento ya existe en ese edificio.");
            return "departamento_ingreso";
        }
    	
    	Long propietarioId = departamento.getPropietario().getId();
        Propietario propietario = propietarioService.findById(propietarioId);
        departamento.setPropietario(propietario);

        departamentoService.guardaryRecalcular(departamento);
        return "redirect:/departamento/lista";
    }
    
    @GetMapping("/departamento/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        departamentoService.eliminaryRecalcular(id);
        return "redirect:/departamento/lista";
    }
    
    @GetMapping("/departamento/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model, Principal principal) {
    	
    	List<Usuario> usuarios = usuarioRepo.findAll();
	     // Obtener nombre del usuario autenticado
	        String email = principal.getName(); // si usas email como username
	        Usuario usuario = usuarioRepo.findByEmail(email);
	        model.addAttribute("usuarioAutenticado", usuario);
		
    	
    	
        Departamento departamento = departamentoService.findById(id);
        model.addAttribute("departamento", departamento);
        model.addAttribute("edificios", edificioService.findAllEdificio());
        model.addAttribute("propietarios", propietarioRepo.findAllConUsuario());
        return "departamentos_actualiza";
    }

    @PostMapping("/departamento/actualizar")
    public String actualizar(@ModelAttribute Departamento departamento) {
    	 Edificio edificio = edificioService.findById(departamento.getEdificio().getId());
    	 Long propietarioId = departamento.getPropietario().getId();
    	 Propietario propietario = propietarioService.findById(propietarioId);
    	 departamento.setEdificio(edificio);
    	 departamento.setPropietario(propietario);
        departamentoService.updateDepartamento(departamento.getId(), departamento);
        return "redirect:/departamento/lista";
    }
}
