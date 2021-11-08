package com.bluebank.dto;

import java.io.Serializable;
import java.time.Instant;

import com.bluebank.entities.enums.StatusConta;
import com.bluebank.entities.enums.TipoConta;
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
public class ContaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	private Long id;
	private String conta;
	private String agencia;
	private Double saldo;
	private Double limiteCredito;
	private Integer tipo;
	private Integer status;
	private Instant criadoEm;
	private Instant atualizadoEm;
	private Long clienteId;
	@JsonIgnore
	private ClienteDTO cliente;
	
	public Instant getAtualizadoEm() {
		if (this.atualizadoEm == null) {
			this.atualizadoEm = this.criadoEm;
		}
		
		return this.atualizadoEm;
	}
	
	public TipoConta getTipo() {
		return TipoConta.toEnum(tipo);
	}

	public void setTipo(TipoConta tipo) {
		if(tipo != null) {
		this.tipo = tipo.getCod();
		}
	}
	
	public StatusConta getStatus() {
		return StatusConta.toEnum(status);
	}
	
	public void setStatus(StatusConta status) {
		if(status != null) {
			this.status = status.getCod();
		}
	}
}
