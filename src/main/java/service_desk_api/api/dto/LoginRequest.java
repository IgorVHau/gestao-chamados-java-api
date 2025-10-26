package service_desk_api.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
	
	@Email(message = "E-mail inválido.")
	private String email;
	
	@NotBlank(message = "A senha é obrigatória.")
	private String senha;
	
	public LoginRequest() {}
	
	public LoginRequest(String email, String senha) {
		this.email = email;
		this.senha = senha;
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

}
