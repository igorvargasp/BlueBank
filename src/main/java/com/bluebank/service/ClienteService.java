package com.bluebank.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluebank.model.Cliente;
import com.bluebank.repository.ClienteRepository;

@Service
public class ClienteService {
	
	
	private ClienteRepository clienteRepository;
	
	@Autowired
	public ClienteService (ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	
	
	public List<Cliente> findAll() {
		
		return clienteRepository.findAll();
	}
	
	
	
	public Optional<Cliente> findAll(Integer id) {
		
		return clienteRepository.findById(id);
	}
	
	public void deletar(Integer id) {
		
		clienteRepository.deleteById(id);
	}
	
	public void save(Cliente cliente) {
		clienteRepository.save(cliente);
	}
	
}
