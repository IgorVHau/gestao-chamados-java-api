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
		System.out.println("Dá uma olhada nisto: " + new BCryptPasswordEncoder().encode("123456"));
	}
	
	public void run(String... args) throws Exception {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String senhaCriptografada = encoder.encode("123456");
		System.out.println("Senha criptografada: " + senhaCriptografada);
		salvar();
	}
	
	public void salvar() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String userCryptoPassword = encoder.encode("123456");
		Usuario user = new Usuario("Silva", "user@email.com", userCryptoPassword, "USER");
		String adminCryptoPassword = encoder.encode("123");
		Usuario admin = new Usuario("Fernando", "admin@email.com", adminCryptoPassword, "ADMIN");
		usuarioService.save(user);
		usuarioService.save(admin);
	}

}
