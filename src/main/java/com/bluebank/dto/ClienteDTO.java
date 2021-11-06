package com.bluebank.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
	private String tipoCliente;
	private String email;
	private String telefone;
	private Double rendaMensal;
	
	private Set<ContaDTO> contas;
	
	public Set<ContaDTO> getContas() {
		if (contas == null) {
			return new HashSet<>();
		}
		return contas;
	}
}
