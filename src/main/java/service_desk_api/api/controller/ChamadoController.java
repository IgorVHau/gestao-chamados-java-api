package service_desk_api.api.controller;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import service_desk_api.api.service.ChamadoService;
import service_desk_api.api.model.Chamado;

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
	public List<Chamado> listar(){
		return service.listarTodos();
	}
	
	@GetMapping("/{id}")
	public Chamado buscarPorId(@PathVariable Long id) {
		return service.buscarPorId(id)
				.orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
	}

	@PostMapping
	public Chamado criar(@RequestBody @Valid Chamado chamado) {
		return service.criar(chamado);
	}
	
	@PutMapping("/{id}")
	public Chamado atualizar(@PathVariable Long id, @RequestBody @Valid Chamado novoChamado) {
		return service.atualizar(id, novoChamado);
	}
	
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable Long id) {
		service.deletar(id);
	}
	
}
