package service_desk_api.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import service_desk_api.api.exception.BusinessException;
import service_desk_api.api.exception.ResourceNotFoundException;
import service_desk_api.api.service.ChamadoService;

@WebMvcTest(
		controllers = ChamadoController.class,
		excludeAutoConfiguration = SecurityAutoConfiguration.class
		)
@Import(TestSecurityConfig.class)
class ChamadoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ChamadoService chamadoService;
	
	@DisplayName(value = "Deve retornar 404 quando chamado não existir")
	@Test
	void deveRetornar404QuandoBuscarPorIdInexistente() throws Exception{
		
		when(chamadoService.buscarPorIdOuFalhar(99L)).thenThrow(new ResourceNotFoundException("Chamado não encontrado."));
		
		mockMvc.perform(get("/chamados/99"))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.timestamp").isNotEmpty())
			.andExpect(jsonPath("$.status").value(404))
			.andExpect(jsonPath("$.message").value("Chamado não encontrado."))
			.andExpect(jsonPath("$.data").isEmpty());
	}
	
	@DisplayName(value = "Deve retornar 422 ao tentar atualizar chamado concluído")
	@Test
	void deveRetornar422QuandoAtualizarStatusConcluido() throws Exception{
		
		when(chamadoService.atualizar(anyLong(), any())).thenThrow(new BusinessException("Chamado concluído não pode ser alterado."));
		
		mockMvc.perform(put("/chamados/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
							"titulo": "Teste",
							"descricao": "Teste",
							"status": "CONCLUIDO"
						}
						"""
				))
		.andExpect(status().isUnprocessableEntity())
		.andExpect(jsonPath("$.status").value(422))
		.andExpect(jsonPath("$.message").value("Chamado concluído não pode ser alterado."))
		.andExpect(jsonPath("$.data").isEmpty());
	}

}
