package com.bluebank.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.bluebank.controller.ClienteController;
import com.bluebank.dto.ClienteDTO;
import com.bluebank.repository.ClienteRepository;
import com.bluebank.repository.ContaRepository;
import com.bluebank.service.ClienteService;
import com.bluebank.service.exceptions.DatabaseException;
import com.bluebank.service.exceptions.ResourceNotFoundException;
import com.bluebank.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ClienteController.class)
public class ClienteResourceTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ClienteService clienteService;
	
	@MockBean
	private ClienteRepository clienteRepository;

	@MockBean
	private ContaRepository contaRepository;

	private Long existingId;
	private Long nonExistingId;
	private Long dependentId;
	private ClienteDTO clienteDTO;
	private PageImpl<ClienteDTO> page;

	@BeforeEach
	void setUp() throws Exception {

		existingId = 1l;
		nonExistingId = 2l;
		dependentId = 3L;

		clienteDTO = Factory.createClienteDTO();
		page = new PageImpl<>(List.of(clienteDTO));

		when(clienteService.findAll(any())).thenReturn(page);

		when(clienteService.findById(existingId)).thenReturn(clienteDTO);
		when(clienteService.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

		when(clienteService.update(eq(existingId), any())).thenReturn(clienteDTO);
		when(clienteService.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);

		doNothing().when(clienteService).delete(existingId);
		doThrow(ResourceNotFoundException.class).when(clienteService).delete(nonExistingId);
		doThrow(DatabaseException.class).when(clienteService).delete(dependentId);

		when(clienteService.insert(clienteDTO)).thenReturn(clienteDTO);
	}

	@Test
	public void deleteShouldDeleteResourceWhenIdExists() throws Exception {

		ResultActions result = mockMvc.perform(delete("/clientes/{id}", existingId).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNoContent());
	}

	@Test
	public void deleteShouldThrowResourceNotFoundException() throws Exception {

		ResultActions result = mockMvc.perform(delete("/clientes/{id}", nonExistingId).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
	}

	@Test
	public void insertShouldReturnClienteDTO() throws Exception {

		String jsonBody = objectMapper.writeValueAsString(clienteDTO);

		ResultActions result = mockMvc.perform(post("/clientes").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.nomeCompleto").exists());
		result.andExpect(jsonPath("$.email").exists());
	}

	@Test
	public void updateShouldReturnClienteDTOWhenIdExists() throws Exception {

		String jsonBody = objectMapper.writeValueAsString(clienteDTO);

		ResultActions result = mockMvc.perform(put("/clientes/{id}", existingId).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.nomeCompleto").exists());
		result.andExpect(jsonPath("$.email").exists());
		result.andExpect(jsonPath("$.dataNascimento").exists());
	}

	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {

		String jsonBody = objectMapper.writeValueAsString(clienteDTO);

		ResultActions result = mockMvc.perform(put("/clientes/{id}", nonExistingId).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
	}

	@Test
	public void findAllShouldReturnPage() throws Exception {
		ResultActions result = mockMvc.perform(get("/clientes").accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
	}

	@Test
	public void findByIdShouldReturnClienteWhenIdExists() throws Exception {
		ResultActions result = mockMvc.perform(get("/clientes/{id}", existingId).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.nomeCompleto").exists());
		result.andExpect(jsonPath("$.email").exists());
	}

	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
		ResultActions result = mockMvc.perform(get("/clientes/{id}", nonExistingId).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
	}

}
