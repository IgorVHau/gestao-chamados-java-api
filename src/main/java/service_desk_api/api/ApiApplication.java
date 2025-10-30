package service_desk_api.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
		System.out.println("Dá uma olhada nisto: " + new BCryptPasswordEncoder().encode("123456"));
	}
	
	public void run(String... args) throws Exception {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String senhaCriptografada = encoder.encode("123456");
		System.out.println("Senha criptografada: " + senhaCriptografada);
	}

}
