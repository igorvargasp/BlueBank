package com.bluebank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluebank.dto.TransacaoDTO;
import com.bluebank.entities.Transacao;
import com.bluebank.entities.enums.StatusTransacao;
import com.bluebank.mapper.TransacaoMapper;
import com.bluebank.repository.TransacaoRepository;
import com.bluebank.service.exceptions.BusinessException;
import com.bluebank.service.exceptions.ResourceNotFoundException;

@Service
public class TransacaoService {
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@Autowired
	private TransacaoMapper transacaoMapper;
	
	@Autowired
	private ContaService contaService;
	
	@Transactional(readOnly = true)
	public Page<TransacaoDTO> findAll(Pageable pageable) {
		
		return transacaoRepository
				.findAll(pageable)
				.map(transacao -> transacaoMapper.toDto(transacao));
	}

	@Transactional(readOnly = true)
	public TransacaoDTO findByTransacaoId(Long id) {
		
		return transacaoMapper.toDto(transacaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada! Id = " + id)));
	}
	
	@Transactional(readOnly = true)
	public Page<TransacaoDTO> findByContaOrigemId(Pageable pageable, Long id) {
		
		return transacaoRepository.findByContaOrigemId(pageable, id)
				.map(transacao -> transacaoMapper.toDto(transacao));
	}

	@Transactional
	public TransacaoDTO updateStatus(Long id, TransacaoDTO dto) {
			Transacao transacao = transacaoRepository.findById(id).orElseThrow(() ->
			new ResourceNotFoundException("Transacao não encontrada! Id = " + id));
			if (dto.getStatus() == StatusTransacao.CANCELADA) {
				transferFunds(
						transacao.getContaDestino().getId(),
						transacao.getContaOrigem().getId(),
						transacao.getMontante(),
						transacao.getTipo().getCod()
						);
			}
			if (transacao.getStatus() == StatusTransacao.CANCELADA) {
				throw new BusinessException("Transação já foi cancelada!");
			}
			transacao.setStatus(dto.getStatus().getCod());
			return transacaoMapper
					.toDto(transacaoRepository
							.save(transacao));
	}
	
	@Transactional
	public TransacaoDTO transferFunds(Long contaOrigemId, Long contaDestinoId, Double montante, Integer tipoTransacao) {
		
		return contaService.transferFunds(contaOrigemId, contaDestinoId, montante, tipoTransacao);
	}
}
