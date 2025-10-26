package service_desk_api.api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import service_desk_api.api.repository.UsuarioRepository;
import service_desk_api.api.dto.LoginRequest;
import service_desk_api.api.model.Usuario;
import service_desk_api.api.dto.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final UsuarioRepository repository;
	
	//@Autowired
	//private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public AuthController(UsuarioRepository repository) {
		this.repository = repository;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request){
		try {
//			authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(
//							request.getEmail(), request.getSenha()));
			
			
			String token = jwtUtil.generateToken(request.getEmail());
			
			return ResponseEntity.ok(token);
		} catch (Exception e) {
			return ResponseEntity.status(401).body("Credenciais inválidas!");
		}
		
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
	}

}
