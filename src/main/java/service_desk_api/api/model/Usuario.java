package service_desk_api.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Email(message = "Formato de e-mail inválido.")
	@Column(unique = true)
	private String email;
	
	@NotBlank
	@Size(min = 6, message = "A senha deve ter no mínimo 6 caractéres.")
	private String senha;
	
	@NotBlank
	@Size(min = 2, max = 100, message = "O nome deve ter entre 2 a 100 caractéres.")
	private String nome;
	
	@NotBlank
	@Pattern(regexp = "^(USER|ADMIN)$", message = "A função deve ser USER ou ADMIN.")
	private String role;
	
	public Usuario(){
	}
	
	public Usuario(String nome, String email, String senha, String role) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.role = role;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
}
