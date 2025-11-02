package service_desk_api.api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import service_desk_api.api.repository.UsuarioRepository;
import service_desk_api.api.service.UsuarioService;
import service_desk_api.api.dto.LoginRequest;
import service_desk_api.api.model.Usuario;
import service_desk_api.api.dto.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	//private UsuarioRepository repository;
	private UsuarioService service;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public AuthController(
			//UsuarioRepository repository, 
			UsuarioService service) {
		//this.repository = repository;
		this.service = service;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
				);
			
			String token = jwtUtil.generateToken(request.getEmail());
			System.out.println("Token gerado: " + token);
			return ResponseEntity.ok(Collections.singletonMap("token", token));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Collections.singletonMap("erro", "Credenciais inválidas"));
		}
	}
	
	@GetMapping("/login")
	public ResponseEntity<?> criptografar(@RequestBody String senha) {
		try {
			return ResponseEntity.ok(Collections.singletonMap("senha", new BCryptPasswordEncoder().encode(senha)));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Collections.singletonMap("mensagem", "chamada não válida"));
		}
	}
	
	public void save() {
		
	}
	
//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody LoginRequest request){
//		try {
//			authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(
//							request.getEmail(), request.getSenha()));
			
			
//			String token = jwtUtil.generateToken(request.getEmail());
//			
//			return ResponseEntity.ok(token);
//		} catch (Exception e) {
//			return ResponseEntity.status(401).body("Credenciais inválidas!");
//		}
		
//		var usuario = repository.findByEmail(request.getEmail());
//		
//		if(usuario == null || !usuario.getSenha().equals(request.getSenha())) {
//			return ResponseEntity.status(401).body("Credenciais inválidas");
//		}
		
		
		
		//String token = "oi";
		//String token = jwtUtil.extractEmail(request.getEmail());
//		String token = jwtUtil.generateToken(request.getEmail());
//		
//		return ResponseEntity.ok(token);
//	}

}
