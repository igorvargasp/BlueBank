package com.bluebank.mapper;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bluebank.dto.TransacaoDTO;
import com.bluebank.entities.Conta;
import com.bluebank.entities.Transacao;
import com.bluebank.service.ContaService;

@Component
public class TransacaoMapper {

	@Autowired
	ContaMapper contaMapper;
	
	@Autowired
	ContaService contaService;
	
	public Transacao toEntity (TransacaoDTO dto) {
		return Transacao.builder()
				.id(dto.getId())
				.montante(dto.getMontante())
				.tipoTransacao(dto.getTipoTransacao())
				.status(dto.getStatus())
				.contaOrigem(contaMapper
						.toEntity(contaService
								.findById(dto.getOrigem_id())))
				.contaDestino(contaMapper
						.toEntity(contaService
								.findById(dto.getDestino_id())))
				.criadoEm(dto.getCriadoEm())
				.atualizadoEm(dto.getAtualizadoEm())
				.build();
	}
	
	public Transacao createPrePersistenceEntityWithProvidedAccounts (
			Double montante,
			String tipoTransacao,
			Conta origem,
			Conta destino
			) {
		return Transacao.builder()
				.id(null)
				.montante(montante)
				.tipoTransacao(tipoTransacao)
				.status("Concluído")
				.contaOrigem(origem)
				.contaDestino(destino)
				.criadoEm(Instant.now())
				.build();
	}
	
	public TransacaoDTO toDto (Transacao transacao) {
		return TransacaoDTO.builder()
				.id(transacao.getId())
				.montante(transacao.getMontante())
				.tipoTransacao(transacao.getTipoTransacao())
				.status(transacao.getStatus())
				.origem_id(transacao.getContaOrigem().getId())
				.destino_id(transacao.getContaDestino().getId())
				.criadoEm(transacao.getCriadoEm())
				.atualizadoEm(transacao.getAtualizadoEm())
				.build();
	}
}
