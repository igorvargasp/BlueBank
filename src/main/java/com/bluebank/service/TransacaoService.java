package com.bluebank.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluebank.dto.ContaDTO;
import com.bluebank.dto.TransacaoDTO;
import com.bluebank.entities.Transacao;
import com.bluebank.mapper.ContaMapper;
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
	
	@Autowired
	private ContaMapper contaMapper;

	@Transactional(readOnly = true)
	public Page<TransacaoDTO> findAll(Pageable pageable) {
		return transacaoRepository.findAll(pageable).map(transacao -> transacaoMapper.toDto(transacao));
	}

	@Transactional(readOnly = true)
	public TransacaoDTO findById(Long id) {
		return transacaoMapper.toDto(transacaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Transacao não encontrada! Id = " + id)));
	}

	@Transactional
	public TransacaoDTO insert(TransacaoDTO dto) {
		return transacaoMapper.toDto(transacaoRepository.save(transacaoMapper.toEntity(dto)));
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
		ContaDTO contaOrigem = contaService.findById(origemId);
		ContaDTO contaDestino = contaService.findById(destinoId);
		
		contaService.isAmountValid(montante);
		contaService.hasLimit(contaOrigem, montante);
		contaOrigem = contaService.withdraw(contaOrigem, montante);
		contaDestino = contaService.deposit(contaDestino, montante);
		
		contaRepository.saveAll(Arrays.asList(contaMapper.toEntity(contaDestino), contaMapper.toEntity(contaOrigem)));
		
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
