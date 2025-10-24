package service_desk_api.api.service;

import org.springframework.stereotype.Service;
import service_desk_api.api.model.Chamado;
import service_desk_api.api.repository.ChamadoRepository;

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
	
	public Chamado criar(Chamado chamado) {
		return repository.save(chamado);
	}
	
	public Chamado atualizar(Long id, Chamado novoChamado) {
		return repository.findById(id)
				.map(c -> {
					c.setTitulo(novoChamado.getTitulo());
					c.setDescricao(novoChamado.getDescricao());
					c.setStatus(novoChamado.getStatus());
					return repository.save(c);
				})
				.orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
	}
	
	public void deletar(Long id) {
		repository.deleteById(id);
	}

}
