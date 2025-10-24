package service_desk_api.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chamado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O título é obrigatório.")
	@Size(max = 100, message = "O título deve ter no máximo 100 caractéres.")
	private String titulo;
	
	@NotBlank(message = "A descrição é obrigatória.")
	private String descricao;
	
	@Enumerated(EnumType.STRING)
	private Status status;

}
