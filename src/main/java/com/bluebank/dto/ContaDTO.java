package com.bluebank.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContaDTO {

	@EqualsAndHashCode.Include
	private Long id;
	private Long conta;
	private Long agencia;
	private Double saldo;
	private Double limiteCredito;
	private String tipoConta;
	
	@JsonIgnore
	private ClienteDTO cliente;
}
