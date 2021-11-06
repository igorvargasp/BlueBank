package com.bluebank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluebank.dto.ContaDTO;
import com.bluebank.entities.Conta;
import com.bluebank.mapper.ContaMapper;
import com.bluebank.repository.ContaRepository;
import com.bluebank.service.exceptions.DatabaseException;
import com.bluebank.service.exceptions.InsufficientFundsException;
import com.bluebank.service.exceptions.ResourceNotFoundException;

@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private ContaMapper contaMapper;

	@Transactional(readOnly = true)
	public Page<ContaDTO> findAll(Pageable pageable) {
		return contaRepository.findAll(pageable).map(conta -> contaMapper.toDto(conta));
	}

	@Transactional(readOnly = true)
	public ContaDTO findById(Long id) {
		return contaMapper.toDto(contaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Conta não encontrado! Id = " + id)));
	}

	@Transactional
	public ContaDTO insert(ContaDTO dto) {
		return contaMapper.toDto(contaRepository.save(contaMapper.toEntity(dto)));
	}

	@Transactional
	public ContaDTO update(Long id, ContaDTO dto) {
			Conta conta = contaRepository.findById(id).orElseThrow(() ->
			new ResourceNotFoundException("Conta não encontrado! Id = " + id));
			dto.setId(conta.getId());
			return contaMapper
					.toDto(contaRepository
							.save(contaMapper
									.toEntity(dto)));
	}

	public void delete(Long id) {
		try {
			contaRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Conta não encontrado! Id = " + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	public boolean hasLimit(ContaDTO conta, Double montante) {
		double limite = conta.getSaldo() + conta.getLimiteCredito();
		if ( limite >= montante) {
			return true;
		}
		throw new InsufficientFundsException("Este montante excede seu limite.");
	}
	
	public ContaDTO withdraw (ContaDTO conta, Double montante) {
		double saldo = conta.getSaldo();
		double limite = conta.getLimiteCredito();
		
		if (montante > saldo) {
			double aux = montante - saldo;
			conta.setSaldo(0.0);
			conta.setLimiteCredito(limite - aux);
			return conta;
		}
		conta.setSaldo(saldo - montante);
		
		return conta;
	}

	public ContaDTO deposit(ContaDTO contaDestino, Double montante) {
		contaDestino.setSaldo(contaDestino.getSaldo() + montante);
		return contaDestino;
	}
}
