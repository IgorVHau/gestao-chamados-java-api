package service_desk_api.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import service_desk_api.api.service.ChamadoService;
import service_desk_api.api.dto.ApiResponse;
import service_desk_api.api.model.Chamado;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import service_desk_api.api.service.ChamadoService;


@RestController
@RequestMapping("/chamados")
public class ChamadoController {
	
	private final ChamadoService service;
	
	public ChamadoController(ChamadoService service) {
		this.service = service;
	}
	
	@GetMapping
	//public List<Chamado> listar(){
		//return service.listarTodos();
	//}
	//public ResponseEntity<List<Chamado>> listar() {
		//return ResponseEntity.ok(service.listarTodos());
	//}
	public ResponseEntity<ApiResponse<List<Chamado>>> listar() {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(ApiResponse.success("Lista de chamados", service.listarTodos(), 200));
	}
	
	@GetMapping("/{id}")
//	public Chamado buscarPorId(@PathVariable Long id) {
//		return service.buscarPorId(id)
//				.orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
//	}
	public ResponseEntity<ApiResponse<Chamado>> buscarPorId(@PathVariable Long id) {
		//Chamado usuarioEncontrado = service.buscarPorId(id)
				//.orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
		//return ResponseEntity.ok(usuarioEncontrado);
		Chamado usuarioEncontrado = service.buscarPorId(id)
				.orElseThrow(() -> new RuntimeException("Chamado não encontrado."));
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(ApiResponse.success("Usuário encontrado.", usuarioEncontrado, 200));
	}

	@PostMapping
//	public Chamado criar(@RequestBody @Valid Chamado chamado) {
//		return service.criar(chamado);
//	}
	public ResponseEntity<ApiResponse<Chamado>> criar(@RequestBody @Valid Chamado chamado) {
		var chamadoCriado = service.criar(chamado);
		//return ResponseEntity.status(HttpStatus.CREATED).body(chamadoCriado);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(ApiResponse.success("Chamado criado com sucesso", chamadoCriado, 201));
	}
	
	@PutMapping("/{id}")
//	public Chamado atualizar(@PathVariable Long id, @RequestBody @Valid Chamado novoChamado) {
//		return service.atualizar(id, novoChamado);
//	}
	public ResponseEntity<ApiResponse<Chamado>> atualizar(@PathVariable Long id, @RequestBody @Valid Chamado novoChamado) {
		//var novoChamadoAtualizado = service.atualizar(id, novoChamado);
		//return ResponseEntity.ok(novoChamadoAtualizado);
		var novoChamadoAtualizado = service.atualizar(id, novoChamado);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(ApiResponse.success("Chamado atualizado com sucesso", novoChamadoAtualizado, 200));
	}
	
	@DeleteMapping("/{id}")
//	public void deletar(@PathVariable Long id) {
//		service.deletar(id);
//	}
	public ResponseEntity<ApiResponse<String>> deletar(@PathVariable Long id) {
//		try {
//			service.buscarPorId(id).orElseThrow(() -> new RuntimeException());
//			service.deletar(id);
//			return ResponseEntity.ok("Chamado deletado!");
//		} catch (RuntimeException re) {
//			return ResponseEntity.notFound().build();
//		}
		service.buscarPorId(id).orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
		service.deletar(id);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(ApiResponse.success("Chamado deletado com sucesso.", null, 200));
	}
	
}
