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

import com.proyecto.entity.Edificio;
import com.proyecto.entity.Usuario;
import com.proyecto.repository.EdificioRepository;
import com.proyecto.repository.UsuarioRepository;

@Controller
public class EdificioController {

	@Autowired
    private EdificioRepository edificioRepo;
	 @Autowired
	private UsuarioRepository usuarioRepo;
    @GetMapping("/edificios/listar")
    public String listarEdificios(Model model, Principal principal) {
    	List<Usuario> usuarios = usuarioRepo.findAll();
	     // Obtener nombre del usuario autenticado
	        String email = principal.getName(); // si usas email como username
	        Usuario usuario = usuarioRepo.findByEmail(email);
	        model.addAttribute("usuarioAutenticado", usuario);
    	
        model.addAttribute("edificios", edificioRepo.findAll());
        return "edificios_lista";
    }

    @GetMapping("/edificios/nuevo")
    public String mostrarFormularioNuevo(Model model, Principal principal) {
    	 List<Usuario> usuarios = usuarioRepo.findAll();
	     // Obtener nombre del usuario autenticado
	        String email = principal.getName(); // si usas email como username
	        Usuario usuario = usuarioRepo.findByEmail(email);
	        model.addAttribute("usuarioAutenticado", usuario);
        model.addAttribute("edificio", new Edificio());
        return "edificios_ingreso";
    }

    @PostMapping("/edificios/guardar")
    public String guardarEdificio(@ModelAttribute Edificio edificio) {
        edificioRepo.save(edificio);
        return "redirect:/edificios/editar";
    }

    @GetMapping("/edificios/editar/{id}")
    public String editarEdificio(@PathVariable Integer id, Model model, Principal principal) {
    	
    	 // Obtener nombre del usuario autenticado
        String email = principal.getName(); // si usas email como username
        Usuario usuario = usuarioRepo.findByEmail(email);
        model.addAttribute("usuarioAutenticado", usuario);
	
        Edificio edificio = edificioRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));
        model.addAttribute("edificio", edificio);
        return "edificios_actualiza";
    }
    
    @PostMapping("/edificios/actualizar")
    public String actualizarEdificio(@ModelAttribute Edificio edificio) {
        Edificio edificioExistente = edificioRepo.findById(edificio.getId())
            .orElseThrow(() -> new IllegalArgumentException("ID de edificio inválido: " + edificio.getId()));

        // Actualizamos solo los campos que deseas permitir editar
        edificioExistente.setNombre(edificio.getNombre());
        edificioExistente.setDireccion(edificio.getDireccion());
        edificioExistente.setNumeroDepartamentos(edificio.getNumeroDepartamentos());

        edificioRepo.save(edificioExistente);

        return "redirect:/edificios/listar";
    }

    @GetMapping("/edificios/eliminar/{id}")
    public String eliminarEdificio(@PathVariable Integer id) {
        edificioRepo.deleteById(id);
        return "redirect:/edificios/editar";
    }
}
