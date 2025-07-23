package com.proyecto.controller;

// ... otras importaciones ...

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping; // ¡Asegúrate de importar GetMapping!
// ...

@Controller
public class LoginController {

    // ... otros Autowired y métodos de login ...

    // --- Recuperación de Contraseña (Verifica este método GET) ---
    @GetMapping("/forgot-password") // ¡Esta es la URL a la que apunta el enlace!
    public String showForgotPasswordForm() {
        return "forgotPassword"; // ¡Este debe ser el nombre exacto de tu archivo HTML sin .html!
    }

    // ... tu método POST para procesar el formulario de recuperación ...
}