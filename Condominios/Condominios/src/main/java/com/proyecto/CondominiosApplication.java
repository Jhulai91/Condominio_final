package com.proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

import jakarta.servlet.MultipartConfigElement;

@SpringBootApplication
public class CondominiosApplication {

	public static void main(String[] args) {
		SpringApplication.run(CondominiosApplication.class, args);
	}
	
    // Aquí añadirías el bean si necesitas configurar límites explícitamente:
   // @Bean
   // public MultipartConfigElement multipartConfigElement() {
   //      MultipartConfigFactory factory = new MultipartConfigFactory();
   //      factory.setMaxFileSize(DataSize.ofMegabytes(5)); // Ejemplo: 5MB por archivo
   //      factory.setMaxRequestSize(DataSize.ofMegabytes(5)); // Ejemplo: 5MB por solicitud total
   //      return factory.createMultipartConfig();
   //  }
}
