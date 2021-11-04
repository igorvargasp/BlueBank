package com.bluebank.mapper;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bluebank.dto.ClienteDTO;
import com.bluebank.entities.Cliente;

@Component
public class ClienteMapper {

	@Autowired
	ContaMapper contaMapper;
	
	public Cliente toEntity (ClienteDTO dto) {
		return Cliente.builder().id(dto.getId())
				.cpf(dto.getCpf())
				.nomeCompleto(dto.getNomeCompleto())
				.dataNascimento(dto.getDataNascimento())
				.tipoCliente(dto.getTipoCliente())
				.email(dto.getEmail())
				.telefone(dto.getTelefone())
				.rendaMensal(dto.getRendaMensal())
				.build();
	}
	
	public Cliente toEntitiesWithContas (ClienteDTO dto) {
		Cliente cliente = Cliente
				.builder()
				.id(dto.getId())
				.cpf(dto.getCpf())
				.nomeCompleto(dto.getNomeCompleto())
				.dataNascimento(dto.getDataNascimento())
				.tipoCliente(dto.getTipoCliente())
				.email(dto.getEmail())
				.telefone(dto.getTelefone())
				.rendaMensal(dto.getRendaMensal())
				.build();
		cliente.getContas().clear();
		cliente.setContas(dto
						.getContas()
						.stream()
						.map((conta) -> contaMapper
								.toEntity(conta))
						.collect(Collectors.toSet()));
		return cliente;
	}
	
	public ClienteDTO toDto (Cliente cliente) {
		return ClienteDTO.builder().id(cliente.getId())
				.cpf(cliente.getCpf())
				.nomeCompleto(cliente.getNomeCompleto())
				.dataNascimento(cliente.getDataNascimento())
				.tipoCliente(cliente.getTipoCliente())
				.email(cliente.getEmail())
				.telefone(cliente.getTelefone())
				.rendaMensal(cliente.getRendaMensal())
				.build();
	}
	
	public ClienteDTO toDtoWithContas (Cliente cliente) {
		ClienteDTO dto = ClienteDTO
				.builder()
				.id(cliente.getId())
				.cpf(cliente.getCpf())
				.nomeCompleto(cliente.getNomeCompleto())
				.dataNascimento(cliente.getDataNascimento())
				.tipoCliente(cliente.getTipoCliente())
				.email(cliente.getEmail())
				.telefone(cliente.getTelefone())
				.rendaMensal(cliente.getRendaMensal())
				.build();
		dto.getContas().clear();
		dto.setContas(cliente
						.getContas()
						.stream()
						.map((conta) -> contaMapper
								.toDto(conta))
						.collect(Collectors.toSet()));
		return dto;
	}
	
}
