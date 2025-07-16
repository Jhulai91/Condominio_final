package com.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.proyecto.entity.Usuario;
import com.proyecto.repository.UsuarioRepository;


@Controller
public class AdminController {
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
	    public String adminHome() {
	        return "admin-home";
	    }
}
