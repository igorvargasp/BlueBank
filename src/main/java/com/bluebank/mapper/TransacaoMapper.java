package com.bluebank.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bluebank.dto.TransacaoDTO;
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
				.origem(contaMapper
						.toEntity(contaService
								.findById(dto.getOrigem_id())))
				.destino(contaMapper
						.toEntity(contaService
								.findById(dto.getDestino_id())))
				.build();
	}
	
	public TransacaoDTO toDto (Transacao transacao) {
		return TransacaoDTO.builder()
				.id(transacao.getId())
				.montante(transacao.getMontante())
				.tipoTransacao(transacao.getTipoTransacao())
				.status(transacao.getStatus())
				.origem_id(transacao.getId())
				.destino_id(transacao.getId())
				.build();
	}
}
