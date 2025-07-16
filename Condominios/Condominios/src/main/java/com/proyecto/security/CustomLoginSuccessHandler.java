package com.proyecto.security;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
	
		String redirectURL = request.getContextPath();

	    if (authentication.getAuthorities().stream().anyMatch(
	            a -> a.getAuthority().equals("ROLE_ADMINISTRADOR"))) {
	        redirectURL += "/admin/home";
	    } else if (authentication.getAuthorities().stream().anyMatch(
	            a -> a.getAuthority().equals("ROLE_PROPIETARIO"))) {
	        redirectURL += "/propietario/home";
	    } else {
	    	  response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Rol no válido");
	            return;
	        //redirectURL += "/login?error";
	    }

	    response.sendRedirect(redirectURL);
    }
		
	

}
