package service_desk_api.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service_desk_api.api.model.Usuario;
import service_desk_api.api.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	//private Usuario usuario;
	private final UsuarioRepository repository;
	
	public UsuarioService(UsuarioRepository repository) {
		this.repository = repository;
	}
	
	public Usuario save(Usuario usuario) {
		System.out.println("Cheguei aqui no save do UsuarioService. Olha o email dele: " + usuario.getEmail());
		return repository.save(usuario);
	}

}
