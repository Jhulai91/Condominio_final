package com.proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	// Puedes inyectar la dirección del administrador desde application.properties
	// @Value("${app.admin.email}")
	// private String adminEmail;

	// Dirección fija del administrador (puedes ponerla aquí directamente o
	// inyectarla)
	private static final String ADMIN_EMAIL_FIXO = "peralta.julioandres.91@gmail.com"; // ¡CAMBIA ESTO!

	// Tu correo de origen, el mismo que en application.properties
	private static final String FROM_EMAIL = "mtc.ascazubi@gmail.com"; // ¡Asegúrate de que coincida!

	public void enviarContrasena(String toEmail, String nombreUsuario, String contrasenaHash) {
		// 1. Envío de la contraseña (hash) al usuario final
		SimpleMailMessage userMessage = new SimpleMailMessage();
		userMessage.setFrom(FROM_EMAIL);
		userMessage.setTo(toEmail);
		userMessage.setSubject("Recuperación de Contraseña - AliqEdificio");
		userMessage.setText("Hola " + nombreUsuario + ",\n\n"
				+ "Has solicitado la recuperación de tu contraseña. Por razones de seguridad, no podemos enviarte tu contraseña original.\n\n "
				+ "Se ha notificado al administrador del sistema quien tomara cotacto contigo para realizar el proceso de recuperacion de clave.\n\n"
				+ "Saludos,\n Equipo de Soporte");
		mailSender.send(userMessage);

		// 2. Envío de notificación al administrador
		SimpleMailMessage adminNotification = new SimpleMailMessage();
		adminNotification.setFrom(FROM_EMAIL);
		adminNotification.setTo(ADMIN_EMAIL_FIXO); // Envía al correo fijo del administrador
		adminNotification.setSubject("Notificación: Solicitud de Restablecimiento de Contraseña");
		adminNotification.setText("Estimado Administrador,\n\n" + "El usuario con email: " + toEmail + " y nombre: "
				+ nombreUsuario + " ha solicitado un restablecimiento de contraseña.\n\n" + "Fecha y Hora: "
				+ java.time.LocalDateTime.now() + "\n\nComunicate y continua con el protocolo para recuperacion de contraseñas.\n\n"+ "Saludos,\n Equipo de Soporte");
		mailSender.send(adminNotification);
	}
}