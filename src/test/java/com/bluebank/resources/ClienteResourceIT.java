package com.bluebank.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.bluebank.dto.ClienteDTO;
import com.bluebank.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClienteResourceIT {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Long existingId;
	private Long nonExistingId;
	private Long countTotalClientes;
	

	@BeforeEach
	void setup() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalClientes = 2L;
	}
	
	@Test
	public void findAllShouldReturnSortedPageWhenSortByName() throws Exception{
		
		ResultActions result = mockMvc.perform(get("/clientes?page=0&size=12&sort=nomeCompleto,asc")
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.totalElements").value(countTotalClientes));
		result.andExpect(jsonPath("$.content").exists());
		result.andExpect(jsonPath("$.content[0].nomeCompleto").value("Joao da Silva"));
		result.andExpect(jsonPath("$.content[1].nomeCompleto").value("Marcos Vinicius"));
	}

	@Test
	public void updateShouldReturnClienteDTOWhenIdExists() throws Exception {
		
		ClienteDTO clienteDTO = Factory.createClienteDTO();
		String jsonBody = objectMapper.writeValueAsString(clienteDTO);
		
		String expectedName = clienteDTO.getNomeCompleto();
		String expectedEmail = clienteDTO.getEmail();
		
		ResultActions result = mockMvc.perform(put("/clientes/{id}", existingId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(existingId));
		result.andExpect(jsonPath("$.nomeCompleto").value(expectedName));
		result.andExpect(jsonPath("$.email").value(expectedEmail));
	}
	
	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
		
		ClienteDTO clienteDTO = Factory.createClienteDTO();
		String jsonBody = objectMapper.writeValueAsString(clienteDTO);
		
		ResultActions result = mockMvc.perform(put("/clientes/{id}", nonExistingId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
		
		result.andExpect(status().isNotFound());
	}
	
}
