package service_desk_api.api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
	
	@Test
	void deveBuscarChamadoPorIdComSucesso() {
		
		Chamado chamado = Chamado.builder()
				.id(1L)
				.titulo("Teste")
				.descricao("Chamado de teste")
				.status(Status.ABERTO)
				.build();
		
		when(chamadoRepository.findById(1L)).thenReturn(Optional.of(chamado));
		
		Optional<Chamado> resultado = chamadoService.buscarPorId(1L);
		
		assertTrue(resultado.isPresent());
		assertEquals("Teste", resultado.get().getTitulo());
		
		verify(chamadoRepository, times(1)).findById(1L);
	}
	

}
