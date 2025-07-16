package com.proyecto.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.entity.Usuario;
import com.proyecto.repository.UsuarioRepository;
import com.proyecto.security.CustomUserDetails;
import com.proyecto.service.CustomUserDetailsService;
@Service
@Transactional
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService{

	 @Autowired
	    private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Usuario usuario = usuarioRepository.findByEmail(email);
                
        return new CustomUserDetails(usuario);
	}

}
