package com.bluebank.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.bluebank.entities.enums.TipoCliente;
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
public class ClienteDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EqualsAndHashCode.Include
	private Long id;
	private String cpf;
	private String nomeCompleto;
	private LocalDate dataNascimento;
	private Integer tipo;
	private String email;
	private String telefone;
	private Double rendaMensal;
	private Instant criadoEm;
	private Instant atualizadoEm;
	@JsonIgnore
	private Set<ContaDTO> contas;
	
	public Set<ContaDTO> getContas() {
		if (contas == null) {
			return new HashSet<>();
		}
		return contas;
	}
	
	public Instant getAtualizadoEm() {
		if (this.atualizadoEm == null) {
			this.atualizadoEm = this.criadoEm;
		}
		
		return this.atualizadoEm;
	}
	
	public TipoCliente getTipo() {
		return TipoCliente.toEnum(tipo);
	}

	public void setTipo(TipoCliente tipo) {
		if(tipo != null) {
		this.tipo = tipo.getCod();
		}
	}
}
