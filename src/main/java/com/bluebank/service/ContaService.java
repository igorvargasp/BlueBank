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
import com.bluebank.entities.enums.StatusConta;
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
		
		return contaMapper.toDto(contaRepository.save(contaMapper.createPrePersistenceAccount(dto)));
	}

	@Transactional
	public ContaDTO updateLimit(Long id, ContaDTO dto) {
			Conta conta = contaRepository.findById(id).orElseThrow(() ->
			new ResourceNotFoundException("Conta não encontrada! Id = " + id));
			isAmountValid(dto.getLimiteCredito());
			isLimitAcceptable(conta.getCliente().getRendaMensal(), dto.getLimiteCredito());
			conta.setLimiteCredito(dto.getLimiteCredito());
			conta.setAtualizadoEm(Instant.now());
			return contaMapper
					.toDto(contaRepository
							.save(conta));
	}


	@Transactional
	public ContaDTO updateStatus(Long id, ContaDTO dto) {
			Conta conta = contaRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada! Id = " + id));
				conta.setAtualizadoEm(Instant.now());
			
				if (dto.getStatus() == StatusConta.BLOQUEADA) {
					conta.setStatus(StatusConta.BLOQUEADA.getCod());
					return contaMapper.toDto(contaRepository.save(conta));
				}
			
				if (dto.getStatus() == StatusConta.DESATIVADA && !hasBalance(conta)) {
					conta.setStatus(StatusConta.DESATIVADA.getCod());
					return contaMapper.toDto(contaRepository.save(conta));
				}
				
			conta.setStatus(dto.getStatus().getCod());
			return contaMapper.toDto(contaRepository.save(conta));
	}
	
	@Transactional
	public TransacaoDTO transferFunds(Long contaOrigemId, Long contaDestinoId, Double montante, Integer tipoTransacao) {
		Conta contaOrigem = contaRepository.findById(contaOrigemId)
				.orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada! Id = " + contaOrigemId));
		Conta contaDestino = contaRepository.findById(contaDestinoId)
				.orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada! Id = " + contaDestinoId));
		
		isAtiva(contaOrigem);
		isAtiva(contaDestino);
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
	
	private boolean hasBalance (Conta conta) {
		if (conta.getSaldo() == 0.0) {
			return true;
		}
		
		throw new BusinessException("Saldo precisa ser zero.");
	}
	
	private boolean isAtiva(Conta conta) {
		if (conta.getStatus() == StatusConta.BLOQUEADA) {
			throw new BusinessException("Conta: " + conta.getId() + ", Bloqueada!");
		}
		if (conta.getStatus() == StatusConta.DESATIVADA) {
			throw new BusinessException("Conta: " + conta.getId() + ", Desativada!");
		}
		return true;
	}
	
	private void isLimitAcceptable(Double rendaMensal, Double novoLimiteCredito) {
		if (novoLimiteCredito > 2 * rendaMensal) {
			throw new BusinessException("Limite máximo de crédito excedido!");
		}
	}
}
