package com.bluebank.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.bluebank.entities.Cliente;
import com.bluebank.repository.ClienteRepository;
import com.bluebank.tests.Factory;

@DataJpaTest
public class ClienteRepositoryTests {

	@Autowired
	private ClienteRepository clienteRepository;
	private long existingId;
	private long nonExistingId;
	private long countTotalClientes;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalClientes = 4L;
	}

	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		clienteRepository.deleteById(existingId);

		Optional<Cliente> result = clienteRepository.findById(existingId);
		Assertions.assertFalse(result.isPresent());
	}

	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
		Cliente cliente = Factory.createCliente();
		
		cliente.setId(null);
		cliente = clienteRepository.save(cliente);
		
		Assertions.assertNotNull(cliente);
		Assertions.assertEquals(countTotalClientes + 1, cliente.getId());
	}

	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionObjectWhenIdDoesNotExists() {
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			clienteRepository.deleteById(nonExistingId);
		});
	}

	@Test
	public void findByIdShouldReturnClienteOptionalWhenExistingId() {
		Optional<Cliente> cliente = clienteRepository.findById(countTotalClientes);
		Assertions.assertTrue(cliente.isPresent());
	}
}
