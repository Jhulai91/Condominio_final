package com.proyecto.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.entity.Propietario;
import com.proyecto.entity.Rol;
import com.proyecto.entity.Usuario;
import com.proyecto.repository.PropietarioRepository;
import com.proyecto.repository.UsuarioRepository;
import com.proyecto.service.UsuarioService;
import com.uisrael.gestion_biblioteca.entity.Autor;
import com.uisrael.gestion_biblioteca.entity.Libro;

@Controller
public class UsuarioController {
	
	  @Autowired
	    private UsuarioRepository usuarioRepo;

	  @Autowired
	    private UsuarioService usuarioService;
	    @Autowired
	    private PropietarioRepository propietarioRepo;
	    @Autowired
	    private PasswordEncoder passwordEncoder; 


	 @GetMapping("/login")
	    public String login(@RequestParam(value = "error", required = false) String error,
                @RequestParam(value = "mensaje", required = false) String mensaje,
                Model model) {
		 
		 if (mensaje != null) {
			    model.addAttribute("error", mensaje);
			}

	        return "login"; 
	    }
	 @GetMapping("/error_login")
	 public String mostrarErrorLogin(@RequestParam(value = "mensaje", required = false) String mensaje, Model model) {
	     if (mensaje != null) {
	         model.addAttribute("error", mensaje);
	     }
	     return "error_login"; 
	 }

	 @GetMapping("/usuario/registro")
	    public String mostrarFormulario(Model model) {
	        model.addAttribute("usuario", new Usuario());
	        return "registro";
	    }

	    @PostMapping("usuario/registro")
	    public String registrar(
	    	    @ModelAttribute Usuario usuario,
	    	    @RequestParam("cedula") String cedula,
	    	    @RequestParam("telefono") String telefono,
	    	    @RequestParam("rol") String rol,
	    	    Model model
	    	) {
	    	    if (usuarioRepo.findByEmail(usuario.getEmail()) != null) {
	    	        model.addAttribute("error", "El correo ya está registrado.");
	    	        return "registro";
	    	    }
	    	    
	    	   
	    	    if (propietarioRepo.findByCedula(cedula) != null) {
	    	        model.addAttribute("error", "La cédula ya está registrada.");
	    	        return "registro";
	    	    }
	    	    
	    	    String hashedPassword = passwordEncoder.encode(usuario.getPassword());
	    	    usuario.setPassword(hashedPassword);
	    	    
	    	    usuario.setRol(Rol.valueOf(rol));

	    	  
	    	    Usuario nuevoUsuario = usuarioRepo.save(usuario);

	    	  
	    	    Propietario propietario = new Propietario();
	    	    propietario.setCedula(cedula);
	    	    propietario.setTelefono(telefono);
	    	    propietario.setUsuario(nuevoUsuario);

	    	    propietarioRepo.save(propietario);

	    	    return "redirect:/login?registroExitoso";
	    	}
	    
	    
	
	    @GetMapping("/usuario/editar-usuario")
	    public String listarUsuarios(Model model, Principal principal) {
	        List<Usuario> usuarios = usuarioRepo.findAll();
	    
	        String email = principal.getName();
	        Usuario usuario = usuarioRepo.findByEmail(email);
	        model.addAttribute("usuarioAutenticado", usuario);

	        model.addAttribute("usuarios", usuarios);
	        return "usuarios_lista"; 
	    }

	    // Mostrar formulario de edición
	    @GetMapping("/usuario/editar-usuario/{id}")
	    public String mostrarFormularioEditar(@PathVariable Integer id, Model model,Principal principal) {
	    	 Usuario usuario = usuarioRepo.findById(id).orElse(null);
	    	    if (usuario == null) {
	    	        return "redirect:/usuario/editar-usuario?error=idNoExiste";
	    	    }
	    	    
	    	    String email = principal.getName();
	    	    Usuario usuarioAutenticado = usuarioRepo.findByEmail(email);
	    	    model.addAttribute("usuarioAutenticado", usuarioAutenticado);

	    	    
	    	    model.addAttribute("usuario", usuario);
	    	    return "usuarios_actualiza";
	    }
	    
	    
	   
	    @PostMapping("/usuario/actualizar")
	    public String actualizarUsuario(@ModelAttribute Usuario usuario) {
	    	Propietario propietario = usuario.getPropietario();

	        if (propietario != null && propietario.getId() != null) {
	           
	            propietario.setUsuario(usuario);
	        }

	        usuario.setPropietario(propietario);
	    	
	    	usuarioRepo.save(usuario);
	        return "redirect:/usuario/editar-usuario/" + usuario.getId() + "?actualizado=true";
	       // return "redirect:/admin/usuarios";
	    }
	    
	    @PostMapping("/usuario/eliminar")
	    public String eliminarUsuario(@RequestParam Integer id) {
	        usuarioRepo.deleteById(id);
	        return "redirect:/usuario/editar-usuario";
	    }
	    
	    

}
