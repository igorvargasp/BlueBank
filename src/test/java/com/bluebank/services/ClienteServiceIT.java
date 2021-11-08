package com.bluebank.services;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.bluebank.dto.ClienteDTO;
import com.bluebank.repository.ClienteRepository;
import com.bluebank.service.ClienteService;

@SpringBootTest
@Transactional
public class ClienteServiceIT {

	@Autowired
	private ClienteService clienteService;
	
	@SuppressWarnings("unused")
	@Autowired
	private ClienteRepository clienteRepository;

	@Test
	public void findAllShouldReturnPageWhenPageDoesNotExist() {
		
		PageRequest pageRequest = PageRequest.of(50, 10);
		
		Page<ClienteDTO> result = clienteService.findAll(pageRequest);
		
		Assertions.assertTrue(result.isEmpty());
	}
	
	@Test
	public void findAllShouldReturnSortedPageWhenPageSortByName() {
		
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("nomeCompleto"));
		
		
		Page<ClienteDTO> result = clienteService.findAll(pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals("Calebe Marcos Manoel Farias", result.getContent().get(0).getNomeCompleto());
		Assertions.assertEquals("Gustavo Benício Cauã da Silva", result.getContent().get(1).getNomeCompleto());
	}
}
