package com.bluebank.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluebank.dto.ClienteDTO;
import com.bluebank.entities.Cliente;
import com.bluebank.mapper.ClienteMapper;
import com.bluebank.repository.ClienteRepository;
import com.bluebank.service.exceptions.BusinessException;
import com.bluebank.service.exceptions.DatabaseException;
import com.bluebank.service.exceptions.ResourceNotFoundException;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ClienteMapper clienteMapper;

	@Transactional(readOnly = true)
	public Page<ClienteDTO> findAll(Pageable pageable) {
		
		return clienteRepository.findAll(pageable).map(cliente -> clienteMapper.toDto(cliente));
	}

	@Transactional(readOnly = true)
	public ClienteDTO findById(Long id) {
		
		return clienteMapper.toDtoWithContas(clienteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado! Id = " + id)));
	}
	
	@Transactional(readOnly = true)
	public ClienteDTO findClienteByContaId(Long id) {
		return new ClienteDTO();
	}
	
	@Transactional
	public ClienteDTO insert(ClienteDTO dto) {
				isClient(dto.getCpf());
				isCpfValido(dto.getCpf());
				dto.setCriadoEm(Instant.now());
				dto.setAtualizadoEm(Instant.now());
				
		return clienteMapper.toDto(clienteRepository.save(clienteMapper.toEntity(dto)));
	}

	@Transactional
	public ClienteDTO update(Long id, ClienteDTO dto) {
			Cliente cliente = clienteRepository.findById(id).orElseThrow(() ->
			new ResourceNotFoundException("Cliente não encontrado! Id = " + id));
			dto.setId(cliente.getId());
			dto.setCpf(cliente.getCpf());
			dto.setCriadoEm(cliente.getCriadoEm());
			dto.setAtualizadoEm(Instant.now());
			
			return clienteMapper
					.toDto(clienteRepository
							.save(clienteMapper
									.toEntity(dto)));
	}

	public void delete(Long id) {
		try {
			clienteRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			
			throw new ResourceNotFoundException("Cliente não encontrado! Id = " + id);
		}
		catch (DataIntegrityViolationException e) {
			
			throw new DatabaseException("Integrity violation");
		}
	}
	
	private boolean isCpfValido(String cpf) { 
		CPFValidator cpfValidator = new CPFValidator(); 
		List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(cpf); 
		if(erros.size() > 0) {
			throw new BusinessException("CPF inválido!");
		}
		
		return true;
	}
	
	private void isClient(String cpf) {
		if(clienteRepository.existsByCpf(cpf)) {
			throw new BusinessException("Cliente já cadastrado!");
		}
	}
}
