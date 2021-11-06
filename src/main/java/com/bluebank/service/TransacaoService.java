package com.bluebank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluebank.dto.TransacaoDTO;
import com.bluebank.entities.Conta;
import com.bluebank.entities.Transacao;
import com.bluebank.mapper.TransacaoMapper;
import com.bluebank.repository.ContaRepository;
import com.bluebank.repository.TransacaoRepository;
import com.bluebank.service.exceptions.ResourceNotFoundException;

@Service
public class TransacaoService {
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@Autowired
	private ContaService contaService;
	
	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private TransacaoMapper transacaoMapper;
	
	@Transactional(readOnly = true)
	public Page<TransacaoDTO> findAll(Pageable pageable) {
		
		return transacaoRepository
				.findAll(pageable)
				.map(transacao -> transacaoMapper.toDto(transacao));
	}

	@Transactional(readOnly = true)
	public TransacaoDTO findById(Long id) {
		
		return transacaoMapper.toDto(transacaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Transacao não encontrada! Id = " + id)));
	}

	@Transactional
	public TransacaoDTO updateStatus(Long id, TransacaoDTO dto) {
			Transacao transacao = transacaoRepository.findById(id).orElseThrow(() ->
			new ResourceNotFoundException("Transacao não encontrada! Id = " + id));
			transacao.setStatus(dto.getStatus());

			return transacaoMapper
					.toDto(transacaoRepository
							.save(transacao));
	}
	
	@Transactional
	public TransacaoDTO transferFunds(Long origemId, Long destinoId, Double montante, String tipoTransacao) {
		Conta contaOrigem = contaRepository.findById(origemId)
				.orElseThrow(() -> new ResourceNotFoundException("Conta não encontrado! Id = " + origemId));;
		Conta contaDestino = contaRepository.findById(destinoId)
				.orElseThrow(() -> new ResourceNotFoundException("Conta não encontrado! Id = " + destinoId));;
		
		contaService.isAmountValid(montante);
		contaService.hasLimit(contaOrigem, montante);
		contaOrigem = contaService.withdraw(contaOrigem, montante);
		contaDestino = contaService.deposit(contaDestino, montante);
		
		return transacaoMapper.toDto(transacaoRepository
				.save(transacaoMapper
						.createPrePersistenceEntityWithProvidedAccounts(
								montante,
								tipoTransacao,
								contaOrigem,
								contaDestino
								)
						)
				);
	}
}
