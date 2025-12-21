package service_desk_api.api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import service_desk_api.api.exception.BusinessException;
import service_desk_api.api.exception.ResourceNotFoundException;
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
	
	@DisplayName(value = "Deve lançar BusinessException ao validar atualização de chamado concluído")
	@Test
	void deveLancarBusinessExceptionAoAtualizarChamadoConcluido() {
		Chamado chamado = new Chamado();
		chamado.setStatus(Status.CONCLUIDO);
		
		assertThrows(
				BusinessException.class,
				() -> chamadoService.validarAtualizacao(chamado));
	}
	
	@DisplayName(value = "Deve atualizar chamado existente quando status não estiver concluído")
	@Test
	void deveAtualizarChamadoExistenteQuandoStatusNaoForConcluido() {
		
		Chamado chamadoExistente = Chamado.builder()
				.id(1L)
				.titulo("Título antigo")
				.descricao("Descrição antiga")
				.status(Status.ABERTO)
				.build();
		
		Chamado novoChamado = Chamado.builder()
				.titulo("Título novo")
				.descricao("Descrição nova")
				.status(Status.EM_ANDAMENTO)
				.build();
		
		when(chamadoRepository.findById(1L)).thenReturn(Optional.of(chamadoExistente));
		when(chamadoRepository.save(any(Chamado.class))).thenAnswer(invocation -> invocation.getArgument(0));
		
		Chamado resultado = chamadoService.atualizar(1L, novoChamado);
		
		assertNotNull(resultado);
		assertEquals("Título novo", resultado.getTitulo());
		assertEquals("Descrição nova", resultado.getDescricao());
		assertEquals(Status.EM_ANDAMENTO, resultado.getStatus());
		
		verify(chamadoRepository).findById(1L);
		verify(chamadoRepository).save(any(Chamado.class));
	}
	
	@DisplayName(value = "Deve lançar ResourceNotFoundException ao buscar por chamado inexistente")
	@Test
	void deveLancarResourceNotFoundExceptionAoBuscarChamadoInexistente() {
		
		when(chamadoRepository.findById(99L)).thenReturn(Optional.empty());
		
		ResourceNotFoundException exception = assertThrows(
				ResourceNotFoundException.class, () -> chamadoService.buscarPorIdOuFalhar(99L));
		
		assertEquals("Chamado não encontrado.", exception.getMessage());
		verify(chamadoRepository).findById(99L);
	}

}
