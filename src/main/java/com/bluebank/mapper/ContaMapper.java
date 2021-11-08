package com.bluebank.mapper;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bluebank.dto.ContaDTO;
import com.bluebank.entities.Conta;
import com.bluebank.entities.enums.StatusConta;
import com.bluebank.repository.ClienteRepository;
import com.bluebank.service.exceptions.BusinessException;
import com.bluebank.service.exceptions.ResourceNotFoundException;

@Component
public class ContaMapper {

	@Autowired
	ClienteMapper clienteMapper;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	public Conta toEntity (ContaDTO dto) {
		return Conta.builder()
				.id(dto.getId())
				.conta(dto.getConta())
				.agencia(dto.getAgencia())
				.saldo(dto.getSaldo())
				.limiteCredito(dto.getLimiteCredito())
				.tipo(dto.getTipo().getCod())
				.cliente(clienteMapper.toEntity(dto.getCliente()))
				.status(dto.getStatus().getCod())
				.criadoEm(dto.getCriadoEm())
				.atualizadoEm(dto.getAtualizadoEm())
				.build();
	}
	
	public ContaDTO toDto (Conta conta) {
		return ContaDTO.builder()
				.id(conta.getId())
				.conta(conta.getConta())
				.agencia(conta.getAgencia())
				.saldo(conta.getSaldo())
				.limiteCredito(conta.getLimiteCredito())
				.tipo(conta.getTipo().getCod())
				.cliente(clienteMapper.toDto(conta.getCliente()))
				.status(conta.getStatus().getCod())
				.criadoEm(conta.getCriadoEm())
				.atualizadoEm(conta.getAtualizadoEm())
				.build();
	}

	public Conta createPrePersistenceAccount(ContaDTO dto) {
		if (dto.getClienteId() == null) {
			throw new BusinessException("Informe o id do cliente!");
		}
		
		return Conta.builder()
		.conta(dto.getConta())
		.agencia(dto.getAgencia())
		.saldo(0.0)
		.limiteCredito(0.0)
		.tipo(dto.getTipo().getCod())
		.cliente(clienteRepository.findById(dto.getClienteId())
				.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado! Id = " + dto.getClienteId())))
		.status(StatusConta.ATIVA.getCod())
		.criadoEm(Instant.now())
		.atualizadoEm(Instant.now())
		.build();
	}
	
}
