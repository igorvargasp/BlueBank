package com.bluebank.dto;

import java.io.Serializable;
import java.time.Instant;

import com.bluebank.entities.enums.StatusTransacao;
import com.bluebank.entities.enums.TipoTransacao;

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
public class TransacaoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	private Long id;
	private Double montante;
	private Integer tipo;
	private Integer status;
	private Long contaOrigem_id;
	private Long contaDestino_id;
	private Instant criadoEm;
	private Instant atualizadoEm;
	
	public Instant getAtualizadoEm() {
		if (this.atualizadoEm == null) {
			this.atualizadoEm = this.criadoEm;
		}
		
		return this.atualizadoEm;
	}
	
	public TipoTransacao getTipo() {
		return TipoTransacao.toEnum(tipo);
	}

	public void setTipo(TipoTransacao tipo) {
		if(tipo != null) {
		this.tipo = tipo.getCod();
		}
	}
	
	public StatusTransacao getStatus() {
		return StatusTransacao.toEnum(status);
	}
	
	public void setStatus(StatusTransacao status) {
		if(status != null) {
			this.status = status.getCod();
		}
	}
}
