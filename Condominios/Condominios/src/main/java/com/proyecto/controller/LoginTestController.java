package com.proyecto.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class LoginTestController {
	 @GetMapping("/rol")
	    @ResponseBody
	    public String mostrarRol(Authentication auth) {
	        return "Roles detectados: " + auth.getAuthorities().toString();
	    }
}
