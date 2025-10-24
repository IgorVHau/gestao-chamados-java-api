package service_desk_api.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable()) // desativa CSRF para API RESTs
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/auth/**").permitAll() // libera login
					.anyRequest().authenticated() //exige autenticação nos demais
					);
		return http.build();
	}

}
