package service_desk_api.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import service_desk_api.api.model.Usuario;
import service_desk_api.api.service.UsuarioService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ApiApplication {
	
	private UsuarioService usuarioService;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
		System.out.println("Aplicação iniciada com sucesso.");
	}
	
}
