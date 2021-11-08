package com.bluebank.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bluebank.dto.ClienteDTO;
import com.bluebank.entities.Cliente;
import com.bluebank.entities.Conta;
import com.bluebank.mapper.ClienteMapper;
import com.bluebank.mapper.ContaMapper;
import com.bluebank.repository.ClienteRepository;
import com.bluebank.repository.ContaRepository;
import com.bluebank.service.ClienteService;
import com.bluebank.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ClienteServiceTest {

	@InjectMocks
	private ClienteService clienteService;

	@Mock
	private ClienteRepository clienteRepository;

	@Mock
	private ClienteMapper clienteMapper;
	
	@Mock
	private ContaMapper contaMapper;

	@Mock
	private ContaRepository contaRepository;

	private long existingId;
	private long nonExistingId;
	private long dependentId;
	private PageImpl<Cliente> page;
	private Cliente cliente;
	private Conta conta;
	private ClienteDTO clienteDTO;

	@BeforeEach
	void setup() throws Exception {
		existingId = 1L;
		nonExistingId = 2L;
		dependentId = 3L;
		cliente = Factory.createCliente();
		clienteDTO = Factory.createClienteDTO();
		conta = Factory.createConta();
		page = new PageImpl<>(List.of(cliente));
		
		Mockito.when(clienteMapper.toDtoWithContas(cliente)).thenReturn(clienteDTO);
		Mockito.when(clienteMapper.toDto(cliente)).thenReturn(clienteDTO);

		Mockito.when(clienteRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

		Mockito.when(clienteRepository.findById(existingId)).thenReturn(Optional.of(cliente));
		Mockito.when(clienteRepository.findById(nonExistingId)).thenReturn(Optional.empty());

		Mockito.when(clienteRepository.findById(existingId)).thenReturn(Optional.of(cliente));
		Mockito.when(clienteRepository.findById(nonExistingId)).thenThrow(EntityNotFoundException.class);

		Mockito.when(contaRepository.findById(existingId)).thenReturn(Optional.of(conta));
		Mockito.when(contaRepository.findById(nonExistingId)).thenThrow(EntityNotFoundException.class);

		Mockito.when(clienteRepository.save(ArgumentMatchers.any())).thenReturn(cliente);
		

		Mockito.doNothing().when(clienteRepository).deleteById(existingId);

		Mockito.doThrow(EmptyResultDataAccessException.class).when(clienteRepository).deleteById(nonExistingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(clienteRepository).deleteById(dependentId);
	}

	@Test
	public void updateShouldReturnClienteDTOWhenIdExists() {
		ClienteDTO dto = clienteMapper.toDtoWithContas(cliente);
		ClienteDTO result = clienteService.update(existingId, dto);
		
		Assertions.assertNotNull(result);

		Assertions.assertDoesNotThrow(() -> {
			clienteService.findById(existingId);
		});

		Mockito.verify(clienteRepository, Mockito.times(2)).findById(existingId);
	}

	@Test
	public void findByIdShouldReturnClienteDTOWhenIdExists() {
		ClienteDTO result = clienteService.findById(existingId);
		Assertions.assertNotNull(result);

		Assertions.assertDoesNotThrow(() -> {
			clienteService.findById(existingId);
		});

		Mockito.verify(clienteRepository, Mockito.times(2)).findById(existingId);
	}

	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			clienteService.findById(nonExistingId);
		});

		Mockito.verify(clienteRepository).findById(nonExistingId);
	}

	@Test
	public void findAllPagedShouldReturnPage() {
		Pageable pageable = PageRequest.of(0, 10);

		Page<ClienteDTO> result = clienteService.findAll(pageable);

		Assertions.assertNotNull(result);
		Mockito.verify(clienteRepository).findAll(pageable);
	}
}
