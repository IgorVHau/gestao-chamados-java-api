package service_desk_api.api.service;

import org.springframework.stereotype.Service;

import service_desk_api.api.exception.BusinessException;
import service_desk_api.api.exception.ResourceNotFoundException;
import service_desk_api.api.model.Chamado;
import service_desk_api.api.model.Status;
import service_desk_api.api.repository.ChamadoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {
	
	private final ChamadoRepository repository;
	
	public ChamadoService(ChamadoRepository repository) {
		this.repository = repository;
	}
	
	public List<Chamado> listarTodos() {
		return repository.findAll();
	}
	
	public Optional<Chamado> buscarPorId(Long id) {
		return repository.findById(id);
	}
	
	public Chamado buscarPorIdOuFalhar(Long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado."));
	}
	
	public Chamado criar(Chamado chamado) {
		System.out.println("Status: " + chamado.getStatus().toString());
		return repository.save(chamado);
	}
	
//	public Chamado atualizar(Long id, Chamado novoChamado) {
//		return repository.findById(id)
//				.map(c -> {
//					c.setTitulo(novoChamado.getTitulo());
//					c.setDescricao(novoChamado.getDescricao());
//					c.setStatus(novoChamado.getStatus());
//					return repository.save(c);
//				})
//				.orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
//	}
	
	public Chamado atualizar(Long id, Chamado novoChamado) {
//		var chamadoAtual = repository.findById(id)
//				.orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado."));
		
		var chamadoAtual = buscarPorIdOuFalhar(id);
		
		validarAtualizacao(chamadoAtual);
		
		chamadoAtual.setTitulo(novoChamado.getTitulo());
		chamadoAtual.setDescricao(novoChamado.getDescricao());
		chamadoAtual.setStatus(novoChamado.getStatus());
		var chamadoAtualizado = repository.save(chamadoAtual);
		return chamadoAtualizado;
	}
	
	public void validarAtualizacao(Chamado chamado) {
		if(chamado.getStatus() == Status.CONCLUIDO) {
			throw new BusinessException("Chamado concluído não pode ser alterado.");
		}
	}
	
	public void deletar(Long id) {
		repository.deleteById(id);
	}

}
