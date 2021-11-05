package com.bluebank.services;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.bluebank.dto.ClienteDTO;
import com.bluebank.repository.ClienteRepository;
import com.bluebank.service.ClienteService;
import com.bluebank.service.exceptions.ResourceNotFoundException;

@SpringBootTest
@Transactional
public class ClienteServiceIT {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
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
	public void findAllShouldReturnPageWhenPage0Size10() {
		
		PageRequest pageRequest = PageRequest.of(0, 10);
		
		Page<ClienteDTO> result = clienteService.findAll(pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(result.getNumber(), 0);
		Assertions.assertEquals(result.getSize(), 10);
		Assertions.assertEquals(countTotalClientes, result.getTotalElements());
	}
	
	
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
		Assertions.assertEquals("Joao da Silva", result.getContent().get(0).getNomeCompleto());
		Assertions.assertEquals("Marcos Vinicius", result.getContent().get(1).getNomeCompleto());
	}
	
	@Test
	public void deleteShouldThrowDataIntegrityViolationExceptionWhenIdExists() {
		//clienteService.delete(existingId);
	//	Assertions.assertEquals(countTotalClientes-1, clienteRepository.count());
		
		Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
			clienteService.delete(existingId);
			
		});
		
	}
	
	@Test
	public void deleteShouldthrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			clienteService.delete(nonExistingId);
		});
		
	}
}
