package service_desk_api.api.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import service_desk_api.api.model.Chamado;
import service_desk_api.api.model.Chamado.ChamadoBuilder;
import service_desk_api.api.model.Status;
import service_desk_api.api.model.Usuario;
import service_desk_api.api.repository.ChamadoRepository;
import service_desk_api.api.repository.UsuarioRepository;

@Configuration
public class DataLoader {
	
	@Bean
	CommandLineRunner initDatabase(UsuarioRepository repository, ChamadoRepository chamadoRepository, PasswordEncoder encoder) {
		return args -> {
			if(repository.findByEmail("admin@email.com").isEmpty()) {
				Usuario admin = new Usuario(
						"Fernando",
						"admin@email.com", 
						encoder.encode("123"),
						"ADMIN"
						);
				repository.save(admin);
				System.out.println("ADMINISTRADOR CRIADO COM SUCESSO!!!");
			}
			if(repository.findByEmail("user@email.com").isEmpty()) {
				Usuario user = new Usuario(
						"Jorge",
						"user@email.com",
						encoder.encode("1234"),
						"USER"
						);
				repository.save(user);
				System.out.println("USUÁRIO CRIADO COM SUCESSO!!!");
			}
			if(chamadoRepository.findAll().isEmpty()) {
				Chamado chamado = Chamado.builder()
					.titulo("Acesso negado ao servidor remoto")
					.descricao("O usuário Mário não consegue acessar o servidor da empresa Bowser cia. Conceder acesso ao encanador.")
					.status(Status.ABERTO)
					.criadoEm(LocalDateTime.now())
					.atualizadoEm(LocalDateTime.now())
					.build();
				chamadoRepository.save(chamado);
				System.out.println("CHAMADO CRIADO COM SUCESSO!!!");
			}
		};
		
	}

}
