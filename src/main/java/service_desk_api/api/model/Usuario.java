package service_desk_api.api.model;

import jakarta.persistence.*;

public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String email;
	
	private String senha;
	
	private String nome;
}
