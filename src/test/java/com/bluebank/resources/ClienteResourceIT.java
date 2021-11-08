package com.bluebank.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClienteResourceIT {
	
	@Autowired
	private MockMvc mockMvc;
	
	private Long countTotalClientes;
	
	@BeforeEach
	void setup() throws Exception {
		countTotalClientes = 4L;
	}
	
	@Test
	public void findAllShouldReturnSortedPageWhenSortByName() throws Exception{
		
		ResultActions result = mockMvc.perform(get("/clientes?page=0&size=12&sort=nomeCompleto,asc")
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.totalElements").value(countTotalClientes));
		result.andExpect(jsonPath("$.content").exists());
		result.andExpect(jsonPath("$.content[0].nomeCompleto").value("Calebe Marcos Manoel Farias"));
		result.andExpect(jsonPath("$.content[1].nomeCompleto").value("Gustavo Benício Cauã da Silva"));
	}
}
