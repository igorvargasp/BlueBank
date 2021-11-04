package com.bluebank.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bluebank.dto.ContaDTO;
import com.bluebank.entities.Conta;

@Component
public class ContaMapper {

	@Autowired
	ClienteMapper clienteMapper;
	
	public Conta toEntity (ContaDTO dto) {
		return Conta.builder()
				.id(dto.getId())
				.conta(dto.getConta())
				.agencia(dto.getAgencia())
				.saldo(dto.getSaldo())
				.limiteCredito(dto.getLimiteCredito())
				.tipoConta(dto.getTipoConta())
				.cliente(clienteMapper.toEntity(dto.getCliente()))
				.build();
	}
	
	public ContaDTO toDto (Conta conta) {
		return ContaDTO.builder()
				.id(conta.getId())
				.conta(conta.getConta())
				.agencia(conta.getAgencia())
				.saldo(conta.getSaldo())
				.limiteCredito(conta.getLimiteCredito())
				.tipoConta(conta.getTipoConta())
				.cliente(clienteMapper.toDto(conta.getCliente()))
				.build();
	}
	
}
