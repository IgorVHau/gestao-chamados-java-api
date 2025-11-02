package service_desk_api.api.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
	@Size(max = 500, message = "A descrição deve ter no máximo 500 caractéres.")
	private String descricao;
	
	@NotNull(message = "O status deve ser preenchido.")
	@Enumerated(EnumType.STRING)
	private Status status;
	
	//@DateTimeFormat(pattern="dd/MM/yyyy hh:mm:ss")
	@JsonFormat(pattern="dd/MM/yyyy hh:mm:ss")
	@PastOrPresent(message = "A data de criação não pode estar no futuro.")
	private LocalDateTime criadoEm;
	
	//@DateTimeFormat(pattern="dd/MM/yyyy hh:mm:ss")
	@JsonFormat(pattern="dd/MM/yyyy hh:mm:ss")
	@PastOrPresent(message = "A data de atualização não pode estar no futuro.")
	private LocalDateTime atualizadoEm;
	
	public Chamado(String titulo, String descricao, Status status) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.status = status;
	}
	
	@PrePersist
	protected void onCreate() {
		this.criadoEm = LocalDateTime.now();
		this.atualizadoEm = LocalDateTime.now();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.atualizadoEm = LocalDateTime.now();
	}

}
