package service_desk_api.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import service_desk_api.api.config.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig { //extends WebSecurityConfiguration {
	
	private final JwtAuthenticationFilter jwtAuthFilter;
	private final UserDetailsService userDetailsService;
	
	public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, UserDetailsService userDetailsService) {
		this.jwtAuthFilter = jwtAuthFilter;
		this.userDetailsService = userDetailsService;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//authenticationManager(http);
		http
			.csrf(csrf -> csrf.disable()) // desativa CSRF para API RESTs
			.headers(headers -> headers.frameOptions(Customizer.withDefaults()).disable())
			.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(authenticationProvider(userDetailsService,passwordEncoder()))
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
			.authorizeHttpRequests(auth -> auth
					//.requestMatchers("/h2-console/**","/auth/login").permitAll() // libera login
					.requestMatchers("/h2-console/**","/auth/login").permitAll()
					.requestMatchers(HttpMethod.GET, "/chamados/**").hasAnyRole("USER", "ADMIN")
					.requestMatchers(HttpMethod.POST, "/chamados/**").hasAnyRole("ADMIN")
					.requestMatchers(HttpMethod.PUT, "/chamados/**").hasAnyRole("ADMIN")
					.requestMatchers(HttpMethod.DELETE, "/chamados/**").hasAnyRole("ADMIN")
					.anyRequest().authenticated() //exige autenticação nos demais
					);
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//		return config.getAuthenticationManager();
//	}
	
//	public AuthenticationManager authenticationManager(
//			HttpSecurity http, 
//			BCryptPasswordEncoder bCryptPasswordEncoder,
//			UserDetailsService userDetailsService
//			) throws Exception {		
//		return http.getSharedObject(AuthenticationManagerBuilder.class)
//				.userDetailsService(userDetailsService)
//				.passwordEncoder(bCryptPasswordEncoder).;
//	}
	
	@Bean
	//public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		//return http.getSharedObject(AuthenticationManager.class);
	//}
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, 
			PasswordEncoder passwordEncoder) {
		System.out.println("AuthenticationProvider aqui");
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}

}
