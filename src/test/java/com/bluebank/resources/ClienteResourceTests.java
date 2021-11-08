package com.bluebank.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.bluebank.service.exceptions.ResourceNotFoundException;
import com.bluebank.tests.Factory;

@WebMvcTest(ClienteController.class)
public class ClienteResourceTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ClienteService clienteService;
	
	@MockBean
	private ClienteRepository clienteRepository;

	@MockBean
	private ContaRepository contaRepository;

	private Long existingId;
	private Long nonExistingId;
	private ClienteDTO clienteDTO;
	private PageImpl<ClienteDTO> page;

	@BeforeEach
	void setUp() throws Exception {

		existingId = 1l;
		nonExistingId = 2l;

		clienteDTO = Factory.createClienteDTO();
		page = new PageImpl<>(List.of(clienteDTO));

		when(clienteService.findAll(any())).thenReturn(page);

		when(clienteService.findById(existingId)).thenReturn(clienteDTO);
		when(clienteService.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

		when(clienteService.insert(clienteDTO)).thenReturn(clienteDTO);
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
