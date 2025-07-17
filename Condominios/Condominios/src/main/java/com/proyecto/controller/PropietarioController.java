package com.proyecto.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.thymeleaf.context.Context;

import com.proyecto.entity.Alicuota;
import com.proyecto.entity.Departamento;
import com.proyecto.entity.Propietario;
import com.proyecto.entity.Usuario;
import com.proyecto.repository.UsuarioRepository;
import com.proyecto.service.AlicuotaService;
import com.proyecto.service.DepartamentoService;
import com.proyecto.service.PdfGeneratorService;
import com.proyecto.service.PropietarioService;

@Controller
public class PropietarioController {

	@Autowired
	private final UsuarioRepository usuarioRepo;
	private final PropietarioService propietarioService;
	private final DepartamentoService departamentoService;
	private final AlicuotaService alicuotaService;
	private final PdfGeneratorService pdfGeneratorService;
	
	public PropietarioController(
            UsuarioRepository usuarioRepo,
            PropietarioService propietarioService,
            DepartamentoService departamentoService,
            AlicuotaService alicuotaService,
            PdfGeneratorService pdfGeneratorService) { 
		this.usuarioRepo = usuarioRepo;
        this.propietarioService = propietarioService;
        this.departamentoService = departamentoService;
        this.alicuotaService = alicuotaService; 
        this.pdfGeneratorService = pdfGeneratorService;
	}

	@GetMapping("/propietario/home")
	public String propietarioHome(Model model) { 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUsuarioAutenticado = authentication.getName();

        Optional<Propietario> propietarioOptional = propietarioService.obtenerPropietarioPorEmailUsuario(emailUsuarioAutenticado);

        if (propietarioOptional.isPresent()) {
            Propietario propietario = propietarioOptional.get();
            // Obtener los departamentos del propietario
            List<Departamento> departamentos = departamentoService.findByPropietario(propietario);

            // Obtener el conteo de alícuotas por estado
            Map<String, Long> alicuotasCountByState = alicuotaService.getAlicuotasCountByStateForDepartamentos(departamentos);
            model.addAttribute("alicuotasCountByState", alicuotasCountByState);

            // Opcional: Obtener la suma de valores por estado
            Map<String, Double> alicuotasSumValueByState = alicuotaService.getAlicuotasSumValueByStateForDepartamentos(departamentos);
            model.addAttribute("alicuotasSumValueByState", alicuotasSumValueByState);

        } else {
            // Manejar caso donde no se encuentra el propietario, si es necesario
            model.addAttribute("mensajeError", "No se encontró la información de propietario para el usuario actual.");
        }
		return "propietario-home";
	}

	@ModelAttribute
	public void agregarUsuarioAutenticado(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		if (userDetails != null) {
			Usuario usuario = usuarioRepo.findByEmail(userDetails.getUsername());
			model.addAttribute("usuarioAutenticado", usuario);
		}
	}
	
    @GetMapping("/propietario/informacion") 
    public String propietarioInformacion(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUsuarioAutenticado = authentication.getName();

        Optional<Propietario> propietarioOptional = propietarioService.obtenerPropietarioPorEmailUsuario(emailUsuarioAutenticado);

        if (propietarioOptional.isPresent()) {
            Propietario propietario = propietarioOptional.get();
            model.addAttribute("propietario", propietario);
            model.addAttribute("usuario", propietario.getUsuario()); 

            List<Departamento> departamentos = departamentoService.findByPropietario(propietario);
            model.addAttribute("departamentos", departamentos);
   
            return "propietario-informacion"; 
        } else {

            model.addAttribute("mensajeError", "No se encontró la información de propietario para el usuario actual.");
            return "error"; 
        }
    }
    
    @GetMapping("/propietario/alicuotas/canceladas")
    public String verAlicuotasCanceladas(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUsuarioAutenticado = authentication.getName();

        Optional<Propietario> propietarioOptional = propietarioService.obtenerPropietarioPorEmailUsuario(emailUsuarioAutenticado);

        if (propietarioOptional.isPresent()) {
            Propietario propietario = propietarioOptional.get();
            model.addAttribute("propietario", propietario);
            model.addAttribute("usuario", propietario.getUsuario()); 

            List<Departamento> departamentos = departamentoService.findByPropietario(propietario);

            List<Alicuota> alicuotasCanceladas = alicuotaService.findAlicuotasCanceladasByDepartamentos(departamentos);
            model.addAttribute("alicuotasCanceladas", alicuotasCanceladas); 

            return "alicuotas-canceladas-propietario"; 
        } else {
            model.addAttribute("mensajeError", "No se encontró la información de propietario para el usuario actual.");
            return "error";
        }
    }
    
    @GetMapping("/propietario/alicuotas") 
    public String verAlicuotas(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUsuarioAutenticado = authentication.getName();

        Optional<Propietario> propietarioOptional = propietarioService.obtenerPropietarioPorEmailUsuario(emailUsuarioAutenticado);

        if (propietarioOptional.isPresent()) {
            Propietario propietario = propietarioOptional.get();
            model.addAttribute("propietario", propietario);
            model.addAttribute("usuario", propietario.getUsuario());

            List<Departamento> departamentos = departamentoService.findByPropietario(propietario);

            List<Alicuota> todasLasAlicuotas = alicuotaService.findAlicuotasByDepartamentos(departamentos);
            model.addAttribute("alicuotas", todasLasAlicuotas); 

            return "alicuotas-propietario"; 
        } else {
            model.addAttribute("mensajeError", "No se encontró la información de propietario para el usuario actual.");
            return "error";
        }
    }    
    
    @GetMapping("/propietario/alicuotas/export-pdf")
    public ResponseEntity<byte[]> exportarAlicuotasPdf() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUsuarioAutenticado = authentication.getName();

        Optional<Propietario> propietarioOptional = propietarioService.obtenerPropietarioPorEmailUsuario(emailUsuarioAutenticado);

        if (propietarioOptional.isPresent()) {
            Propietario propietario = propietarioOptional.get();
            Usuario usuario = propietario.getUsuario();

            List<Departamento> departamentos = departamentoService.findByPropietario(propietario);
            List<Alicuota> todasLasAlicuotas = alicuotaService.findAlicuotasByDepartamentos(departamentos);

            Context context = new Context();
            context.setVariable("propietario", propietario);
            context.setVariable("usuario", usuario);
            context.setVariable("alicuotas", todasLasAlicuotas);

            String templateName = "alicuotas-propietario-pdf"; 

            byte[] pdfBytes = pdfGeneratorService.generatePdfFromHtml(templateName, context);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=alicuotas_propietario.pdf"); 
            headers.setContentType(MediaType.APPLICATION_PDF);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
        } else {
            return ResponseEntity.status(404).body("No se encontró la información del propietario.".getBytes());
        }
    }
}
