package com.bluebank.tests;

import java.time.LocalDate;

import com.bluebank.dto.ClienteDTO;
import com.bluebank.entities.Cliente;
import com.bluebank.entities.Conta;

public class Factory {

	public static Cliente createCliente() {
		
		Cliente cliente = Cliente.builder()
				.id(null)
				.cpf("12312345678")
				.nomeCompleto("Joao da Silva")
				.dataNascimento(LocalDate.of(2000, 10, 10))
				.tipoCliente("PF")
				.email("joao@gmail.com")
				.telefone("(88)987541236")
				.rendaMensal(3570.00)
				.build();
		
		return cliente;
	}
	
	public static ClienteDTO createClienteDTO() {
		
		ClienteDTO dto = ClienteDTO.builder()
				.id(null)
				.cpf("12312345678")
				.nomeCompleto("Joao da Silva")
				.dataNascimento(LocalDate.of(2000, 10, 10))
				.tipoCliente("PF")
				.email("joao@gmail.com")
				.telefone("(88)987541236")
				.rendaMensal(3570.00)
				.build();
		
		return dto;
	}
	
	public static Conta createConta () {
		return Conta.builder()
				.id(null)
				.conta("88888")
				.agencia("777")
				.saldo(700.00)
				.limiteCredito(1000.00)
				.tipoConta("PF")
				.build();
	}

}
