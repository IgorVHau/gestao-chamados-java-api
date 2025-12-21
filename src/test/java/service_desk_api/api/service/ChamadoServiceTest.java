package service_desk_api.api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import service_desk_api.api.exception.BusinessException;
import service_desk_api.api.model.Chamado;
import service_desk_api.api.model.Status;
import service_desk_api.api.repository.ChamadoRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChamadoServiceTest {
	
	@Mock
	private ChamadoRepository chamadoRepository;
	
	@InjectMocks
	private ChamadoService chamadoService;
	
	Chamado criarChamado() {
		
		Chamado chamado = Chamado.builder()
				.id(1L)
				.titulo("Teste")
				.descricao("Chamado de teste")
				.status(Status.ABERTO)
				.build();
		
		return chamado;
	}
	
	@DisplayName(value = "Deve retornar chamado ao buscar por id existente")
	@Test
	void deveRetornarChamadoQuandoBuscarPorIdExistente() {
		
		Chamado chamado = criarChamado();
		
		when(chamadoRepository.findById(1L)).thenReturn(Optional.of(chamado));
		
		Optional<Chamado> resultado = chamadoService.buscarPorId(1L);
		
		assertTrue(resultado.isPresent());
		assertEquals("Teste", resultado.get().getTitulo());
		
		verify(chamadoRepository, times(1)).findById(1L);
	}
	
	@DisplayName(value = "Deve retornar Optional.empty ao buscar chamado inexistente")
	@Test
	void deveRetornarOptionalVazioQuandoBuscarPorIdInexistente() {
		
		when(chamadoRepository.findById(99L)).thenReturn(Optional.empty());
		
		Optional<Chamado> resultado = chamadoService.buscarPorId(99L);
		
		assertTrue(resultado.isEmpty());
		
		verify(chamadoRepository, times(1)).findById(99L);
	}
	
	@DisplayName(value = "Deve lançar BusinessException quando tentar atualizar chamado concluído")
	@Test
	void deveLancarBusinessExceptionAoAtualizarChamadoConcluido() {
		Chamado chamado = new Chamado();
		chamado.setStatus(Status.CONCLUIDO);
		
		assertThrows(
				BusinessException.class,
				() -> chamadoService.validarAtualizacao(chamado));
	}

}
