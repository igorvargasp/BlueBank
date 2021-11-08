package com.bluebank.mapper;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bluebank.dto.TransacaoDTO;
import com.bluebank.entities.Conta;
import com.bluebank.entities.Transacao;
import com.bluebank.entities.enums.StatusTransacao;
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
				.tipo(dto.getTipo().getCod())
				.status(dto.getStatus().getCod())
				.contaOrigem(contaMapper
						.toEntity(contaService
								.findById(dto.getContaOrigem_id())))
				.contaDestino(contaMapper
						.toEntity(contaService
								.findById(dto.getContaDestino_id())))
				.criadoEm(dto.getCriadoEm())
				.atualizadoEm(dto.getAtualizadoEm())
				.build();
	}
	
	public Transacao createPrePersistenceEntityWithProvidedAccounts (
			Double montante,
			Integer tipoTransacao,
			Conta origem,
			Conta destino
			) {
		return Transacao.builder()
				.id(null)
				.montante(montante)
				.tipo(tipoTransacao)
				.status((StatusTransacao.CONCLUÍDA.getCod()))
				.contaOrigem(origem)
				.contaDestino(destino)
				.criadoEm(Instant.now())
				.build();
	}
	
	public TransacaoDTO toDto (Transacao transacao) {
		return TransacaoDTO.builder()
				.id(transacao.getId())
				.montante(transacao.getMontante())
				.tipo(transacao.getTipo().getCod())
				.status(transacao.getStatus().getCod())
				.contaOrigem_id(transacao.getContaOrigem().getId())
				.contaDestino_id(transacao.getContaDestino().getId())
				.criadoEm(transacao.getCriadoEm())
				.atualizadoEm(transacao.getAtualizadoEm())
				.build();
	}
}
