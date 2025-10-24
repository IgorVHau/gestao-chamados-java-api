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

}
