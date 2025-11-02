package service_desk_api.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import service_desk_api.api.service.ChamadoService;
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
	public ResponseEntity<List<Chamado>> listar() {
		return ResponseEntity.ok(service.listarTodos());
	}
	
	@GetMapping("/{id}")
//	public Chamado buscarPorId(@PathVariable Long id) {
//		return service.buscarPorId(id)
//				.orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
//	}
	public ResponseEntity<Chamado> buscarPorId(@PathVariable Long id) {
		Chamado usuarioEncontrado = service.buscarPorId(id)
				.orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
		return ResponseEntity.ok(usuarioEncontrado);
	}

	@PostMapping
//	public Chamado criar(@RequestBody @Valid Chamado chamado) {
//		return service.criar(chamado);
//	}
	public ResponseEntity<Chamado> criar(@RequestBody @Valid Chamado chamado) {
		chamado.setCriadoEm(LocalDateTime.now());
		chamado.setAtualizadoEm(LocalDateTime.now());
		var chamadoCriado = service.criar(chamado);
		return ResponseEntity.status(HttpStatus.CREATED).body(chamadoCriado);
	}
	
	@PutMapping("/{id}")
//	public Chamado atualizar(@PathVariable Long id, @RequestBody @Valid Chamado novoChamado) {
//		return service.atualizar(id, novoChamado);
//	}
	public ResponseEntity atualizar(@PathVariable Long id, @RequestBody @Valid Chamado novoChamado) {
		var novoChamadoAtualizado = service.atualizar(id, novoChamado);
		novoChamadoAtualizado.setAtualizadoEm(LocalDateTime.now());
		return ResponseEntity.ok(novoChamadoAtualizado);
	}
	
	@DeleteMapping("/{id}")
//	public void deletar(@PathVariable Long id) {
//		service.deletar(id);
//	}
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		try {
			service.buscarPorId(id).orElseThrow(() -> new RuntimeException());
			service.deletar(id);
			return ResponseEntity.ok("Chamado deletado!");
		} catch (RuntimeException re) {
			return ResponseEntity.notFound().build();
		}
	}
	
}
