package com.bluebank.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluebank.dto.ContaDTO;
import com.bluebank.dto.TransacaoDTO;
import com.bluebank.entities.Conta;
import com.bluebank.entities.Transacao;
import com.bluebank.mapper.ContaMapper;
import com.bluebank.mapper.TransacaoMapper;
import com.bluebank.repository.ContaRepository;
import com.bluebank.repository.TransacaoRepository;
import com.bluebank.service.exceptions.BusinessException;
import com.bluebank.service.exceptions.InsufficientFundsException;
import com.bluebank.service.exceptions.InvalidDataException;
import com.bluebank.service.exceptions.ResourceNotFoundException;

@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private TransacaoRepository transacaoRepository;

	@Autowired
	private ContaMapper contaMapper;
	
	@Autowired
	private TransacaoMapper transacaoMapper; 

	@Transactional(readOnly = true)
	public Page<ContaDTO> findAll(Pageable pageable) {
		
		return contaRepository.findAll(pageable).map(conta -> contaMapper.toDto(conta));
	}

	@Transactional(readOnly = true)
	public Page<ContaDTO> findAllByClienteId (Pageable pageable, Long id){
		
		return contaRepository.findAllByClienteId(pageable, id).map(conta -> contaMapper.toDto(conta));
	}
	
	@Transactional(readOnly = true)
	public ContaDTO findById(Long id) {
		
		return contaMapper.toDto(contaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada! Id = " + id)));
	}
	

	@Transactional
	public ContaDTO insert(ContaDTO dto) {
		dto.setSaldo(0.0);
		dto.setLimiteCredito(0.0);
		return contaMapper.toDto(contaRepository.save(contaMapper.toEntity(dto)));
	}

	@Transactional
	public ContaDTO updateLimit(Long id, ContaDTO dto) {
			Conta conta = contaRepository.findById(id).orElseThrow(() ->
			new ResourceNotFoundException("Conta não encontrada! Id = " + id));
			conta.setLimiteCredito(dto.getLimiteCredito());
			
			return contaMapper
					.toDto(contaRepository
							.save(conta));
	}

	@Transactional
	public ContaDTO disable(Long id) {
			ContaDTO contaDTO = findById(id);
			if (hasBalance(contaDTO)) {
				throw new BusinessException("Saldo precisa ser zero.");
			}
			contaDTO.setStatus("Desativada");
			contaDTO.setAtualizadoEm(Instant.now());
			
			return contaMapper.toDto(contaRepository.save(contaMapper.toEntity(contaDTO)));
	}
	
	@Transactional
	public TransacaoDTO transferFunds(Long contaOrigemId, Long contaDestinoId, Double montante, String tipoTransacao) {
		Conta contaOrigem = contaRepository.findById(contaOrigemId)
				.orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada! Id = " + contaOrigemId));
		Conta contaDestino = contaRepository.findById(contaDestinoId)
				.orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada! Id = " + contaDestinoId));
		
		isAmountValid(montante);
		hasLimit(contaOrigem, montante);
		contaOrigem = withdraw(contaOrigem, montante);
		contaDestino = deposit(contaDestino, montante);
		
		Transacao newTransacao =  transacaoMapper.createPrePersistenceEntityWithProvidedAccounts(
						montante,
						tipoTransacao,
						contaOrigem,
						contaDestino);
		
				return transacaoMapper.toDto(transacaoRepository.save(newTransacao));
	}
	
	private boolean hasLimit(Conta conta, Double montante) {
		double limite = conta.getSaldo() + conta.getLimiteCredito();
		if ( limite >= montante) {
			return true;
		}
		
		throw new InsufficientFundsException("Este montante excede seu limite.");
	}
	
	private Conta withdraw (Conta contaOrigem, Double montante) {
		double saldo = contaOrigem.getSaldo();
		
		if (montante > saldo) {
			contaOrigem.setSaldo(saldo - montante);
			
			return contaOrigem;
		}
		contaOrigem.setSaldo(saldo - montante);
		
		return contaOrigem;
	}

	private Conta deposit(Conta contaDestino, Double montante) {
		contaDestino.setSaldo(contaDestino.getSaldo() + montante);
		
		return contaDestino;
	}

	private boolean isAmountValid(Double montante) {
		if (montante <= 0.0) {
			throw new InvalidDataException("O valor deve ser maior que zero.");
		}
		
		return true;
	}
	
	private boolean hasBalance (ContaDTO conta) {
		if (conta.getSaldo() == 0.0) {
			return false;
		}
		
		return true;
	}
}
