package com.proyecto.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;

import com.proyecto.entity.Alicuota;
import com.proyecto.entity.Departamento;
import com.proyecto.entity.Pago;
import com.proyecto.entity.Propietario;
import com.proyecto.entity.Usuario;
import com.proyecto.repository.UsuarioRepository;
import com.proyecto.service.AlicuotaService;
import com.proyecto.service.DepartamentoService;
import com.proyecto.service.PagoService;
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
	private final PagoService pagoService;

	public PropietarioController(UsuarioRepository usuarioRepo, PropietarioService propietarioService,
			DepartamentoService departamentoService, AlicuotaService alicuotaService,
			PdfGeneratorService pdfGeneratorService, PagoService pagoService) {
		this.usuarioRepo = usuarioRepo;
		this.propietarioService = propietarioService;
		this.departamentoService = departamentoService;
		this.alicuotaService = alicuotaService;
		this.pdfGeneratorService = pdfGeneratorService;
		this.pagoService = pagoService;
	}

	@GetMapping("/propietario/home")
	public String propietarioHome(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String emailUsuarioAutenticado = authentication.getName();

		Optional<Propietario> propietarioOptional = propietarioService
				.obtenerPropietarioPorEmailUsuario(emailUsuarioAutenticado);

		if (propietarioOptional.isPresent()) {
			Propietario propietario = propietarioOptional.get();
			// Pasa el objeto propietario y usuario al modelo para uso en la vista
			model.addAttribute("propietario", propietario);
			model.addAttribute("usuario", propietario.getUsuario());
			// Obtener los departamentos del propietario
			List<Departamento> departamentos = departamentoService.findByPropietario(propietario);

			// Obtener el conteo de alícuotas por estado
			Map<String, Long> alicuotasCountByState = alicuotaService
					.getAlicuotasCountByStateForDepartamentos(departamentos);
			model.addAttribute("alicuotasCountByState", alicuotasCountByState);

			// Opcional: Obtener la suma de valores por estado
			Map<String, Double> alicuotasSumValueByState = alicuotaService
					.getAlicuotasSumValueByStateForDepartamentos(departamentos);
			model.addAttribute("alicuotasSumValueByState", alicuotasSumValueByState);

			// Obtener todas las alícuotas del propietario para la tabla de detalle en home
			List<Alicuota> todasLasAlicuotas = alicuotaService.findAlicuotasByDepartamentos(departamentos);
			model.addAttribute("alicuotas", todasLasAlicuotas);

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

		Optional<Propietario> propietarioOptional = propietarioService
				.obtenerPropietarioPorEmailUsuario(emailUsuarioAutenticado);

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

		Optional<Propietario> propietarioOptional = propietarioService
				.obtenerPropietarioPorEmailUsuario(emailUsuarioAutenticado);

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

		Optional<Propietario> propietarioOptional = propietarioService
				.obtenerPropietarioPorEmailUsuario(emailUsuarioAutenticado);

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

		Optional<Propietario> propietarioOptional = propietarioService
				.obtenerPropietarioPorEmailUsuario(emailUsuarioAutenticado);

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

			return ResponseEntity.ok().headers(headers).body(pdfBytes);
		} else {
			return ResponseEntity.status(404).body("No se encontró la información del propietario.".getBytes());
		}
	}

	// --- NUEVOS MÉTODOS PARA EL REGISTRO DE PAGOS ---

	@GetMapping("/propietario/pagos/registrar")
	public String mostrarFormularioRegistrarPago(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String emailUsuarioAutenticado = authentication.getName();

		Optional<Propietario> propietarioOptional = propietarioService
				.obtenerPropietarioPorEmailUsuario(emailUsuarioAutenticado);

		if (!propietarioOptional.isPresent()) {
			model.addAttribute("mensajeError", "No se encontró la información de propietario para el usuario actual.");
			return "error"; // O redirigir a una página de error
		}

		Propietario propietario = propietarioOptional.get();
		List<Departamento> departamentos = departamentoService.findByPropietario(propietario);

		// Obtener solo las alícuotas PENDIENTES o VENCIDAS del propietario
		List<Alicuota> alicuotasDisponiblesParaPago = alicuotaService.findAlicuotasByDepartamentos(departamentos)
				.stream()
				.filter(alicuota -> alicuota.getPago() == null || !alicuota.getPago().getEstado().equals("PAGADO")) // Asegura
																													// que
																													// no
																													// tenga
																													// un
																													// pago
																													// ya
																													// registrado
																													// como
																													// PAGADO
				.collect(Collectors.toList());

		model.addAttribute("pago", new Pago()); // Objeto Pago vacío para el formulario
		model.addAttribute("alicuotasDisponibles", alicuotasDisponiblesParaPago);
		model.addAttribute("usuarioAutenticado", usuarioRepo.findByEmail(emailUsuarioAutenticado)); // Re-añadir el
																									// usuario
																									// autenticado

		return "propietario-registrar-pago"; // Nombre de la nueva plantilla HTML
	}

	@PostMapping("/propietario/pagos/registrar")
	public String procesarRegistroPago(@ModelAttribute("pago") Pago pago, // Enlaza los campos del formulario a un
																			// objeto Pago
			@RequestParam("alicuotaId") Integer alicuotaId, // Para el ID de la alícuota seleccionada
			@RequestParam("comprobante") MultipartFile comprobanteFile, // Para el archivo PDF
			Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String emailUsuarioAutenticado = authentication.getName();

		Optional<Propietario> propietarioOptional = propietarioService
				.obtenerPropietarioPorEmailUsuario(emailUsuarioAutenticado);

		if (!propietarioOptional.isPresent()) {
			model.addAttribute("mensajeError", "No se encontró la información de propietario para el usuario actual.");
			return "error";
		}

		Propietario propietario = propietarioOptional.get();
		List<Departamento> departamentosDelPropietario = departamentoService.findByPropietario(propietario);

		// 1. Validar que la alícuota seleccionada exista y pertenezca al propietario
		Optional<Alicuota> alicuotaOptional = alicuotaService.findAlicuotaById(alicuotaId);

		if (!alicuotaOptional.isPresent()) {
			model.addAttribute("mensajeError", "La alícuota seleccionada no es válida.");
			// Recargar la lista de alícuotas disponibles para que el usuario pueda intentar
			// de nuevo
			model.addAttribute("alicuotasDisponibles",
					alicuotaService.findAlicuotasByDepartamentos(departamentosDelPropietario).stream()
							.filter(a -> a.getPago() == null || !a.getPago().getEstado().equals("PAGADO"))
							.collect(Collectors.toList()));
			model.addAttribute("pago", pago); // Mantener datos del formulario
			return "propietario-registrar-pago";
		}

		Alicuota alicuotaSeleccionada = alicuotaOptional.get();

		// Verificar que la alícuota realmente pertenezca a uno de los departamentos del
		// propietario
		boolean alicuotaPerteneceAlPropietario = departamentosDelPropietario.stream()
				.anyMatch(dep -> dep.getId().equals(alicuotaSeleccionada.getDepartamento().getId()));

		if (!alicuotaPerteneceAlPropietario) {
			model.addAttribute("mensajeError", "No tiene permiso para registrar pagos para esta alícuota.");
			model.addAttribute("alicuotasDisponibles",
					alicuotaService.findAlicuotasByDepartamentos(departamentosDelPropietario).stream()
							.filter(a -> a.getPago() == null || !a.getPago().getEstado().equals("PAGADO"))
							.collect(Collectors.toList()));
			model.addAttribute("pago", pago);
			return "propietario-registrar-pago";
		}

		// Verificar si la alícuota ya tiene un pago registrado
		if (alicuotaSeleccionada.getPago() != null && alicuotaSeleccionada.getPago().getEstado().equals("PAGADO")) {
			model.addAttribute("mensajeError", "Esta alícuota ya ha sido marcada como PAGADA.");
			model.addAttribute("alicuotasDisponibles",
					alicuotaService.findAlicuotasByDepartamentos(departamentosDelPropietario).stream()
							.filter(a -> a.getPago() == null || !a.getPago().getEstado().equals("PAGADO"))
							.collect(Collectors.toList()));
			model.addAttribute("pago", pago);
			return "propietario-registrar-pago";
		}

		// 2. Procesar el archivo PDF
		if (comprobanteFile != null && !comprobanteFile.isEmpty()) {
			try {
				pago.setComprobantePdf(comprobanteFile.getBytes());
			} catch (IOException e) {
				model.addAttribute("mensajeError", "Error al subir el archivo del comprobante: " + e.getMessage());
				model.addAttribute("alicuotasDisponibles",
						alicuotaService.findAlicuotasByDepartamentos(departamentosDelPropietario).stream()
								.filter(a -> a.getPago() == null || !a.getPago().getEstado().equals("PAGADO"))
								.collect(Collectors.toList()));
				model.addAttribute("pago", pago);
				return "propietario-registrar-pago";
			}
		} else {
			// Decidir si el comprobante PDF es obligatorio o no
			// Si es obligatorio, puedes añadir un error aquí
			model.addAttribute("mensajeError", "El comprobante PDF es obligatorio.");
			model.addAttribute("alicuotasDisponibles",
					alicuotaService.findAlicuotasByDepartamentos(departamentosDelPropietario).stream()
							.filter(a -> a.getPago() == null || !a.getPago().getEstado().equals("PAGADO"))
							.collect(Collectors.toList()));
			model.addAttribute("pago", pago);
			return "propietario-registrar-pago";
		}

		// 3. Establecer el estado inicial y la relación con la alícuota
		pago.setEstado("PENDIENTE"); // Por defecto, como lo solicitaste
		pago.setAlicuota(alicuotaSeleccionada);

		try {
			// Guardar el pago
			Pago pagoGuardado = pagoService.savePago(pago);

			// Actualizar la referencia de pago en la alícuota
			alicuotaSeleccionada.setPago(pagoGuardado);
			alicuotaService.saveAlicuota(alicuotaSeleccionada); // Asegúrate de tener un saveAlicuota en AlicuotaService

			model.addAttribute("mensajeExito", "Pago registrado exitosamente. Será revisado por la administración.");
			return "redirect:/propietario/alicuotas"; // Redirigir a la vista de alícuotas
		} catch (Exception e) {
			model.addAttribute("mensajeError", "Error al registrar el pago: " + e.getMessage());
			// En caso de error al guardar, recargar el formulario con los datos
			model.addAttribute("alicuotasDisponibles",
					alicuotaService.findAlicuotasByDepartamentos(departamentosDelPropietario).stream()
							.filter(a -> a.getPago() == null || !a.getPago().getEstado().equals("PAGADO"))
							.collect(Collectors.toList()));
			model.addAttribute("pago", pago);
			return "propietario-registrar-pago";
		}
	}

	// Método para ver el comprobante PDF (para un administrador o el propio
	// propietario)
	@GetMapping("/pagos/{pagoId}/comprobante")
	public ResponseEntity<byte[]> verComprobantePdf(@PathVariable Integer pagoId) {
		Optional<Pago> pagoOptional = pagoService.findPagoById(pagoId);

		if (pagoOptional.isPresent() && pagoOptional.get().getComprobantePdf() != null) {
			Pago pago = pagoOptional.get();

			// Validar que el usuario autenticado tiene permiso para ver este comprobante
			// Esto es CRUCIAL para la seguridad.
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String emailUsuarioAutenticado = authentication.getName();
			Optional<Propietario> propietarioOptional = propietarioService
					.obtenerPropietarioPorEmailUsuario(emailUsuarioAutenticado);

			if (propietarioOptional.isPresent()) {
				Propietario propietario = propietarioOptional.get();
				// Obtener todos los IDs de departamento del propietario
				List<Integer> deptosDelPropietarioIds = departamentoService.findByPropietario(propietario).stream()
						.map(Departamento::getId).collect(Collectors.toList());

				// Verificar si la alícuota asociada al pago pertenece a uno de los
				// departamentos del propietario
				if (pago.getAlicuota() != null
						&& deptosDelPropietarioIds.contains(pago.getAlicuota().getDepartamento().getId())) {
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_PDF);
					String filename = "comprobante_" + pago.getNumeroComprobante() + ".pdf";
					headers.setContentDispositionFormData("inline", filename); // "inline" para abrir en el navegador,
																				// "attachment" para descargar

					return ResponseEntity.ok().headers(headers).body(pago.getComprobantePdf());
				}
			}
			// Si no tiene permisos, o si no es el propietario de la alícuota, o si es un
			// administrador (necesitaría otra lógica de permiso)
			return ResponseEntity.status(403).body("Acceso denegado o comprobante no encontrado.".getBytes());
		}
		return ResponseEntity.status(404).body("Comprobante no encontrado.".getBytes());
	}
}
